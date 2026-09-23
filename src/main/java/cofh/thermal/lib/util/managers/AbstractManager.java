package cofh.thermal.lib.util.managers;

import cofh.lib.util.crafting.ComparableItemStack;
import cofh.lib.util.crafting.ComparableItemStackNBT;
import cofh.lib.util.crafting.IngredientWithCount;
import cofh.lib.util.helpers.MathHelper;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.List;

public abstract class AbstractManager implements IManager {

    private static final int MIN_POWER = 1;
    private static final int MAX_POWER = 32000;

    protected int defaultEnergy;
    protected float defaultScale = 1.0F;

    protected int basePower = 20;

    protected AbstractManager() {

    }

    protected AbstractManager(int defaultEnergy) {

        this.defaultEnergy = defaultEnergy;
    }

    //    public AbstractManager setDefaultEnergy(int defaultEnergy) {
    //
    //        if (defaultEnergy > 0) {
    //            this.defaultEnergy = defaultEnergy;
    //        }
    //        return this;
    //    }

    public AbstractManager setDefaultScale(float defaultScale) {

        if (defaultScale > 0) {
            this.defaultScale = defaultScale;
        }
        return this;
    }

    public static ComparableItemStack makeComparable(ItemStack stack) {

        return new ComparableItemStack(stack);
    }

    public static ComparableItemStack makeNBTComparable(ItemStack stack) {

        return new ComparableItemStackNBT(stack);
    }

    public static List<ItemStack> getItems(Ingredient ingredient) {

        int count = ingredient.getCustomIngredient() instanceof IngredientWithCount counted ? counted.getCount() : 1;
        return ingredient.items().map(item -> new ItemStack(item, count)).toList();
    }

    public static ItemStack getResultItem(Recipe<?> recipe) {

        List<RecipeDisplay> displays = recipe.display();
        return displays.isEmpty() ? ItemStack.EMPTY : displays.getFirst().result().resolveForFirstStack(ContextMap.EMPTY);
    }

    public int getDefaultEnergy() {

        return defaultEnergy;
    }

    public float getDefaultScale() {

        return defaultScale;
    }

    public void setBasePower(int rate) {

        basePower = MathHelper.clamp(rate, getMinPower(), getMaxPower());
    }

    public int getBasePower() {

        return basePower;
    }

    public int getMinPower() {

        return MIN_POWER;
    }

    public int getMaxPower() {

        return MAX_POWER;
    }

}
