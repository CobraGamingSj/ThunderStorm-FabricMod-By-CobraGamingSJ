package net.cobra.storm.item;

import net.cobra.storm.entity.projectile.ThunderBoltEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ThunderChargeItem extends Item implements ProjectileItem {
    public ThunderChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ThunderBoltEntity thunderChargeProjectile = new ThunderBoltEntity(user, world);
            thunderChargeProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(thunderChargeProjectile);
        }

        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.ENTITY_WIND_CHARGE_THROW,
                SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 10);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void initializeProjectile(ProjectileEntity entity, double x, double y, double z, float power, float uncertainty) {
    }

    @Override
    public ProjectileItem.Settings getProjectileSettings() {
        return ProjectileItem.Settings.builder()
                .positionFunction((pointer, facing) -> DispenserBlock.getOutputLocation(pointer, 1.0, Vec3d.ZERO))
                .uncertainty(6.6666665F)
                .power(1.0F)
                .overrideDispenseEvent(1051)
                .build();
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        Random random = world.getRandom();
        double d = random.nextTriangular(direction.getOffsetX(), 0.11485000000000001);
        double e = random.nextTriangular(direction.getOffsetY(), 0.11485000000000001);
        double f = random.nextTriangular(direction.getOffsetZ(), 0.11485000000000001);
        Vec3d vec3d = new Vec3d(d, e, f);
        ThunderBoltEntity thunderChargeProjectile = new ThunderBoltEntity(world, pos.getX(), pos.getY(), pos.getZ(), vec3d);
        thunderChargeProjectile.setVelocity(vec3d);
        return thunderChargeProjectile;
    }
}
