package cofh.thermal.core.client.renderer.entity.model;

import cofh.thermal.core.client.renderer.entity.ElementalProjectileRenderer.ElementalProjectileRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

public class ElementalProjectileModel extends EntityModel<ElementalProjectileRenderState> {

    public static final ModelLayerLocation PROJECTILE_LAYER = new ModelLayerLocation(Identifier.parse("thermal:elemental_projectile"), "main");

    protected final ModelPart cube;

    public ElementalProjectileModel(ModelPart root) {

        super(root);
        this.cube = root.getChild("cube");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("cube",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(ElementalProjectileRenderState state) {

        super.setupAnim(state);
        this.cube.yRot = state.yRot * ((float) Math.PI / 180F);
        this.cube.xRot = state.xRot * ((float) Math.PI / 180F);
    }

}
