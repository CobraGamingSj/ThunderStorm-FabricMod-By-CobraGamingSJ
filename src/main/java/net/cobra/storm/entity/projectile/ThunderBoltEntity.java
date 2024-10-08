package net.cobra.storm.entity.projectile;

import net.cobra.storm.entity.ModEntityType;
import net.cobra.storm.entity.ThunderStalkerEntity;
import net.cobra.storm.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ThunderBoltEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(ThunderBoltEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int deflectCooldown = 5;

    public ThunderBoltEntity(EntityType<? extends ThunderBoltEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThunderBoltEntity(LivingEntity livingEntity, World world) {
        super(ModEntityType.THUNDER_BOLT_ENTITY, livingEntity, world);
    }

    public ThunderBoltEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(ModEntityType.THUNDER_BOLT_ENTITY, x, y, z, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.deflectCooldown > 0) {
            this.deflectCooldown--;
        }
    }
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        return new EntitySpawnS2CPacket(this, entityTrackerEntry);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
    }

    @Override
    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return this.deflectCooldown > 0 ? false : super.deflect(deflection, deflector, owner, fromAttack);
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

    @Override
    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
        return this.isCharged() && ThunderStalkerEntity.canDestroy(blockState) ? Math.min(10.5F, max) : max;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
            TntEntity tnt = new TntEntity(EntityType.TNT, this.getWorld());
            lightningEntity.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
            tnt.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
            this.getWorld().spawnEntity(lightningEntity);
            this.getWorld().spawnEntity(tnt);
            tnt.setFuse(0);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, World.ExplosionSourceType.TNT);
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, World.ExplosionSourceType.TRIGGER);
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
            TntEntity tnt = new TntEntity(EntityType.TNT, this.getWorld());
            lightningEntity.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
            tnt.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
            tnt.setFuse(0);
            this.getWorld().spawnEntity(lightningEntity);
            this.getWorld().spawnEntity(tnt);
            this.discard();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THUNDER_BOLT;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(CHARGED, false);
        super.initDataTracker(builder);
    }

    public boolean isCharged() {
        return this.dataTracker.get(CHARGED);
    }

    public void setCharged(boolean charged) {
        this.dataTracker.set(CHARGED, charged);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("dangerous", this.isCharged());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCharged(nbt.getBoolean("dangerous"));
    }
}
