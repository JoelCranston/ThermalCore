package cofh.thermal.core.util.recipes.machine;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;

import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.INSOLATOR_CATALYST_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.INSOLATOR_CATALYST;

public class InsolatorCatalyst extends ThermalCatalyst {

    public InsolatorCatalyst(Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        super(ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Nonnull
    @Override
    public RecipeSerializer<InsolatorCatalyst> getSerializer() {

        return INSOLATOR_CATALYST_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<InsolatorCatalyst> getType() {

        return INSOLATOR_CATALYST.get();
    }

}
