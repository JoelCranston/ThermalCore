package cofh.thermal.core.client.renderer.entity.model;

import cofh.lib.util.helpers.MathHelper;
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

public class BasalzModel extends EntityModel<LivingEntityRenderState> {

    public static final ModelLayerLocation BASALZ_LAYER = new ModelLayerLocation(Identifier.parse("thermal:basalz"), "main");
    private static final int PILLARS = 4;

    private final ModelPart head;
    private final ModelPart core;
    private final ModelPart[] pillars;

    public BasalzModel(ModelPart root) {

        super(root);
        this.head = root.getChild("head");
        this.core = root.getChild("core");
        this.pillars = new ModelPart[4];

        Arrays.setAll(this.pillars, (num) -> root.getChild("pillar_" + num));
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("core",
                CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-4.0F, 10.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        for (int i = 0; i < PILLARS; ++i) {
            int odd = i & 1;
            partdefinition.addOrReplaceChild("pillar_" + i,
                    CubeListBuilder.create()
                            .texOffs(odd * 20, 38).addBox(((i + 1 & 2) >> 1) * 17 - 11, 6.0F + i, odd * 17 - 11, 5.0F, 13.0F, 5.0F, (i & 2) > 0),
                    PartPose.ZERO);
        }
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {

        super.setupAnim(state);
        core.y = MathHelper.sin(state.ageInTicks * 0.1F);

        for (ModelPart pillar : pillars) {
            pillar.yRot = (state.ageInTicks * 6 - state.bodyRot) * (float) Math.PI / 180.0F;
        }
        this.head.yRot = state.yRot * ((float) Math.PI / 180F);
        this.head.xRot = state.xRot * ((float) Math.PI / 180F);
    }

}
