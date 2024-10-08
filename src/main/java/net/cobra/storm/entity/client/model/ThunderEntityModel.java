package net.cobra.storm.entity.client.model;

import net.cobra.storm.entity.ThunderStalkerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ThunderEntityModel<T extends ThunderStalkerEntity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart head1;
	private final ModelPart head2;
	private final ModelPart head3;
	private final ModelPart body1;
	private final ModelPart body2;
	private final ModelPart body3;
	public ThunderEntityModel(ModelPart root) {
        this.root = root;
        this.head1 = root.getChild("head1");
		this.head2 = root.getChild("head2");
		this.head3 = root.getChild("head3");
		this.body1 = root.getChild("body1");
		this.body2 = root.getChild("body2");
		this.body3 = root.getChild("body3");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head1 = modelPartData.addChild("head1", ModelPartBuilder.create().uv(-8, -4).cuboid(-8.0F, -12.0F, -4.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head2 = modelPartData.addChild("head2", ModelPartBuilder.create().uv(24, -4).cuboid(-11.0F, -12.0F, -4.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 4.0F, 0.0F));

		ModelPartData head3 = modelPartData.addChild("head3", ModelPartBuilder.create().uv(24, -4).cuboid(-5.0F, -12.0F, -4.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(10.0F, 4.0F, 0.0F));

		ModelPartData body1 = modelPartData.addChild("body1", ModelPartBuilder.create().uv(-6, 16).cuboid(-13.0F, 3.9F, -0.5F, 26.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body2 = modelPartData.addChild("body2", ModelPartBuilder.create().uv(0, 22).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F))
		.uv(18, 22).cuboid(-7.0F, 1.5F, 0.5F, 17.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(18, 22).cuboid(-7.0F, 4.0F, 0.5F, 17.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(18, 22).cuboid(-7.0F, 6.5F, 0.5F, 17.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 6.9F, -0.5F));

		ModelPartData body3 = modelPartData.addChild("body3", ModelPartBuilder.create().uv(12, 22).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 16.9F, -0.5F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float f, float g, float h, float i, float j) {
		this.head2.yaw = i * (float) (Math.PI / 180.0);
		this.head2.pitch = j * (float) (Math.PI / 180.0);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		root.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}