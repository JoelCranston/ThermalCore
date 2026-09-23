package cofh.thermal.core.common.item;

import cofh.core.common.event.ArmorEvents;
import cofh.core.common.item.ArmorItemCoFH;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Consumer;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public class BeekeeperArmorItem extends ArmorItemCoFH {

    public BeekeeperArmorItem(ArmorMaterial pMaterial, ArmorType pType, Item.Properties pProperties) {

        super(pMaterial, pType, pProperties);

        ArmorEvents.registerStingResistArmor(this, RESISTANCE_RATIO[getType().getSlot().getIndex()]);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flagIn) {

        tooltip.accept(getTextComponent("info.thermal.beekeeper_armor").withStyle(ChatFormatting.GOLD));
    }

}
