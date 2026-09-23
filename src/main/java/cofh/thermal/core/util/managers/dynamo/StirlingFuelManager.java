package cofh.thermal.core.util.managers.dynamo;

import cofh.core.util.ProxyUtils;
import cofh.core.util.helpers.FluidHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.dynamo.StirlingFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.FuelValues;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.Constants.RF_PER_FURNACE_UNIT;
import static cofh.lib.util.Utils.getName;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.STIRLING_FUEL;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class StirlingFuelManager extends SingleItemFuelManager {

    private static final StirlingFuelManager INSTANCE = new StirlingFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static StirlingFuelManager instance() {

        return INSTANCE;
    }

    private StirlingFuelManager() {

        super(DEFAULT_ENERGY);
    }

    @Override
    public boolean validFuel(ItemStack input) {

        if (FluidHelper.hasFluidHandlerCap(input)) {
            return false;
        }
        return getEnergy(input) > 0;
    }

    @Override
    protected void clear() {

        fuelMap.clear();
        convertedFuels.clear();
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFurnaceFuel(stack);
    }

    public int getEnergyFurnaceFuel(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.getItem().getCraftingRemainder(stack) != null) {
            return 0;
        }
        FuelValues fuelValues = fuelValues();
        if (fuelValues == null) {
            return 0;
        }
        int energy = stack.getBurnTime(null, fuelValues) * RF_PER_FURNACE_UNIT;
        return energy >= MIN_ENERGY ? energy : 0;
    }

    protected static FuelValues fuelValues() {

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            return server.fuelValues();
        }
        Level level = ProxyUtils.getClientWorld();
        return level == null ? null : level.fuelValues();
    }

    // region IManager
    @Override
    public void refresh(RecipeMap recipeMap) {

        clear();
        var recipes = recipeMap.byType(STIRLING_FUEL.get());
        for (var entry : recipes) {
            addFuel(entry.value());
        }
        createConvertedRecipes(recipeMap);
    }
    // endregion

    // region CONVERSION
    protected List<RecipeHolder<StirlingFuel>> convertedFuels = new ArrayList<>();

    public List<RecipeHolder<StirlingFuel>> getConvertedFuels() {

        return convertedFuels;
    }

    protected void createConvertedRecipes(RecipeMap recipeMap) {

        ItemStack query;
        for (Item item : BuiltInRegistries.ITEM) {
            query = new ItemStack(item);
            try {
                if (getFuel(query) == null && validFuel(query)) {
                    convertedFuels.add(convert(query, getEnergy(query)));
                }
            } catch (Exception e) { // pokemon!
                ThermalCore.LOG.error(getRegistryName(query.getItem()) + " threw an exception when querying the fuel value as the mod author is doing non-standard things in their item code (possibly tag related). It may not display in JEI but should function as fuel.");
            }
        }
    }

    protected RecipeHolder<StirlingFuel> convert(ItemStack item, int energy) {

        return new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(ID_THERMAL, "stirling_" + getName(item))), new StirlingFuel(energy, singletonList(Ingredient.of(item.getItem())), emptyList()));
    }
    // endregion
}
