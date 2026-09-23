package cofh.thermal.core.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class SantaHatModel extends EntityModel<EntityRenderState> {

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation HAT_LAYER = new ModelLayerLocation(Identifier.parse("thermal:santa_hat"), "main");
    public static final Identifier TEXTURE = Identifier.parse(ID_THERMAL + ":textures/entity/santa_hat.png");

    public SantaHatModel(ModelPart root) {

        super(root);
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(30, 17).addBox(-1.0F, -5.0F, 6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(35, 17).addBox(-3.0F, -7.0F, -1.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 17).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

}
