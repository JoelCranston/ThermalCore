package cofh.thermal.core.util.recipes.machine;

import cofh.lib.common.fluid.FluidIngredient;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.managers.machine.SmelterRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.SMELTER_RECIPE_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.SMELTER_RECIPE;

public class SmelterRecipe extends ThermalRecipe {

    public SmelterRecipe(int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStackTemplate> outputItems, List<Float> outputItemChances, List<FluidStackTemplate> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);

        if (this.energy <= 0) {
            int defaultEnergy = SmelterRecipeManager.instance().getDefaultEnergy();
            ThermalCore.LOG.warn("Energy value for an Induction Smelter recipe was out of allowable range and has been set to a default value of " + defaultEnergy + ".");
            this.energy = defaultEnergy;
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<? extends SmelterRecipe> getSerializer() {

        return SMELTER_RECIPE_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<? extends SmelterRecipe> getType() {

        return SMELTER_RECIPE.get();
    }

}
