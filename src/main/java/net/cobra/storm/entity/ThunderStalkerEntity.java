package net.cobra.storm.entity;

import com.google.common.collect.ImmutableList;
import net.cobra.storm.entity.ai.SeekAndAttackGoal;
import net.cobra.storm.entity.projectile.ThunderBoltEntity;
import net.cobra.storm.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

import static net.cobra.storm.registry.tag.ModBlockTags.THUNDER_STALKER_IMMUNE;

public class ThunderStalkerEntity extends HostileEntity implements SkinOverlayOwner, RangedAttackMob {
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_1 = DataTracker.registerData(ThunderStalkerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_2 = DataTracker.registerData(ThunderStalkerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_3 = DataTracker.registerData(ThunderStalkerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final List<TrackedData<Integer>> TRACKED_ENTITY_IDS = ImmutableList.of(TRACKED_ENTITY_ID_1, TRACKED_ENTITY_ID_2, TRACKED_ENTITY_ID_3);
    private static final TrackedData<Integer> INVUL_TIMER = DataTracker.registerData(ThunderStalkerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final float[] sideHeadPitches = new float[2];
    private final float[] sideHeadYaws = new float[2];
    private final float[] prevSideHeadPitches = new float[2];
    private final float[] prevSideHeadYaws = new float[2];
    private final int[] chargeCooldowns = new int[2];
    private final int[] chargedThunderCooldowns = new int[2];
    private int blockBreakingCooldown;
    private final ServerBossBar bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.RED, BossBar.Style.PROGRESS).setDarkenSky(true);
    private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE = entity -> !entity.getType().isIn(EntityTypeTags.WITHER_FRIENDS) && entity.isMobOrPlayer();
    private static final TargetPredicate HEAD_TARGET_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(20.0).setPredicate(CAN_ATTACK_PREDICATE);
    private static final ProjectileDeflection PROJECTILE_DEFLECTOR = (projectile, hitEntity, random) -> {
        hitEntity.getWorld().playSoundFromEntity(null, hitEntity, SoundEvents.ENTITY_BREEZE_DEFLECT, hitEntity.getSoundCategory(), 1.0F, 1.0F);
        ProjectileDeflection.SIMPLE.deflect(projectile, hitEntity, random);
    };
    private static final int LOOK_RADIUS = 100;

    public ThunderStalkerEntity(EntityType<? extends ThunderStalkerEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
        this.setHealth(this.getMaxHealth());
        this.experiencePoints = 50;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public boolean isInLookRadius(LivingEntity entity) {
        return this.squaredDistanceTo(entity) <= LOOK_RADIUS * LOOK_RADIUS;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(9, new SeekAndAttackGoal(this, LOOK_RADIUS));
        this.goalSelector.add(6, new ProjectileAttackGoal(this, 1.0, 40, 20.0F));
        this.goalSelector.add(4, new FlyGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(5, new RevengeGoal(this));
        this.targetSelector.add(9, new ActiveTargetGoal(this, AnimalEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
        this.targetSelector.add(9, new ActiveTargetGoal(this, LivingEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TRACKED_ENTITY_ID_1, 0);
        builder.add(TRACKED_ENTITY_ID_2, 0);
        builder.add(TRACKED_ENTITY_ID_3, 0);
        builder.add(INVUL_TIMER, 0);
    }

    public double getChargeY() {
        return this.getEyeY() - 0.4;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Invul", this.getInvulnerableTimer());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setInvulTimer(nbt.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BREEZE_DEATH;
    }

    @Override
    public void tickMovement() {
        Vec3d vec3d = this.getVelocity().multiply(1.0, 0.6, 1.0);
        if (!this.getWorld().isClient && this.getTrackedEntityId(0) > 0) {
            Entity entity = this.getWorld().getEntityById(this.getTrackedEntityId(0));
            if (entity != null) {
                double d = vec3d.y;
                if (this.getY() < entity.getY() || !this.shouldRenderOverlay() && this.getY() < entity.getY() + 5.0) {
                    d = Math.max(0.0, d);
                    d += 0.3 - d * 0.6F;
                }

                vec3d = new Vec3d(vec3d.x, d, vec3d.z);
                Vec3d vec3d2 = new Vec3d(entity.getX() - this.getX(), 0.0, entity.getZ() - this.getZ());
                if (vec3d2.horizontalLengthSquared() > 9.0) {
                    Vec3d vec3d3 = vec3d2.normalize();
                    vec3d = vec3d.add(vec3d3.x * 0.3 - vec3d.x * 0.6, 0.0, vec3d3.z * 0.3 - vec3d.z * 0.6);
                }
            }
        }

        this.setVelocity(vec3d);
        if (vec3d.horizontalLengthSquared() > 0.05) {
            this.setYaw((float) MathHelper.atan2(vec3d.z, vec3d.x) * (180.0F / (float) Math.PI) - 90.0F);
        }

        super.tickMovement();

        for (int i = 0; i < 2; i++) {
            this.prevSideHeadYaws[i] = this.sideHeadYaws[i];
            this.prevSideHeadPitches[i] = this.sideHeadPitches[i];
        }

        for (int i = 0; i < 2; i++) {
            int j = this.getTrackedEntityId(i + 1);
            Entity entity2 = null;
            if (j > 0) {
                entity2 = this.getWorld().getEntityById(j);
            }

            if (entity2 != null) {
                double e = this.getHeadX(i + 1);
                double f = this.getHeadY(i + 1);
                double g = this.getHeadZ(i + 1);
                double h = entity2.getX() - e;
                double k = entity2.getEyeY() - f;
                double l = entity2.getZ() - g;
                double m = Math.sqrt(h * h + l * l);
                float n = (float) (MathHelper.atan2(l, h) * 180.0F / (float) Math.PI) - 90.0F;
                float o = (float) (-(MathHelper.atan2(k, m) * 180.0F / (float) Math.PI));
                this.sideHeadPitches[i] = this.getNextAngle(this.sideHeadPitches[i], o, 40.0F);
                this.sideHeadYaws[i] = this.getNextAngle(this.sideHeadYaws[i], n, 10.0F);
            } else {
                this.sideHeadYaws[i] = this.getNextAngle(this.sideHeadYaws[i], this.bodyYaw, 10.0F);
            }
        }

        boolean bl = this.shouldRenderOverlay();

        for (int jx = 0; jx < 3; jx++) {
            double p = this.getHeadX(jx);
            double q = this.getHeadY(jx);
            double r = this.getHeadZ(jx);
            float s = 0.3F * this.getScale();
            this.getWorld()
                    .addParticle(
                            ParticleTypes.SMOKE,
                            p + this.random.nextGaussian() * (double) s,
                            q + this.random.nextGaussian() * (double) s,
                            r + this.random.nextGaussian() * (double) s,
                            0.0,
                            0.0,
                            0.0
                    );
            if (bl && this.getWorld().random.nextInt(4) == 0) {
                this.getWorld()
                        .addParticle(
                                EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, 0.7F, 0.7F, 0.5F),
                                p + this.random.nextGaussian() * (double) s,
                                q + this.random.nextGaussian() * (double) s,
                                r + this.random.nextGaussian() * (double) s,
                                0.0,
                                0.0,
                                0.0
                        );
            }
        }

        if (this.getInvulnerableTimer() > 0) {
            float t = 3.3F * this.getScale();

            for (int u = 0; u < 3; u++) {
                this.getWorld()
                        .addParticle(
                                EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, 0.7F, 0.7F, 0.9F),
                                this.getX() + this.random.nextGaussian(),
                                this.getY() + (double) (this.random.nextFloat() * t),
                                this.getZ() + this.random.nextGaussian(),
                                0.0,
                                0.0,
                                0.0
                        );
            }
        }
    }

    @Override
    protected void mobTick() {

        if (this.getInvulnerableTimer() > 0) {
            int i = this.getInvulnerableTimer() - 1;
            this.bossBar.setPercent(1.0F - (float) i / 220.0f);
            if (i <= 0) {
                this.getWorld().createExplosion(this, this.getX(), this.getEyeY(), this.getZ(), 10.0F, false, World.ExplosionSourceType.TNT);
                if (!this.isSilent()) {
                    this.getWorld().syncGlobalEvent(WorldEvents.WITHER_SPAWNS, this.getBlockPos(), 0);
                }
            }

            this.setInvulTimer(i);
            if (this.age % 10 == 0) {
                this.heal(10.0F);
            }
        } else {
            super.mobTick();

            for (int ix = 1; ix < 3; ix++) {
                if (this.age >= this.chargeCooldowns[ix - 1]) {
                    this.chargeCooldowns[ix - 1] = this.age + 10 + this.random.nextInt(10);
                    if ((this.getWorld().getDifficulty() == Difficulty.NORMAL || this.getWorld().getDifficulty() == Difficulty.HARD)
                            && this.chargedThunderCooldowns[ix - 1]++ > 15) {
                        float f = 10.0F;
                        float g = 5.0F;
                        double d = MathHelper.nextDouble(this.random, this.getX() - 10.0, this.getX() + 10.0);
                        double e = MathHelper.nextDouble(this.random, this.getY() - 5.0, this.getY() + 5.0);
                        double h = MathHelper.nextDouble(this.random, this.getZ() - 10.0, this.getZ() + 10.0);
                        this.shootThunderChargeAt(ix + 1, d, e, h, true);
                        this.chargedThunderCooldowns[ix - 1] = 0;
                    }

                    int j = this.getTrackedEntityId(ix);
                    if (j > 0) {
                        LivingEntity livingEntity = (LivingEntity) this.getWorld().getEntityById(j);
                        if (livingEntity != null && this.canTarget(livingEntity) && !(this.squaredDistanceTo(livingEntity) > 900.0) && this.canSee(livingEntity)) {
                            this.shootThunderChargeAt(ix + 1, livingEntity);
                            this.chargeCooldowns[ix - 1] = this.age + 40 + this.random.nextInt(20);
                            this.chargedThunderCooldowns[ix - 1] = 0;
                        } else {
                            this.setTrackedEntityId(ix, 0);
                        }
                    } else {
                        List<LivingEntity> list = this.getWorld().getTargets(LivingEntity.class, HEAD_TARGET_PREDICATE, this, this.getBoundingBox().expand(20.0, 8.0, 20.0));
                        if (!list.isEmpty()) {
                            LivingEntity livingEntity2 = (LivingEntity) list.get(this.random.nextInt(list.size()));
                            this.setTrackedEntityId(ix, livingEntity2.getId());
                        }
                    }
                }
            }

            if (this.getTarget() != null) {
                this.setTrackedEntityId(0, this.getTarget().getId());
            } else {
                this.setTrackedEntityId(0, 0);
            }

            if (this.blockBreakingCooldown > 0) {
                this.blockBreakingCooldown--;
                if (this.blockBreakingCooldown == 0 && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    boolean bl = false;
                    int j = MathHelper.floor(this.getWidth() / 2.0F + 1.0F);
                    int k = MathHelper.floor(this.getHeight());

                    for (BlockPos blockPos : BlockPos.iterate(
                            this.getBlockX() - j, this.getBlockY(), this.getBlockZ() - j, this.getBlockX() + j, this.getBlockY() + k, this.getBlockZ() + j
                    )) {
                        BlockState blockState = this.getWorld().getBlockState(blockPos);
                        if (canDestroy(blockState)) {
                            bl = this.getWorld().breakBlock(blockPos, true, this) || bl;
                        }
                    }

                    if (bl) {
                        this.getWorld().syncWorldEvent(null, WorldEvents.WITHER_BREAKS_BLOCK, this.getBlockPos(), 0);
                    }
                }
            }

            if (this.age % 20 == 0) {
                this.heal(1.5F);
            }

            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    public static boolean canDestroy(BlockState block) {
        return !block.isAir() && !block.isIn(THUNDER_STALKER_IMMUNE);
    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    private double getHeadX(int headIndex) {
        if (headIndex <= 0) {
            return this.getX();
        } else {
            float f = (this.bodyYaw + (float) (180 * (headIndex - 1))) * (float) (Math.PI / 180.0);
            float g = MathHelper.cos(f);
            return this.getX() + (double) g * 1.3 * (double) this.getScale();
        }
    }

    private double getHeadY(int headIndex) {
        float f = headIndex <= 0 ? 3.0F : 2.2F;
        return this.getY() + (double) (f * this.getScale());
    }

    private double getHeadZ(int headIndex) {
        if (headIndex <= 0) {
            return this.getZ();
        } else {
            float f = (this.bodyYaw + (float) (180 * (headIndex - 1))) * (float) (Math.PI / 180.0);
            float g = MathHelper.sin(f);
            return this.getZ() + (double) g * 1.3 * (double) this.getScale();
        }
    }

    private float getNextAngle(float prevAngle, float desiredAngle, float maxDifference) {
        float f = MathHelper.wrapDegrees(desiredAngle - prevAngle);
        if (f > maxDifference) {
            f = maxDifference;
        }

        if (f < -maxDifference) {
            f = -maxDifference;
        }

        return prevAngle + f;
    }

    private void shootThunderChargeAt(int headIndex, LivingEntity target) {
        this.shootThunderChargeAt(
                headIndex, target.getX(), target.getY() + (double) target.getStandingEyeHeight() * 0.5, target.getZ(), headIndex == 0 && this.random.nextFloat() < 0.001F
        );
    }

    private void shootThunderChargeAt(int headIndex, double targetX, double targetY, double targetZ, boolean charged) {
        if (!this.isSilent()) {
            this.getWorld().syncWorldEvent(null, WorldEvents.WITHER_SHOOTS, this.getBlockPos(), 0);
        }

        double d = this.getHeadX(headIndex);
        double e = this.getHeadY(headIndex);
        double f = this.getHeadZ(headIndex);
        double g = targetX - d;
        double h = targetY - e;
        double i = targetZ - f;
        Vec3d vec3d = new Vec3d(g, h, i);
        ThunderBoltEntity thunderChargeEntity = new ThunderBoltEntity(this, this.getWorld());
        thunderChargeEntity.setOwner(this);
        if (charged) {
            thunderChargeEntity.setCharged(true);
        }

        thunderChargeEntity.setPos(d, e, f);
        this.getWorld().spawnEntity(thunderChargeEntity);
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        this.shootThunderChargeAt(0, target);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {

        if (source.getSource() instanceof TntEntity || source.getSource() instanceof LightningEntity) {
            return false;
        }

        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.isIn(DamageTypeTags.WITHER_IMMUNE_TO) || source.getAttacker() instanceof ThunderStalkerEntity) {
            return false;
        } else if (this.getInvulnerableTimer() > 0 && !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        } else {
            if (this.shouldRenderOverlay()) {
                Entity entity = source.getSource();
                if (entity instanceof PersistentProjectileEntity || entity instanceof WindChargeEntity || entity instanceof ThunderBoltEntity) {
                    return false;
                }
            }

            Entity entity = source.getAttacker();
            if (entity != null && entity.getType().isIn(EntityTypeTags.WITHER_FRIENDS)) {
                return false;
            } else {
                if (this.blockBreakingCooldown <= 0) {
                    this.blockBreakingCooldown = 20;
                }

                for (int i = 0; i < this.chargedThunderCooldowns.length; i++) {
                    this.chargedThunderCooldowns[i] = this.chargedThunderCooldowns[i] + 3;
                }

                return super.damage(source, amount);
            }
        }
    }

    @Override
    public ProjectileDeflection getProjectileDeflection(ProjectileEntity projectile) {
        if (projectile.getType() != EntityType.BREEZE_WIND_CHARGE && projectile.getType() != EntityType.WIND_CHARGE && projectile.getType() != ModEntityType.THUNDER_BOLT_ENTITY && projectile.getType() != EntityType.WITHER_SKULL && projectile.getType() != EntityType.TRIDENT) {
            return this.getType().isIn(EntityTypeTags.DEFLECTS_PROJECTILES) ? PROJECTILE_DEFLECTOR : ProjectileDeflection.NONE;
        } else {
            return ProjectileDeflection.NONE;
        }
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        super.dropEquipment(world, source, causedByPlayer);
        ItemEntity itemEntity = this.dropItem(ModItems.THUNDER_BOLT);
        if (itemEntity != null) {
            itemEntity.setCovetedItem();
        }
    }

    @Override
    public void checkDespawn() {
        if (this.getWorld().getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
        return super.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, false, false, false), source);
    }

    public static DefaultAttributeContainer.Builder createThunderStormAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2000)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 10)
                .add(EntityAttributes.GENERIC_ARMOR, 12.0)
                .add(EntityAttributes.GENERIC_SCALE, 5.0);
    }

    public float getHeadYaw(int headIndex) {
        return this.sideHeadYaws[headIndex];
    }

    public float getHeadPitch(int headIndex) {
        return this.sideHeadPitches[headIndex];
    }

    public int getInvulnerableTimer() {
        return this.dataTracker.get(INVUL_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.dataTracker.set(INVUL_TIMER, ticks);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource.getAttacker() instanceof BreezeEntity || damageSource.getAttacker() instanceof ThunderStalkerEntity || damageSource.getAttacker() instanceof WitherEntity || super.isInvulnerableTo(damageSource);
    }

    public int getTrackedEntityId(int headIndex) {
        return this.dataTracker.<Integer>get((TrackedData<Integer>) TRACKED_ENTITY_IDS.get(headIndex));
    }

    public void setTrackedEntityId(int headIndex, int id) {
        this.dataTracker.set((TrackedData<Integer>) TRACKED_ENTITY_IDS.get(headIndex), id);
    }

    @Override
    public boolean shouldRenderOverlay() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.equals(StatusEffects.BLINDNESS) ? false : super.canHaveStatusEffect(effect);
    }
}
