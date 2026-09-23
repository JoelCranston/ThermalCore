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

public class BlizzModel extends EntityModel<LivingEntityRenderState> {

    public static final ModelLayerLocation BLIZZ_LAYER = new ModelLayerLocation(Identifier.parse("thermal:blizz"), "main");
    private static final int CUBES = 4;

    private final ModelPart[] topCubes;
    private final ModelPart[] botCubes;
    private final ModelPart head;

    public BlizzModel(ModelPart root) {

        super(root);
        this.head = root.getChild("head");
        this.topCubes = new ModelPart[CUBES];
        this.botCubes = new ModelPart[CUBES];

        Arrays.setAll(this.topCubes, (num) -> root.getChild("cube_top_" + num));
        Arrays.setAll(this.botCubes, (num) -> root.getChild("cube_bot_" + num));
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.5F, -4.0F, -4.0F, 9.0F, 8.0F, 8.0F)
                        .texOffs(0, 16).addBox(-4.5F, -4.0F, -6.0F, 9.0F, 4.0F, 2.0F),
                PartPose.ZERO);

        CubeListBuilder topCube = CubeListBuilder.create()
                .texOffs(34, 8)
                .addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F);
        CubeListBuilder botcube = CubeListBuilder.create()
                .texOffs(34, 2)
                .addBox(-2.0F, 17.0F, -2.0F, 3.0F, 3.0F, 3.0F);

        for (int i = 0; i < CUBES; ++i) {
            partdefinition.addOrReplaceChild("cube_top_" + i, topCube, PartPose.ZERO);
            partdefinition.addOrReplaceChild("cube_bot_" + i, botcube, PartPose.ZERO);
        }
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {

        super.setupAnim(state);
        float x = MathHelper.bevel(state.ageInTicks * 0.05F);
        float z = MathHelper.bevel(state.ageInTicks * 0.05F + 1.0F);
        for (int i = 0; i < CUBES; ++i) {
            topCubes[i].x = x * -4.0F;
            topCubes[i].z = z * 4.0F;
            topCubes[i].y = MathHelper.sin(state.ageInTicks * 0.2F + i * 4);
            botCubes[i].x = x * 3.5F;
            botCubes[i].z = z * 3.5F;
            botCubes[i].y = MathHelper.sin(state.ageInTicks * 0.2F + i * 4 + 2);
            float temp = -x;
            x = z;
            z = temp;
        }
        this.head.yRot = state.yRot * ((float) Math.PI / 180F);
        this.head.xRot = state.xRot * ((float) Math.PI / 180F);
    }

}
