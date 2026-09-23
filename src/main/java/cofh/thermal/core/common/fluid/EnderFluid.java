package cofh.thermal.core.common.fluid;

import cofh.lib.common.fluid.FluidCoFH;
import cofh.lib.util.Utils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
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

import static cofh.lib.util.Utils.itemProperties;
import static cofh.lib.util.helpers.BlockHelper.lightValue;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.toolsTab;
import static cofh.thermal.lib.util.ThermalIDs.ID_FLUID_ENDER;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

public class EnderFluid extends FluidCoFH {

    private static EnderFluid INSTANCE;

    public static EnderFluid instance() {

        if (INSTANCE == null) {
            INSTANCE = new EnderFluid();
        }
        return INSTANCE;
    }

    protected EnderFluid() {

        super(FLUIDS, ID_FLUID_ENDER);

        particleColor = new Vector3f(0.035F, 0.215F, 0.333F);

        block = BLOCKS.register(fluid(ID_FLUID_ENDER), () -> new FluidBlock(stillFluid, of().mapColor(MapColor.COLOR_CYAN).lightLevel(lightValue(3)).replaceable().noCollision().strength(1200.0F).pushReaction(PushReaction.DESTROY).noLootTable()));
        bucket = toolsTab(1000, ITEMS.register(bucket(ID_FLUID_ENDER), () -> new BucketItem(stillFluid.get(), itemProperties().craftRemainder(Items.BUCKET).stacksTo(1))));
    }

    @Override
    protected BaseFlowingFluid.Properties fluidProperties() {

        return new BaseFlowingFluid.Properties(type(), stillFluid, flowingFluid).block(block).bucket(bucket).levelDecreasePerBlock(2);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final Supplier<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_ENDER, () -> new FluidType(FluidType.Properties.create()
            .lightLevel(3)
            .density(4000)
            .viscosity(2500)
            .rarity(Rarity.UNCOMMON)
            .canDrown(true)
            .canSwim(false)
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

        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {

            if (entity instanceof ItemEntity || entity instanceof ExperienceOrb) {
                return;
            }
            if (level.getGameTime() % 8 == 0) {
                BlockPos randPos = pos.offset(-8 + level.getRandom().nextInt(17), level.getRandom().nextInt(8), -8 + level.getRandom().nextInt(17));

                if (!level.getBlockState(randPos).isSolid()) {
                    if (entity instanceof LivingEntity) {
                        Utils.teleportEntityTo(entity, randPos);
                    } else {
                        entity.setPos(pos.getX(), pos.getY(), pos.getZ());
                        entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    }
                }
            }

        }

    }
    // endregion
}
