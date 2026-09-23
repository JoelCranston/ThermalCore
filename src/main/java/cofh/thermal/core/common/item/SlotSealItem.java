package cofh.thermal.core.common.item;

import cofh.core.common.item.ItemCoFH;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;

public class SlotSealItem extends ItemCoFH {

    public SlotSealItem(Properties builder) {

        super(builder);
    }

    @Override
    public int hashCode() {

        return 0;
    }

    @Override
    public ItemStackTemplate getCraftingRemainder(ItemInstance stack) {

        return new ItemStackTemplate(this);
    }

}
