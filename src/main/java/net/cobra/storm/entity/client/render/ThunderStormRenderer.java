package net.cobra.storm.entity.client.render;

import net.cobra.storm.ThunderStorm;
import net.cobra.storm.entity.ThunderEntity;
import net.cobra.storm.entity.client.model.ThunderEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ThunderStormRenderer extends MobEntityRenderer<ThunderEntity, ThunderEntityModel<ThunderEntity>> {
    private static final Identifier TEXTURE = Identifier.of(ThunderStorm.MOD_ID, "textures/entity/thunder/thunder_storm.png");

    public ThunderStormRenderer(EntityRendererFactory.Context context) {
        super(context, new ThunderEntityModel<>(context.getPart(ModModelLayers.THUNDER_STORM)), 0.75f);
    }

    @Override
    public Identifier getTexture(ThunderEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(ThunderEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(1f, 1f, 1f);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
