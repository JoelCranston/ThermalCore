package cofh.thermal.core.common.fluid;

import cofh.lib.common.fluid.FluidCoFH;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Supplier;

import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.toolsTab;
import static cofh.thermal.core.util.RegistrationHelper.itemProperties;
import static cofh.thermal.lib.util.ThermalIDs.ID_FLUID_TREE_OIL;

public class TreeOilFluid extends FluidCoFH {

    private static TreeOilFluid INSTANCE;

    public static TreeOilFluid instance() {

        if (INSTANCE == null) {
            INSTANCE = new TreeOilFluid();
        }
        return INSTANCE;
    }

    protected TreeOilFluid() {

        super(FLUIDS, ID_FLUID_TREE_OIL);

        bucket = toolsTab(1000, ITEMS.register(bucket(ID_FLUID_TREE_OIL), id -> new BucketItem(stillFluid.get(), itemProperties(id).craftRemainder(Items.BUCKET).stacksTo(1))));
    }

    @Override
    protected BaseFlowingFluid.Properties fluidProperties() {

        return new BaseFlowingFluid.Properties(type(), stillFluid, flowingFluid).bucket(bucket);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final Supplier<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_TREE_OIL, () -> new FluidType(FluidType.Properties.create()
            .density(900)
            .viscosity(1200)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

}
