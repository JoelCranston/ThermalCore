package cofh.thermal.core.common.item;

import cofh.core.common.event.ArmorEvents;
import cofh.core.common.item.ArmorItemCoFH;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public class HazmatArmorItem extends ArmorItemCoFH {

    public HazmatArmorItem(ArmorMaterial pMaterial, ArmorType pType, Item.Properties pProperties) {

        super(pMaterial, pType, pProperties);

        ArmorEvents.registerHazardResistArmor(this, RESISTANCE_RATIO[getType().getSlot().getIndex()]);
        if (getType().getSlot() == EquipmentSlot.FEET) {
            ArmorEvents.registerFallResistArmor(this, 6.0D);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flagIn) {

        tooltip.accept(getTextComponent("info.thermal.hazmat_armor").withStyle(ChatFormatting.GOLD));

        if (getType().getSlot() == EquipmentSlot.HEAD) {
            tooltip.accept(getTextComponent("info.thermal.hazmat_helmet").withStyle(ChatFormatting.GOLD));
        }
        if (getType().getSlot() == EquipmentSlot.FEET) {
            tooltip.accept(getTextComponent("info.thermal.hazmat_boots").withStyle(ChatFormatting.GOLD));
        }
    }

    // Also ticks in the main inventory, so only act while worn.
    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {

        if (getType().getSlot() == EquipmentSlot.HEAD && entity instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
            if (player.getAirSupply() < player.getMaxAirSupply() && world.getRandom().nextInt(3) > 0) {
                player.setAirSupply(player.getAirSupply() + 1);
            }
            // TODO: Revisit
            //            if (!player.areEyesInFluid(FluidTags.WATER)) {
            //                Utils.addPotionEffectNoEvent(player, new EffectInstance(Effects.WATER_BREATHING, AIR_DURATION, 0, false, false, true));
            //            }
        }
    }

}
