package cofh.thermal.core.client.renderer.entity.layers;

import cofh.thermal.core.client.renderer.entity.model.SantaHatModel;
import cofh.thermal.core.common.config.ThermalClientConfig;
import cofh.thermal.core.util.HolidayHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class FestiveLayer<S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends RenderLayer<S, M> {

    protected SantaHatModel santaHatModel;

    protected float offset;
    protected float widthScale;

    public FestiveLayer(EntityRendererProvider.Context ctx, RenderLayerParent<S, M> pRenderer) {

        this(ctx, pRenderer, 0.0F, 1.0F);
    }

    public FestiveLayer(EntityRendererProvider.Context ctx, RenderLayerParent<S, M> pRenderer, float offset, float widthScale) {

        super(pRenderer);
        this.santaHatModel = new SantaHatModel(ctx.bakeLayer(SantaHatModel.HAT_LAYER));

        this.offset = offset;
        this.widthScale = widthScale;
    }

    @Override
    public void submit(PoseStack pPoseStack, SubmitNodeCollector pCollector, int pPackedLight, S pState, float pNetHeadYaw, float pHeadPitch) {

        if (!ThermalClientConfig.festiveMobs.get() || !HolidayHelper.isChristmas(3, 2)) {
            return;
        }
        pPoseStack.pushPose();
        pPoseStack.translate(0, offset, 0);

        pPoseStack.scale(widthScale, 1.0F, widthScale);

        pPoseStack.mulPose(Axis.YP.rotationDegrees(pNetHeadYaw));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pHeadPitch));

        pCollector.submitModel(this.santaHatModel, pState, pPoseStack, SantaHatModel.TEXTURE, pPackedLight, OverlayTexture.NO_OVERLAY, pState.outlineColor, null);
        pPoseStack.popPose();
    }

}
