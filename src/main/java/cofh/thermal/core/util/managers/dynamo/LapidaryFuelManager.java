package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import net.minecraft.world.item.crafting.RecipeMap;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.LAPIDARY_FUEL;

public class LapidaryFuelManager extends SingleItemFuelManager {

    private static final LapidaryFuelManager INSTANCE = new LapidaryFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static LapidaryFuelManager instance() {

        return INSTANCE;
    }

    private LapidaryFuelManager() {

        super(DEFAULT_ENERGY);
    }

    // region IManager
    @Override
    public void refresh(RecipeMap recipeMap) {

        clear();
        var recipes = recipeMap.byType(LAPIDARY_FUEL.get());
        for (var entry : recipes) {
            addFuel(entry.value());
        }
    }
    // endregion
}
