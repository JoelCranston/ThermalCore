package cofh.thermal.core.common.fluid;

import cofh.lib.common.fluid.FluidCoFH;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
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

import static cofh.lib.util.helpers.BlockHelper.lightValue;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.toolsTab;
import static cofh.thermal.core.util.RegistrationHelper.blockProperties;
import static cofh.thermal.core.util.RegistrationHelper.itemProperties;
import static cofh.thermal.lib.util.ThermalIDs.ID_FLUID_REDSTONE;

public class RedstoneFluid extends FluidCoFH {

    private static RedstoneFluid INSTANCE;

    public static RedstoneFluid instance() {

        if (INSTANCE == null) {
            INSTANCE = new RedstoneFluid();
        }
        return INSTANCE;
    }

    protected RedstoneFluid() {

        super(FLUIDS, ID_FLUID_REDSTONE);

        particleColor = new Vector3f(0.4F, 0.0F, 0.0F);

        block = BLOCKS.register(fluid(ID_FLUID_REDSTONE), id -> new FluidBlock(stillFluid, blockProperties(id).mapColor(MapColor.COLOR_RED).lightLevel(lightValue(7)).replaceable().noCollision().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable()));
        bucket = toolsTab(1000, ITEMS.register(bucket(ID_FLUID_REDSTONE), id -> new BucketItem(stillFluid.get(), itemProperties(id).craftRemainder(Items.BUCKET).stacksTo(1))));
    }

    @Override
    protected BaseFlowingFluid.Properties fluidProperties() {

        return new BaseFlowingFluid.Properties(type(), stillFluid, flowingFluid).block(block).bucket(bucket);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final Supplier<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_REDSTONE, () -> new FluidType(FluidType.Properties.create()
            .fallDistanceModifier(0F)
            .lightLevel(7)
            .density(1200)
            .viscosity(1500)
            .rarity(Rarity.UNCOMMON)
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

            float farDistance = Math.min(8F, renderDistance * 16);

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

        @Override
        public boolean isSignalSource(BlockState state) {

            return true;
            // return redstoneMushroomSignal.get();
        }

        @Override
        public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {

            return Math.max(15 - 2 * blockState.getValue(LEVEL), 1);
            // return redstoneMushroomSignal.get() && blockState.getValue(AGE_0_4) == 4 ? 7 : 0;
        }

    }
    // endregion
}
