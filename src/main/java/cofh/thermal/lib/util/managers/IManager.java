package cofh.thermal.lib.util.managers;

import net.minecraft.world.item.crafting.RecipeMap;

public interface IManager {

    default void config() {

    }

    void refresh(RecipeMap recipeMap);

}
