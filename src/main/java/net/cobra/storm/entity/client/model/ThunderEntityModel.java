package net.cobra.storm.entity.client.model;

import net.cobra.storm.entity.ThunderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ThunderEntityModel<T extends ThunderEntity> extends SinglePartEntityModel<T> {
	private final ModelPart thunder;
	private final ModelPart head;

	public ThunderEntityModel(ModelPart root) {
		this.thunder = root.getChild("thunder");
		this.head = thunder.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData thunder = modelPartData.addChild("thunder", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = thunder.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-13.5F, -32.0F, -6.75F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 0).mirrored().cuboid(5.5F, -32.0F, -6.75F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 0).cuboid(-6.0F, -29.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 0).mirrored().cuboid(4.0F, -29.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData stick_upper = thunder.addChild("stick_upper", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, -26.0F, -12.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(6.0F, -26.0F, -12.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(6.0F, -26.0F, 10.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-8.0F, -26.0F, 10.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-6.0F, -22.0F, -10.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(4.0F, -22.0F, -10.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(4.0F, -22.0F, 8.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-6.0F, -22.0F, 8.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData stick_lower = thunder.addChild("stick_lower", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -14.0F, -4.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(2.0F, -14.0F, -4.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(2.0F, -14.0F, 2.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-4.0F, -14.0F, 2.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(ThunderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25F, 45f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	@Override
	public ModelPart getPart() {
		return this.thunder;
	}
}