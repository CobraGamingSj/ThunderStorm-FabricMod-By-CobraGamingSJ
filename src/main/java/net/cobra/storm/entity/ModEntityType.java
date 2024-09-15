package net.cobra.storm.entity;

import net.cobra.storm.ThunderStorm;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityType {

    public static final EntityType<ThunderEntity> THUNDER_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThunderStorm.MOD_ID, "thunder_storm"), EntityType.Builder.create(ThunderEntity::new, SpawnGroup.MONSTER).dimensions(1f, 1f).build());

    public static final EntityType<ThunderChargeProjectileEntity> THUNDER_PROJECTILE_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThunderStorm.MOD_ID, "thunder_charge"),
            EntityType.Builder.<ThunderChargeProjectileEntity>create(ThunderChargeProjectileEntity::new, SpawnGroup.MISC).dimensions(0.25f, 0.25f).build());

    public static void register() {

    }
}
