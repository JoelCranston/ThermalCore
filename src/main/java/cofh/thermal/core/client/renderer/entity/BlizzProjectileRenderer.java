package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.common.entity.projectile.BlizzProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BlizzProjectileRenderer extends ElementalProjectileRenderer<BlizzProjectile> {

    public static final Identifier TEXTURE = Identifier.parse(ID_THERMAL + ":textures/entity/blizz_projectile.png");

    public BlizzProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public Identifier getTextureLocation(ElementalProjectileRenderState state) {

        return TEXTURE;
    }

}
