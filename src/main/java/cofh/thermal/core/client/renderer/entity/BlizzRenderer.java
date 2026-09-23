package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.layers.FestiveLayer;
import cofh.thermal.core.client.renderer.entity.model.BlizzModel;
import cofh.thermal.core.common.entity.monster.Blizz;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BlizzRenderer extends MobRenderer<Blizz, BlizzRenderer.BlizzRenderState, BlizzModel> {

    private static final Identifier CALM_TEXTURE = Identifier.fromNamespaceAndPath(ID_THERMAL, "textures/entity/blizz.png");
    private static final Identifier ANGRY_TEXTURE = Identifier.fromNamespaceAndPath(ID_THERMAL, "textures/entity/blizz_angry.png");

    public BlizzRenderer(EntityRendererProvider.Context ctx) {

        super(ctx, new BlizzModel(ctx.bakeLayer(BlizzModel.BLIZZ_LAYER)), 0.5F);
        this.addLayer(new FestiveLayer<>(ctx, this, -0.25F, 1.0F));
    }

    @Override
    protected int getBlockLightLevel(Blizz entityIn, BlockPos pos) {

        return entityIn.isAngry() ? 7 : super.getBlockLightLevel(entityIn, pos);
    }

    @Override
    public Identifier getTextureLocation(BlizzRenderState state) {

        return state.isAngry ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

    @Override
    public BlizzRenderState createRenderState() {

        return new BlizzRenderState();
    }

    @Override
    public void extractRenderState(Blizz entityIn, BlizzRenderState state, float partialTicks) {

        super.extractRenderState(entityIn, state, partialTicks);
        state.isAngry = entityIn.isAngry();
    }

    public static class BlizzRenderState extends LivingEntityRenderState {

        public boolean isAngry;

    }

}
