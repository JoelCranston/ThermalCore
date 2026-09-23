package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.common.entity.projectile.BasalzProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BasalzProjectileRenderer extends ElementalProjectileRenderer<BasalzProjectile> {

    public static final Identifier TEXTURE = Identifier.parse(ID_THERMAL + ":textures/entity/basalz_projectile.png");

    public BasalzProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public Identifier getTextureLocation(ElementalProjectileRenderState state) {

        return TEXTURE;
    }

}
