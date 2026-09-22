package cofh.thermal.core.common.item;

import net.minecraft.core.component.DataComponents;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.common.item.ItemCoFH;
import cofh.core.util.ProxyUtils;
import cofh.lib.api.IConveyableData;
import cofh.lib.api.control.ISecurable;
import cofh.lib.api.item.IPlacementItem;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.helpers.StringHelper.canLocalize;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static net.minecraft.ChatFormatting.DARK_GRAY;
import static net.minecraft.ChatFormatting.GRAY;

public class RedprintItem extends ItemCoFH implements IPlacementItem {

    public RedprintItem(Properties builder) {

        super(builder);

        ProxyUtils.registerItemModelProperty(this, ResourceLocation.parse("has_data"), ((stack, world, entity, seed) -> ItemHelper.hasCustomData(stack) ? 1F : 0F));
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        CompoundTag conveyableData = ItemHelper.hasCustomData(stack) ? ItemHelper.getCustomData(stack) : null;

        if (conveyableData == null) {
            tooltip.add(getTextComponent("info.thermal.redprint.use").withStyle(GRAY));
        } else {
            tooltip.add(getTextComponent("info.thermal.redprint.use.contents").withStyle(GRAY));
            tooltip.add(getTextComponent("info.thermal.redprint.use.sneak").withStyle(DARK_GRAY));

            tooltip.add(getTextComponent("info.thermal.redprint.contents"));
            for (String type : conveyableData.getAllKeys()) {
                if (!canLocalize("info.thermal.redprint.data." + type)) {
                    tooltip.add(getTextComponent("info.thermal.redprint.unknown")
                            .withStyle(DARK_GRAY));
                }
                tooltip.add(Component.literal(" - ")
                        .append(getTextComponent("info.thermal.redprint.data." + type)
                                .withStyle(GRAY))
                );
            }
        }
    }

    protected boolean useDelegate(ItemStack stack, UseOnContext context) {

        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        if (player.isSecondaryUseActive() && context.getHand() == InteractionHand.MAIN_HAND) {
            if (ItemHelper.hasCustomData(stack)) {
                player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.3F);
                ItemHelper.setCustomData(stack, null);
            stack.remove(DataComponents.RARITY);
                stack.remove(DataComponents.RARITY);
            }
            return true;
        }
        BlockPos pos = context.getClickedPos();
        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof ISecurable && !((ISecurable) tile).canAccess(player)) {
            return false;
        }
        if (tile instanceof IConveyableData conveyableTile) {
            if (!ItemHelper.hasCustomData(stack) && context.getHand() == InteractionHand.MAIN_HAND) {
                // The blob is a component copy now, so it is built up and then stored back.
                CompoundTag conveyableData = new CompoundTag();
                conveyableTile.writeConveyableData(player, conveyableData);
                tile.setChanged();
                if (conveyableData.isEmpty()) {
                    return false;
                } else {
                    ItemHelper.setCustomData(stack, conveyableData);
                    stack.set(DataComponents.RARITY, Rarity.UNCOMMON);
                    player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.7F);
                }
            } else if (ItemHelper.hasCustomData(stack)) {
                conveyableTile.readConveyableData(player, ItemHelper.getCustomData(stack));
                player.level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.5F, 0.8F);
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), stack) && useDelegate(stack, context) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (player.isSecondaryUseActive()) {
            if (ItemHelper.hasCustomData(stack)) {
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
            }
            ItemHelper.setCustomData(stack, null);
            stack.remove(DataComponents.RARITY);
                stack.remove(DataComponents.RARITY);
        }
        player.swing(hand);
        return InteractionResultHolder.success(stack);
    }

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, UseOnContext context) {

        return useDelegate(stack, context);
    }
    // endregion
}
