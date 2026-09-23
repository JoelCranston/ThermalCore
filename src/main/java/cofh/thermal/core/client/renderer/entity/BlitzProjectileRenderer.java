package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.common.entity.projectile.BlitzProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BlitzProjectileRenderer extends ElementalProjectileRenderer<BlitzProjectile> {

    public static final Identifier TEXTURE = Identifier.parse(ID_THERMAL + ":textures/entity/blitz_projectile.png");

    public BlitzProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    protected RenderType getRenderType(ElementalProjectileRenderState state) {

        return RenderTypes.entityTranslucent(TEXTURE);
    }

    @Override
    public Identifier getTextureLocation(ElementalProjectileRenderState state) {

        return TEXTURE;
    }

}

