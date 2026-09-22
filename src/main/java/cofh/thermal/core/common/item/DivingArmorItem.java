package cofh.thermal.core.common.item;

import cofh.core.client.renderer.entity.model.ArmorFullSuitModel;
import cofh.core.common.item.ArmorItemCoFH;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED;

public class DivingArmorItem extends ArmorItemCoFH {

    protected static final double[] SWIM_SPEED_BONUS = new double[]{0.60D, 0.30D, 0.10D, 0.0D};
    protected static final int AIR_DURATION = 1800;

    private static final ResourceLocation SWIM_SPEED_MODIFIER = ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "diving_swim_speed");

    public DivingArmorItem(Holder<ArmorMaterial> pMaterial, ArmorItem.Type pType, Item.Properties pProperties) {

        super(pMaterial, pType, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {

        if (getType().getSlot() == EquipmentSlot.HEAD) {
            tooltip.add(getTextComponent("info.thermal.diving_helmet").withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {

        double bonus = SWIM_SPEED_BONUS[getType().getSlot().getIndex()];
        if (bonus <= 0.0D) {
            return super.getDefaultAttributeModifiers(stack);
        }
        return super.getDefaultAttributeModifiers(stack).withModifierAdded(SWIM_SPEED,
                new AttributeModifier(SWIM_SPEED_MODIFIER, bonus, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.bySlot(getType().getSlot()));
    }

    // Also ticks in the main inventory, so only act while worn.
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {

        if (getType().getSlot() == EquipmentSlot.HEAD && entity instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
            if (player.getAirSupply() < player.getMaxAirSupply() && world.random.nextInt(5) > 0) {
                player.setAirSupply(player.getAirSupply() + 1);
            }
            // TODO: Revisit
            //            if (!player.areEyesInFluid(FluidTags.WATER)) {
            //                Utils.addPotionEffectNoEvent(player, new EffectInstance(Effects.WATER_BREATHING, AIR_DURATION, 0, false, false, true));
            //            }
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
