package cofh.thermal.core.common.item;

import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.core.Holder;
import cofh.core.client.renderer.entity.model.ArmorFullSuitModel;
import cofh.core.common.event.ArmorEvents;
import cofh.core.common.item.ArmorItemCoFH;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public class HazmatArmorItem extends ArmorItemCoFH {

    public HazmatArmorItem(Holder<ArmorMaterial> pMaterial, ArmorItem.Type pType, Item.Properties pProperties) {

        super(pMaterial, pType, pProperties);

        ArmorEvents.registerHazardResistArmor(this, RESISTANCE_RATIO[getType().getSlot().getIndex()]);
        if (getType().getSlot() == EquipmentSlot.FEET) {
            ArmorEvents.registerFallResistArmor(this, 6.0D);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.hazmat_armor").withStyle(ChatFormatting.GOLD));

        if (getType().getSlot() == EquipmentSlot.HEAD) {
            tooltip.add(getTextComponent("info.thermal.hazmat_helmet").withStyle(ChatFormatting.GOLD));
        }
        if (getType().getSlot() == EquipmentSlot.FEET) {
            tooltip.add(getTextComponent("info.thermal.hazmat_boots").withStyle(ChatFormatting.GOLD));
        }
    }

    // NeoForge's onArmorTick is gone; Inventory#tick reaches the armour compartment through
    // inventoryTick, so the piece has to check it is actually being worn.
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {

        if (getType().getSlot() != EquipmentSlot.HEAD || !(entity instanceof Player player) || player.getItemBySlot(EquipmentSlot.HEAD) != stack) {
            return;
        }
        if (player.getAirSupply() < player.getMaxAirSupply() && world.random.nextInt(3) > 0) {
            player.setAirSupply(player.getAirSupply() + 1);
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

        consumer.accept(new IClientItemExtensions() {

            @Override
            @Nonnull
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {

                return armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.FEET ? _default : ArmorFullSuitModel.INSTANCE.get();
            }
        });
    }

}
