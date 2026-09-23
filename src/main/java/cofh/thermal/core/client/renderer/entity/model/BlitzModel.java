package cofh.thermal.core.client.renderer.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

import java.util.Arrays;

public class BlitzModel extends EntityModel<LivingEntityRenderState> {

    public static final ModelLayerLocation BLITZ_LAYER = new ModelLayerLocation(Identifier.parse("thermal:blitz"), "main");

    private final ModelPart head;
    private final ModelPart[] body;
    private final ModelPart cyclone;

    public BlitzModel(ModelPart root) {

        super(root);
        this.head = root.getChild("head");
        this.body = new ModelPart[3];
        this.cyclone = root.getChild("cyclone");

        Arrays.setAll(this.body, (num) -> root.getChild("body_" + num));
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 39).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("body_0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8.0F, 2.0F, -8.0F, 16.0F, 6.0F, 16.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("body_1",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-5.0F, 9.0F, -5.0F, 10.0F, 6.0F, 10.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("body_2",
                CubeListBuilder.create()
                        .texOffs(40, 27).addBox(-3.0F, 16.0F, -3.0F, 6.0F, 6.0F, 6.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("cyclone",
                CubeListBuilder.create()
                        .texOffs(15, 39).addBox(-12.5F, 23.0F, -12.5F, 25.0F, 0.0F, 25.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {

        super.setupAnim(state);
        for (int i = 0; i < this.body.length; ++i) {
            this.body[i].yRot = (state.ageInTicks * (i + 1) * 10 - state.bodyRot) * (float) Math.PI / 180.0F;
        }
        cyclone.yRot = (state.ageInTicks * 20 - state.bodyRot) * (float) Math.PI / 180.0F;

        this.head.yRot = state.yRot * ((float) Math.PI / 180F);
        this.head.xRot = state.xRot * ((float) Math.PI / 180F);
    }

}
