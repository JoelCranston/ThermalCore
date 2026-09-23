package cofh.thermal.core.compat.patchouli;

import cofh.thermal.lib.util.ThermalRecipeManagers;
import cofh.thermal.lib.util.managers.AbstractManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cofh.thermal.lib.util.managers.AbstractManager.getResultItem;

public class CraftingProcessor implements IComponentProcessor {

    private CraftingRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {

        if (!variables.has("recipe"))
            return;
        Identifier recipeId = Identifier.parse(variables.get("recipe", level.registryAccess()).asString());
        RecipeHolder<?> recipe = ThermalRecipeManagers.instance().getClientRecipeMap().byKey(ResourceKey.create(Registries.RECIPE, recipeId));
        if (recipe != null && recipe.value() instanceof CraftingRecipe) {
            this.recipe = (CraftingRecipe) recipe.value();
        } else {
            LogManager.getLogger().warn("Thermalpedia missing the crafting recipe: " + recipeId);
        }
    }

    @Override
    public IVariable process(Level level, String key) {

        if (recipe == null) {
            return null;
        }
        if (key.equals("out")) {
            return IVariable.from(getResultItem(recipe), level.registryAccess());
        } else if (key.startsWith("in")) {
            int index = Integer.parseInt(key.substring(key.length() - 1));
            if (recipe instanceof ShapedRecipe) {
                int width = ((ShapedRecipe) recipe).getWidth();
                if (width < 3) {
                    if (index % 3 >= width) {
                        return null;
                    }
                    index = index * width / 3 + index % 3;
                }
            }
            List<Optional<Ingredient>> ingredients = recipe instanceof ShapedRecipe shaped ? shaped.getIngredients() : recipe.placementInfo().ingredients().stream().map(Optional::of).toList();
            if (ingredients.size() <= index) {
                return null;
            }
            List<ItemStack> stacks = ingredients.get(index).map(AbstractManager::getItems).orElse(List.of());
            return IVariable.wrapList(stacks.stream().map(stack -> IVariable.from(stack, level.registryAccess())).collect(Collectors.toList()), level.registryAccess());
        } else if (key.equals("title")) {
            return IVariable.from(getResultItem(recipe).getHoverName(), level.registryAccess());
        } else if (key.equals("show")) {
            return IVariable.wrap(true);
        }
        return null;
    }

}
