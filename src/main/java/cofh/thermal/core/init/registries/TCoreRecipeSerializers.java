package cofh.thermal.core.init.registries;

import cofh.thermal.core.util.managers.dynamo.*;
import cofh.thermal.core.util.managers.machine.*;
import cofh.thermal.core.util.recipes.device.*;
import cofh.thermal.core.util.recipes.dynamo.*;
import cofh.thermal.core.util.recipes.machine.*;
import cofh.thermal.lib.util.recipes.DynamoFuelSerializer;
import cofh.thermal.lib.util.recipes.MachineCatalystSerializer;
import cofh.thermal.lib.util.recipes.MachineRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.*;

public class TCoreRecipeSerializers {

    private TCoreRecipeSerializers() {

    }

    public static void register() {

    }

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HiveExtractorMapping>> HIVE_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(ID_HIVE_EXTRACTOR_MAPPING, () -> new RecipeSerializer<>(HiveExtractorMapping.CODEC, HiveExtractorMapping.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TreeExtractorMapping>> TREE_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(ID_TREE_EXTRACTOR_MAPPING, () -> new RecipeSerializer<>(TreeExtractorMapping.CODEC, TreeExtractorMapping.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TreeExtractorBoost>> TREE_EXTRACTOR_BOOST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_TREE_EXTRACTOR_BOOST, () -> new RecipeSerializer<>(TreeExtractorBoost.CODEC, TreeExtractorBoost.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FisherBoost>> FISHER_BOOST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_FISHER_BOOST, () -> new RecipeSerializer<>(FisherBoost.CODEC, FisherBoost.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RockGenMapping>> ROCK_GEN_SERIALIZER = RECIPE_SERIALIZERS.register(ID_ROCK_GEN_MAPPING, () -> new RecipeSerializer<>(RockGenMapping.CODEC, RockGenMapping.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PotionDiffuserBoost>> POTION_DIFFUSER_BOOST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_POTION_DIFFUSER_BOOST, () -> new RecipeSerializer<>(PotionDiffuserBoost.CODEC, PotionDiffuserBoost.STREAM_CODEC));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FurnaceRecipe>> FURNACE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_FURNACE_RECIPE, () -> new MachineRecipeSerializer<>(FurnaceRecipe::new, FurnaceRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SawmillRecipe>> SAWMILL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SAWMILL_RECIPE, () -> new MachineRecipeSerializer<>(SawmillRecipe::new, SawmillRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PulverizerRecipe>> PULVERIZER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PULVERIZER_RECIPE, () -> new MachineRecipeSerializer<>(PulverizerRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PulverizerRecycleRecipe>> PULVERIZER_RECYCLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PULVERIZER_RECYCLE_RECIPE, () -> new MachineRecipeSerializer<>(PulverizerRecycleRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SmelterRecipe>> SMELTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SMELTER_RECIPE, () -> new MachineRecipeSerializer<>(SmelterRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SmelterRecycleRecipe>> SMELTER_RECYCLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SMELTER_RECYCLE_RECIPE, () -> new MachineRecipeSerializer<>(SmelterRecycleRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<InsolatorRecipe>> INSOLATOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_INSOLATOR_RECIPE, () -> new InsolatorRecipe.Serializer<>(InsolatorRecipe::new, InsolatorRecipeManager.instance().getDefaultEnergy(), InsolatorRecipeManager.instance().getDefaultWater()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CENTRIFUGE_RECIPE, () -> new MachineRecipeSerializer<>(CentrifugeRecipe::new, CentrifugeRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PressRecipe>> PRESS_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PRESS_RECIPE, () -> new MachineRecipeSerializer<>(PressRecipe::new, PressRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrucibleRecipe>> CRUCIBLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CRUCIBLE_RECIPE, () -> new MachineRecipeSerializer<>(CrucibleRecipe::new, CrucibleRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ChillerRecipe>> CHILLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CHILLER_RECIPE, () -> new MachineRecipeSerializer<>(ChillerRecipe::new, ChillerRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RefineryRecipe>> REFINERY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_REFINERY_RECIPE, () -> new MachineRecipeSerializer<>(RefineryRecipe::new, RefineryRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PyrolyzerRecipe>> PYROLYZER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PYROLYZER_RECIPE, () -> new MachineRecipeSerializer<>(PyrolyzerRecipe::new, PyrolyzerRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BottlerRecipe>> BOTTLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_BOTTLER_RECIPE, () -> new MachineRecipeSerializer<>(BottlerRecipe::new, BottlerRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BrewerRecipe>> BREWER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_BREWER_RECIPE, () -> new MachineRecipeSerializer<>(BrewerRecipe::new, BrewerRecipeManager.instance().getDefaultEnergy()).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrystallizerRecipe>> CRYSTALLIZER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CRYSTALLIZER_RECIPE, () -> new MachineRecipeSerializer<>(CrystallizerRecipe::new, CrystallizerRecipeManager.instance().getDefaultEnergy()).toVanilla());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PulverizerCatalyst>> PULVERIZER_CATALYST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PULVERIZER_CATALYST, () -> new MachineCatalystSerializer<>(PulverizerCatalyst::new).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SmelterCatalyst>> SMELTER_CATALYST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SMELTER_CATALYST, () -> new MachineCatalystSerializer<>(SmelterCatalyst::new).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<InsolatorCatalyst>> INSOLATOR_CATALYST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_INSOLATOR_CATALYST, () -> new MachineCatalystSerializer<>(InsolatorCatalyst::new).toVanilla());

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<StirlingFuel>> STIRLING_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_STIRLING_FUEL, () -> new DynamoFuelSerializer<>(StirlingFuel::new, StirlingFuelManager.instance().getDefaultEnergy(), StirlingFuelManager.MIN_ENERGY, StirlingFuelManager.MAX_ENERGY).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CompressionFuel>> COMPRESSION_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_COMPRESSION_FUEL, () -> new DynamoFuelSerializer<>(CompressionFuel::new, CompressionFuelManager.instance().getDefaultEnergy(), CompressionFuelManager.MIN_ENERGY, CompressionFuelManager.MAX_ENERGY).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagmaticFuel>> MAGMATIC_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_MAGMATIC_FUEL, () -> new DynamoFuelSerializer<>(MagmaticFuel::new, MagmaticFuelManager.instance().getDefaultEnergy(), MagmaticFuelManager.MIN_ENERGY, MagmaticFuelManager.MAX_ENERGY).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<NumismaticFuel>> NUMISMATIC_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_NUMISMATIC_FUEL, () -> new DynamoFuelSerializer<>(NumismaticFuel::new, NumismaticFuelManager.instance().getDefaultEnergy(), NumismaticFuelManager.MIN_ENERGY, NumismaticFuelManager.MAX_ENERGY).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<LapidaryFuel>> LAPIDARY_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_LAPIDARY_FUEL, () -> new DynamoFuelSerializer<>(LapidaryFuel::new, LapidaryFuelManager.instance().getDefaultEnergy(), LapidaryFuelManager.MIN_ENERGY, LapidaryFuelManager.MAX_ENERGY).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DisenchantmentFuel>> DISENCHANTMENT_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_DISENCHANTMENT_FUEL, () -> new DynamoFuelSerializer<>(DisenchantmentFuel::new, DisenchantmentFuelManager.instance().getDefaultEnergy(), DisenchantmentFuelManager.MIN_ENERGY, DisenchantmentFuelManager.MAX_ENERGY).toVanilla());
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GourmandFuel>> GOURMAND_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_GOURMAND_FUEL, () -> new DynamoFuelSerializer<>(GourmandFuel::new, GourmandFuelManager.instance().getDefaultEnergy(), GourmandFuelManager.MIN_ENERGY, GourmandFuelManager.MAX_ENERGY).toVanilla());

}
