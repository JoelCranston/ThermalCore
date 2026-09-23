package cofh.thermal.core.client.renderer.entity;

import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.client.renderer.entity.ElementalProjectileRenderer.ElementalProjectileRenderState;
import cofh.thermal.core.client.renderer.entity.layers.FestiveLayer;
import cofh.thermal.core.client.renderer.entity.model.BasalzModel;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.common.entity.monster.Basalz;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BasalzRenderer extends MobRenderer<Basalz, BasalzRenderer.BasalzRenderState, BasalzModel> {

    private static final Identifier CALM_TEXTURE = Identifier.fromNamespaceAndPath(ID_THERMAL, "textures/entity/basalz.png");
    private static final Identifier ANGRY_TEXTURE = Identifier.fromNamespaceAndPath(ID_THERMAL, "textures/entity/basalz_angry.png");

    protected ElementalProjectileModel projectileModel;
    protected ElementalProjectileRenderState projectileState = new ElementalProjectileRenderState();

    public BasalzRenderer(EntityRendererProvider.Context ctx) {

        super(ctx, new BasalzModel(ctx.bakeLayer(BasalzModel.BASALZ_LAYER)), 0.5F);
        this.addLayer(new FestiveLayer<>(ctx, this, -0.25F, 1.25F));
        this.projectileModel = new ElementalProjectileModel(ctx.bakeLayer(ElementalProjectileModel.PROJECTILE_LAYER));
    }

    @Override
    public void submit(BasalzRenderState state, PoseStack poseStackIn, SubmitNodeCollector collector, CameraRenderState camera) {

        if (state.isAlive) {
            float scale = 1.0F - MathHelper.clamp((state.angerTime + state.partialTick) / Basalz.DEPLOY_TIME, 0.0F, 1.0F);
            scale = 1.0F - scale * scale * scale;
            if (!state.isAngry) {
                scale = 1.0F - scale;
            }
            if (scale > 0.0F) {
                poseStackIn.pushPose();
                float time = state.ageInTicks;
                poseStackIn.translate(0, state.boundingBoxHeight * (0.35F + 0.35F * scale), 0);
                poseStackIn.scale(scale, scale, scale);
                int orbit = state.orbit;
                float inv = 1.0F / orbit;
                Quaternionf rot = Axis.YP.rotationDegrees(360.0F * inv);
                poseStackIn.mulPose(Axis.YP.rotationDegrees(time * Math.max(36 * inv, 12)));
                for (int i = 0; i < orbit; ++i) {
                    poseStackIn.pushPose();
                    float t = time + i;
                    poseStackIn.translate(3, 0.5F * MathHelper.sin(time * 0.15708F - i * inv * 6.2832F), 0);
                    poseStackIn.mulPose(Axis.YP.rotationDegrees(MathHelper.sin(t * 0.1F) * 180.0F));
                    poseStackIn.mulPose(Axis.XP.rotationDegrees(MathHelper.cos(t * 0.1F) * 180.0F));
                    float invScale = 0.5F / scale;
                    poseStackIn.scale(invScale, invScale, invScale);
                    collector.submitModel(this.projectileModel, projectileState, poseStackIn, projectileModel.renderType(BasalzProjectileRenderer.TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, 0xCCFFFFFF, null, state.outlineColor, null);
                    poseStackIn.popPose();
                    poseStackIn.mulPose(rot);
                }
                poseStackIn.popPose();
            }
        }
        super.submit(state, poseStackIn, collector, camera);
    }

    @Override
    protected int getBlockLightLevel(Basalz entityIn, BlockPos pos) {

        return entityIn.isAngry() ? 12 : super.getBlockLightLevel(entityIn, pos);
    }

    @Override
    protected AABB getBoundingBoxForCulling(Basalz entity) {

        return entity.isAngry() ? super.getBoundingBoxForCulling(entity).inflate(4) : super.getBoundingBoxForCulling(entity);
    }

    @Override
    public Identifier getTextureLocation(BasalzRenderState state) {

        return state.isAngry ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

    @Override
    public BasalzRenderState createRenderState() {

        return new BasalzRenderState();
    }

    @Override
    public void extractRenderState(Basalz entityIn, BasalzRenderState state, float partialTicks) {

        super.extractRenderState(entityIn, state, partialTicks);
        state.isAlive = entityIn.isAlive();
        state.isAngry = entityIn.isAngry();
        state.angerTime = entityIn.angerTime;
        state.orbit = entityIn.getOrbit();
    }

    public static class BasalzRenderState extends LivingEntityRenderState {

        public boolean isAlive;
        public boolean isAngry;
        public int angerTime;
        public int orbit;

    }

}
