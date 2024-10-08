package net.cobra.storm;

import net.cobra.storm.entity.ModEntityType;
import net.cobra.storm.entity.client.model.ThunderEntityModel;
import net.cobra.storm.entity.client.render.ModModelLayers;
import net.cobra.storm.entity.client.render.ThunderStormRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ThunderStormClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityType.THUNDER_BOLT_ENTITY, FlyingItemEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.THUNDER_STORM, ThunderEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityType.THUNDER_ENTITY, ThunderStormRenderer::new);
    }
}
