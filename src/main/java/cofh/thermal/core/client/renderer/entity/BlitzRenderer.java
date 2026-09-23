package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.layers.FestiveLayer;
import cofh.thermal.core.client.renderer.entity.model.BlitzModel;
import cofh.thermal.core.common.entity.monster.Blitz;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BlitzRenderer extends MobRenderer<Blitz, BlitzRenderer.BlitzRenderState, BlitzModel> {

    private static final Identifier CALM_TEXTURE = Identifier.fromNamespaceAndPath(ID_THERMAL, "textures/entity/blitz.png");
    private static final Identifier ANGRY_TEXTURE = Identifier.fromNamespaceAndPath(ID_THERMAL, "textures/entity/blitz_angry.png");

    public BlitzRenderer(EntityRendererProvider.Context ctx) {

        super(ctx, new BlitzModel(ctx.bakeLayer(BlitzModel.BLITZ_LAYER)), 0.5F);
        this.addLayer(new FestiveLayer<>(ctx, this, -0.3F, 1.2F));
    }

    @Override
    protected int getBlockLightLevel(Blitz entityIn, BlockPos pos) {

        return entityIn.isAngry() ? 12 : super.getBlockLightLevel(entityIn, pos);
    }

    @Override
    public Identifier getTextureLocation(BlitzRenderState state) {

        return state.isAngry ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

    @Override
    public BlitzRenderState createRenderState() {

        return new BlitzRenderState();
    }

    @Override
    public void extractRenderState(Blitz entityIn, BlitzRenderState state, float partialTicks) {

        super.extractRenderState(entityIn, state, partialTicks);
        state.isAngry = entityIn.isAngry();
    }

    public static class BlitzRenderState extends LivingEntityRenderState {

        public boolean isAngry;

    }

}
