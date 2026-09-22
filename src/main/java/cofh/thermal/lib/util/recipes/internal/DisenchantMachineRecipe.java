package cofh.thermal.lib.util.recipes.internal;

import net.minecraft.tags.EnchantmentTags;
import net.minecraft.core.Holder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.thermal.lib.util.recipes.IMachineInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class DisenchantMachineRecipe extends BaseMachineRecipe {

    public DisenchantMachineRecipe(int energy, float experience) {

        super(energy, experience);
    }

    public DisenchantMachineRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
    }

    private int getEnchantmentXp(ItemStack stack) {

        // 1.21: enchantments are the ENCHANTMENTS component keyed by Holder, and "is a curse"
        // is the minecraft:curse tag rather than a flag on the enchantment.
        int encXP = 0;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getEnchantments().entrySet()) {
            if (!entry.getKey().is(EnchantmentTags.CURSE)) {
                encXP += entry.getKey().value().getMinCost(entry.getIntValue());
            }
        }
        return encXP;
    }

    // region IMachineRecipe
    @Override
    public float getXp(IMachineInventory inventory) {

        int encXP = 0;
        for (IItemStackHolder holder : inventory.inputSlots()) {
            encXP += getEnchantmentXp(holder.getItemStack());
        }
        return encXP + experience * inventory.getMachineProperties().getXpMod();
    }
    // endregion
}
