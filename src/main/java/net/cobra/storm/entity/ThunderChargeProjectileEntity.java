package net.cobra.storm.entity;

import net.cobra.storm.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ThunderChargeProjectileEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(ThunderChargeProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ThunderChargeProjectileEntity(EntityType<? extends ThunderChargeProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    
    public ThunderChargeProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntityType.THUNDER_PROJECTILE_ENTITY, livingEntity, world);
    }

    public ThunderChargeProjectileEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(ModEntityType.THUNDER_PROJECTILE_ENTITY, x, y, z, world);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
        return this.isCharged() && WitherEntity.canDestroy(blockState) ? Math.min(0.8F, max) : max;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
            lightningEntity.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
            this.getWorld().spawnEntity(lightningEntity);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, World.ExplosionSourceType.MOB);
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.getWorld());
            lightningEntity.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
            this.getWorld().spawnEntity(lightningEntity);
            this.discard();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THUNDER_CHARGE;
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
