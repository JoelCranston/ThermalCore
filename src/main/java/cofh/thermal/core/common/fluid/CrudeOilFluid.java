package cofh.thermal.core.common.fluid;

import cofh.lib.common.fluid.FluidCoFH;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.function.Supplier;

import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.toolsTab;
import static cofh.thermal.core.util.RegistrationHelper.blockProperties;
import static cofh.thermal.core.util.RegistrationHelper.itemProperties;
import static cofh.thermal.lib.util.ThermalIDs.ID_FLUID_CRUDE_OIL;

public class CrudeOilFluid extends FluidCoFH {

    private static CrudeOilFluid INSTANCE;

    public static CrudeOilFluid instance() {

        if (INSTANCE == null) {
            INSTANCE = new CrudeOilFluid();
        }
        return INSTANCE;
    }

    protected CrudeOilFluid() {

        super(FLUIDS, ID_FLUID_CRUDE_OIL);

        particleColor = new Vector3f(0.05F, 0.05F, 0.05F);

        block = BLOCKS.register(fluid(ID_FLUID_CRUDE_OIL), id -> new FluidBlock(stillFluid, blockProperties(id).mapColor(MapColor.COLOR_BLACK).replaceable().noCollision().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable()));
        bucket = toolsTab(1000, ITEMS.register(bucket(ID_FLUID_CRUDE_OIL), id -> new BucketItem(stillFluid.get(), itemProperties(id).craftRemainder(Items.BUCKET).stacksTo(1))));
    }

    @Override
    protected BaseFlowingFluid.Properties fluidProperties() {

        return new BaseFlowingFluid.Properties(type(), stillFluid, flowingFluid).block(block).bucket(bucket).levelDecreasePerBlock(2);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final Supplier<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_CRUDE_OIL, () -> new FluidType(FluidType.Properties.create()
            .fallDistanceModifier(0F)
            .density(850)
            .viscosity(1400)
            .canDrown(true)
            .canSwim(false)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    // region CLIENT
    public static class ClientExtensions implements IClientFluidTypeExtensions {

        @Override
        public Identifier getRenderOverlayTexture(Minecraft mc) {

            return UNDERWATER_LOCATION;
        }

        @Override
        public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {

            fluidFogColor.set(instance().particleColor, 1.0F);
        }

        @Override
        public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {

            float farDistance = Math.min(4F, renderDistance * 16);

            fogData.environmentalStart = -8F;
            fogData.environmentalEnd = farDistance;
            fogData.skyEnd = farDistance;
            fogData.cloudEnd = farDistance;
        }

    }
    // endregion

    // region BLOCK CLASS
    public static class FluidBlock extends LiquidBlock {

        public FluidBlock(Supplier<? extends FlowingFluid> fluidSup, Properties properties) {

            super(fluidSup.get(), properties);
        }

    }
    // endregion
}
