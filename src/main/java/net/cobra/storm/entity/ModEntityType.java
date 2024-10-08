package net.cobra.storm.entity;

import net.cobra.storm.ThunderStorm;
import net.cobra.storm.entity.projectile.ThunderBoltEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityType {

    public static final EntityType<ThunderStalkerEntity> THUNDER_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThunderStorm.MOD_ID, "thunder_storm"), EntityType.Builder.create(ThunderStalkerEntity::new, SpawnGroup.MONSTER).dimensions(1f, 1f).build());

    public static final EntityType<ThunderBoltEntity> THUNDER_BOLT_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThunderStorm.MOD_ID, "thunder_bolt"),
            EntityType.Builder.<ThunderBoltEntity>create(ThunderBoltEntity::new, SpawnGroup.MISC).dimensions(0.25f, 0.25f).build());

    public static void register() {

    }
}
