package cofh.thermal.core.client.renderer.entity;

import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.common.entity.projectile.ElementalProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public abstract class ElementalProjectileRenderer<T extends ElementalProjectile> extends EntityRenderer<T, ElementalProjectileRenderer.ElementalProjectileRenderState> {

    protected final ElementalProjectileModel model;

    protected ElementalProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
        this.model = new ElementalProjectileModel(ctx.bakeLayer(ElementalProjectileModel.PROJECTILE_LAYER));
    }

    protected RenderType getRenderType(ElementalProjectileRenderState state) {

        return this.model.renderType(getTextureLocation(state));
    }

    public abstract Identifier getTextureLocation(ElementalProjectileRenderState state);

    @Override
    public void submit(ElementalProjectileRenderState state, PoseStack poseStackIn, SubmitNodeCollector collector, CameraRenderState camera) {

        poseStackIn.pushPose();
        float f2 = state.ageInTicks;
        poseStackIn.mulPose(Axis.YP.rotationDegrees(MathHelper.sin(f2 * 0.1F) * 180.0F));
        poseStackIn.mulPose(Axis.XP.rotationDegrees(MathHelper.cos(f2 * 0.1F) * 180.0F));
        poseStackIn.scale(0.5F, 0.5F, 0.5F);
        collector.submitModel(this.model, state, poseStackIn, getRenderType(state), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStackIn.popPose();

        super.submit(state, poseStackIn, collector, camera);
    }

    @Override
    public ElementalProjectileRenderState createRenderState() {

        return new ElementalProjectileRenderState();
    }

    @Override
    public void extractRenderState(T entityIn, ElementalProjectileRenderState state, float partialTicks) {

        super.extractRenderState(entityIn, state, partialTicks);
        state.yRot = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot());
        state.xRot = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
    }

    public static class ElementalProjectileRenderState extends EntityRenderState {

        public float yRot;
        public float xRot;

    }

}
