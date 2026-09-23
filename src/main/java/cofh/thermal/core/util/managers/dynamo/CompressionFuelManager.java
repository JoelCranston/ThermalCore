package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleFluidFuelManager;
import net.minecraft.world.item.crafting.RecipeMap;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.COMPRESSION_FUEL;

public class CompressionFuelManager extends SingleFluidFuelManager {

    private static final CompressionFuelManager INSTANCE = new CompressionFuelManager();
    protected static final int DEFAULT_ENERGY = 100000;

    public static CompressionFuelManager instance() {

        return INSTANCE;
    }

    private CompressionFuelManager() {

        super(DEFAULT_ENERGY);
    }

    // region IManager
    @Override
    public void refresh(RecipeMap recipeMap) {

        clear();
        var recipes = recipeMap.byType(COMPRESSION_FUEL.get());
        for (var entry : recipes) {
            addFuel(entry.value());
        }
    }
    // endregion
}
