package net.cobra.storm.entity.ai;

import net.cobra.storm.entity.ThunderStalkerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.List;

public class SeekAndAttackGoal extends Goal {
    private final ThunderStalkerEntity entity;
    private final double radius;
    private LivingEntity target;

    public SeekAndAttackGoal(ThunderStalkerEntity entity, double radius) {
        this.entity = entity;
        this.radius = radius;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        List<LivingEntity> nearbyEntities = this.entity.getWorld().getEntitiesByClass(LivingEntity.class, new Box(
                this.entity.getX() - radius, this.entity.getY() - radius, this.entity.getZ() - radius,
                this.entity.getX() + radius, this.entity.getY() + radius, this.entity.getZ() + radius
        ), entity -> entity instanceof AnimalEntity || entity instanceof PlayerEntity);

        if (!nearbyEntities.isEmpty()) {
            this.target = nearbyEntities.stream()
                    .filter(this.entity::isInLookRadius)
                    .findFirst()
                    .orElse(null);
            return this.target != null;
        }

        return false;
    }

    @Override
    public void start() {
        if (this.target != null) {
            this.entity.getNavigation().startMovingTo(this.target, 1.0); // Adjust speed as needed
        }
    }

    @Override
    public void tick() {
        if (this.target != null) {
            this.entity.getNavigation().startMovingTo(this.target, 1.0); // Adjust speed as needed
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null && this.target.isAlive() && this.entity.squaredDistanceTo(this.target) <= radius * radius;
    }

    @Override
    public void stop() {
        this.entity.setTarget(null);
        this.entity.getNavigation().stop();
    }
}
