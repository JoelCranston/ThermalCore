package cofh.thermal.lib.common.block.entity;

import net.minecraft.core.HolderLookup;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.common.block.entity.BlockEntityCoFH;
import cofh.core.util.control.IRedstoneControllableTile;
import cofh.core.util.control.ISecurableTile;
import cofh.core.util.control.RedstoneControlModule;
import cofh.core.util.control.SecurityControlModule;
import cofh.thermal.core.common.config.ThermalCoreConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;

public class LogisticsBlockEntity extends BlockEntityCoFH implements ISecurableTile, IRedstoneControllableTile {

    protected SecurityControlModule securityControl = new SecurityControlModule(this);
    protected RedstoneControlModule redstoneControl = new RedstoneControlModule(this);

    public LogisticsBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        CompoundTag nbt = ItemHelper.getBlockEntityData(stack);
        if (hasSecurity()) {
            securityControl().write(nbt);
        }
        if (ThermalCoreConfig.keepRSControl.get()) {
            redstoneControl().writeSettings(nbt);
        }
        if (!nbt.isEmpty()) {
            ItemHelper.setBlockEntityData(stack, nbt);
        }
        return super.createItemStackTag(stack);
    }

    // region NBT
    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {

        super.loadAdditional(nbt, registries);

        securityControl.read(nbt);
        redstoneControl.read(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {

        super.saveAdditional(nbt, registries);

        securityControl.write(nbt);
        redstoneControl.write(nbt);
    }
    // endregion

    // region NETWORK

    // CONTROL
    @Override
    public FriendlyByteBuf getControlPacket(FriendlyByteBuf buffer) {

        super.getControlPacket(buffer);

        securityControl.writeToBuffer(buffer);
        redstoneControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        securityControl.readFromBuffer(buffer);
        redstoneControl.readFromBuffer(buffer);
    }
    // endregion

    // region MODULES
    @Override
    public SecurityControlModule securityControl() {

        return securityControl;
    }

    @Override
    public RedstoneControlModule redstoneControl() {

        return redstoneControl;
    }
    // endregion

    // region IConveyableData
    @Override
    public void readConveyableData(Player player, CompoundTag tag) {

        redstoneControl.readSettings(tag);

        onControlUpdate();
    }

    @Override
    public void writeConveyableData(Player player, CompoundTag tag) {

        redstoneControl.writeSettings(tag);
    }
    // endregion
}
