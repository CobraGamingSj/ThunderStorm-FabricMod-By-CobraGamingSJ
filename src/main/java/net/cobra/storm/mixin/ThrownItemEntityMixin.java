package net.cobra.storm.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExplosiveProjectileEntity.class)
public class ThrownItemEntityMixin {

//    @Inject(method = "getDrag", at = @At("HEAD"), cancellable = true)
//    protected float ModifygetDrag() {
//        return 1.0f;
//    }
}
