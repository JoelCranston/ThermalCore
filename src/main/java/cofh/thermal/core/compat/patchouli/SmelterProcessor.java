package cofh.thermal.core.compat.patchouli;

import cofh.thermal.core.util.recipes.machine.SmelterRecipe;
import cofh.thermal.lib.util.ThermalRecipeManagers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.stream.Collectors;

import static cofh.thermal.lib.util.managers.AbstractManager.getItems;

public class SmelterProcessor implements IComponentProcessor {

    private SmelterRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {

        if (!variables.has("recipe"))
            return;
        Identifier recipeId = Identifier.parse(variables.get("recipe", level.registryAccess()).asString());
        RecipeHolder<?> recipe = ThermalRecipeManagers.instance().getClientRecipeMap().byKey(ResourceKey.create(Registries.RECIPE, recipeId));
        if (recipe != null && recipe.value() instanceof SmelterRecipe) {
            this.recipe = (SmelterRecipe) recipe.value();
        } else {
            LogManager.getLogger().warn("Thermalpedia missing the smelter recipe: " + recipeId);
        }
    }

    @Override
    public IVariable process(Level level, String key) {

        if (recipe == null)
            return null;
        if (key.equals("out"))
            return IVariable.from(recipe.getOutputItems().get(0), level.registryAccess());
        if (key.startsWith("in")) {
            int index = Integer.parseInt(key.substring(key.length() - 1)) - 1;
            if (recipe.getInputItems().size() <= index)
                return null;
            return IVariable.wrapList(getItems(recipe.getInputItems().get(index)).stream().map(stack -> IVariable.from(stack, level.registryAccess())).collect(Collectors.toList()), level.registryAccess());
        }
        return null;
    }

}
