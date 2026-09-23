package cofh.thermal.lib.util.recipes;

import cofh.lib.common.fluid.FluidIngredient;
import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.ThermalCore;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.Constants.BASE_CHANCE_LOCKED;

public abstract class ThermalRecipe extends SerializableRecipe {

    protected final List<Ingredient> inputItems = new ArrayList<>();
    protected final List<FluidIngredient> inputFluids = new ArrayList<>();

    protected final List<ItemStackTemplate> outputItems = new ArrayList<>();
    protected final List<FluidStackTemplate> outputFluids = new ArrayList<>();
    protected final List<Float> outputItemChances = new ArrayList<>();

    private List<ItemStack> outputItemStacks;
    private List<FluidStack> outputFluidStacks;

    protected int energy;
    protected float xp;

    protected boolean catalyzable;

    protected ThermalRecipe(int energy, float xp, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStackTemplate> outputItems, List<Float> outputItemChances, List<FluidStackTemplate> outputFluids) {

        if ((inputItems == null || inputItems.isEmpty()) && (inputFluids == null || inputFluids.isEmpty()) || (outputItems == null || outputItems.isEmpty()) && (outputFluids == null || outputFluids.isEmpty())) {
            ThermalCore.LOG.warn("Invalid Thermal Series recipe! Please check your datapacks!");
        }
        this.energy = energy;
        this.xp = Math.max(0.0F, xp);

        if (inputItems != null) {
            this.inputItems.addAll(inputItems);
        }
        if (inputFluids != null) {
            this.inputFluids.addAll(inputFluids);
        }
        if (outputItems != null) {
            this.outputItems.addAll(outputItems);

            if (outputItemChances != null) {
                this.outputItemChances.addAll(outputItemChances);
            }
            if (this.outputItemChances.size() < this.outputItems.size()) {
                for (int i = this.outputItemChances.size(); i < this.outputItems.size(); ++i) {
                    this.outputItemChances.add(BASE_CHANCE_LOCKED);
                }
            }
            for (float f : this.outputItemChances) {
                catalyzable |= f >= 0.0F;
            }
        }
        if (outputFluids != null) {
            this.outputFluids.addAll(outputFluids);
        }
        trim();
    }

    private void trim() {

        ((ArrayList<Ingredient>) this.inputItems).trimToSize();
        ((ArrayList<FluidIngredient>) this.inputFluids).trimToSize();

        ((ArrayList<ItemStackTemplate>) this.outputItems).trimToSize();
        ((ArrayList<FluidStackTemplate>) this.outputFluids).trimToSize();
        ((ArrayList<Float>) this.outputItemChances).trimToSize();
    }

    // region GETTERS
    public List<Ingredient> getInputItems() {

        return inputItems;
    }

    public List<FluidIngredient> getInputFluids() {

        return inputFluids;
    }

    public List<ItemStackTemplate> getOutputItemTemplates() {

        return outputItems;
    }

    public List<FluidStackTemplate> getOutputFluidTemplates() {

        return outputFluids;
    }

    // Stacks are only created on demand; default components are not bound while recipes load.
    public List<ItemStack> getOutputItems() {

        if (outputItemStacks == null) {
            List<ItemStack> stacks = new ArrayList<>(outputItems.size());
            for (ItemStackTemplate template : outputItems) {
                stacks.add(template.create());
            }
            outputItemStacks = stacks;
        }
        return outputItemStacks;
    }

    public List<FluidStack> getOutputFluids() {

        if (outputFluidStacks == null) {
            List<FluidStack> stacks = new ArrayList<>(outputFluids.size());
            for (FluidStackTemplate template : outputFluids) {
                stacks.add(template.create());
            }
            outputFluidStacks = stacks;
        }
        return outputFluidStacks;
    }

    public List<Float> getOutputItemChances() {

        return outputItemChances;
    }

    public int getEnergy() {

        return energy;
    }

    public float getXp() {

        return xp;
    }

    public boolean isCatalyzable() {

        return catalyzable;
    }
    // endregion
}
