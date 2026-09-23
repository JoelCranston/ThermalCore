package cofh.thermal.lib.util;

import cofh.thermal.lib.util.managers.IManager;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;

import java.util.ArrayList;
import java.util.List;

public class ThermalRecipeManagers {

    private static final ThermalRecipeManagers INSTANCE = new ThermalRecipeManagers();

    private RecipeMap clientRecipeMap;
    private RecipeManager serverRecipeManager;
    private final List<IManager> managers = new ArrayList<>();

    public static ThermalRecipeManagers instance() {

        return INSTANCE;
    }

    public void setClientRecipeMap(RecipeMap recipeMap) {

        this.clientRecipeMap = recipeMap;
    }

    public void setServerRecipeManager(RecipeManager recipeManager) {

        this.serverRecipeManager = recipeManager;
    }

    public RecipeMap getClientRecipeMap() {

        return clientRecipeMap == null ? RecipeMap.EMPTY : clientRecipeMap;
    }

    public static void registerManager(IManager manager) {

        if (!instance().managers.contains(manager)) {
            instance().managers.add(manager);
        }
    }

    public void config() {

        for (IManager sub : managers) {
            sub.config();
        }
    }

    public void refreshServer() {

        if (this.serverRecipeManager == null) {
            return;
        }
        for (IManager sub : managers) {
            sub.refresh(this.serverRecipeManager.recipeMap());
        }
    }

    public void refreshClient() {

        if (this.clientRecipeMap == null) {
            return;
        }
        for (IManager sub : managers) {
            sub.refresh(this.clientRecipeMap);
        }
    }
    // endregion
}
