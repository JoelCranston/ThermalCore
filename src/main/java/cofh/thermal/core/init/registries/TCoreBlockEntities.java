package cofh.thermal.core.init.registries;

import cofh.thermal.core.common.block.entity.ChargeBenchBlockEntity;
import cofh.thermal.core.common.block.entity.TinkerBenchBlockEntity;
import cofh.thermal.core.common.block.entity.device.*;
import cofh.thermal.core.common.block.entity.storage.EnergyCellBlockEntity;
import cofh.thermal.core.common.block.entity.storage.FluidCellBlockEntity;
import cofh.thermal.lib.common.block.entity.AugmentableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;
import java.util.function.Supplier;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.BLOCK_ENTITIES;
import static cofh.thermal.lib.util.ThermalIDs.*;

public class TCoreBlockEntities {

    private TCoreBlockEntities() {

    }

    public static void register() {

        // TILE_ENTITIES.register(ID_CHUNK_LOADER, () -> TileEntityType.Builder.of(DeviceChunkLoaderTile::new, CHUNK_LOADER_BLOCK).build(null));

        // TILE_ENTITIES.register(ID_ITEM_CELL, () -> TileEntityType.Builder.of(ItemCellTile::new, ITEM_CELL_BLOCK).build(null));
    }

    public static void capabilitySetup(RegisterCapabilitiesEvent event) {

        var entities = List.of(
                DEVICE_HIVE_EXTRACTOR_TILE.get(),
                DEVICE_TREE_EXTRACTOR_TILE.get(),
                DEVICE_FISHER_TILE.get(),
                DEVICE_COMPOSTER_TILE.get(),
                DEVICE_SOIL_INFUSER_TILE.get(),
                DEVICE_WATER_GEN_TILE.get(),
                DEVICE_ROCK_GEN_TILE.get(),
                DEVICE_COLLECTOR_TILE.get(),
                DEVICE_XP_CONDENSER_TILE.get(),
                DEVICE_NULLIFIER_TILE.get(),
                DEVICE_POTION_DIFFUSER_TILE.get(),

                TINKER_BENCH_TILE.get(),
                CHARGE_BENCH_TILE.get(),

                ENERGY_CELL_TILE.get(),
                FLUID_CELL_TILE.get()
        );

        for (var type : entities) {
            event.registerBlockEntity(Capabilities.Item.BLOCK, type, (blockEntity, side) -> ((AugmentableBlockEntity) blockEntity).getItemHandlerCapability(side));
            event.registerBlockEntity(Capabilities.Fluid.BLOCK, type, (blockEntity, side) -> ((AugmentableBlockEntity) blockEntity).getFluidHandlerCapability(side));
            event.registerBlockEntity(Capabilities.Energy.BLOCK, type, (blockEntity, side) -> ((AugmentableBlockEntity) blockEntity).getEnergyCapability(side));
        }
    }

    public static final Supplier<BlockEntityType<?>> DEVICE_HIVE_EXTRACTOR_TILE = BLOCK_ENTITIES.register(ID_DEVICE_HIVE_EXTRACTOR, () -> new BlockEntityType<>(DeviceHiveExtractorBlockEntity::new, BLOCKS.get(ID_DEVICE_HIVE_EXTRACTOR)));
    public static final Supplier<BlockEntityType<?>> DEVICE_TREE_EXTRACTOR_TILE = BLOCK_ENTITIES.register(ID_DEVICE_TREE_EXTRACTOR, () -> new BlockEntityType<>(DeviceTreeExtractorBlockEntity::new, BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR)));
    public static final Supplier<BlockEntityType<?>> DEVICE_FISHER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_FISHER, () -> new BlockEntityType<>(DeviceFisherBlockEntity::new, BLOCKS.get(ID_DEVICE_FISHER)));
    public static final Supplier<BlockEntityType<?>> DEVICE_COMPOSTER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_COMPOSTER, () -> new BlockEntityType<>(DeviceComposterBlockEntity::new, BLOCKS.get(ID_DEVICE_COMPOSTER)));
    public static final Supplier<BlockEntityType<?>> DEVICE_SOIL_INFUSER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_SOIL_INFUSER, () -> new BlockEntityType<>(DeviceSoilInfuserBlockEntity::new, BLOCKS.get(ID_DEVICE_SOIL_INFUSER)));
    public static final Supplier<BlockEntityType<?>> DEVICE_WATER_GEN_TILE = BLOCK_ENTITIES.register(ID_DEVICE_WATER_GEN, () -> new BlockEntityType<>(DeviceWaterGenBlockEntity::new, BLOCKS.get(ID_DEVICE_WATER_GEN)));
    public static final Supplier<BlockEntityType<?>> DEVICE_ROCK_GEN_TILE = BLOCK_ENTITIES.register(ID_DEVICE_ROCK_GEN, () -> new BlockEntityType<>(DeviceRockGenBlockEntity::new, BLOCKS.get(ID_DEVICE_ROCK_GEN)));
    public static final Supplier<BlockEntityType<?>> DEVICE_COLLECTOR_TILE = BLOCK_ENTITIES.register(ID_DEVICE_COLLECTOR, () -> new BlockEntityType<>(DeviceCollectorBlockEntity::new, BLOCKS.get(ID_DEVICE_COLLECTOR)));
    public static final Supplier<BlockEntityType<?>> DEVICE_XP_CONDENSER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_XP_CONDENSER, () -> new BlockEntityType<>(DeviceXpCondenserBlockEntity::new, BLOCKS.get(ID_DEVICE_XP_CONDENSER)));
    public static final Supplier<BlockEntityType<?>> DEVICE_NULLIFIER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_NULLIFIER, () -> new BlockEntityType<>(DeviceNullifierBlockEntity::new, BLOCKS.get(ID_DEVICE_NULLIFIER)));
    public static final Supplier<BlockEntityType<?>> DEVICE_POTION_DIFFUSER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_POTION_DIFFUSER, () -> new BlockEntityType<>(DevicePotionDiffuserBlockEntity::new, BLOCKS.get(ID_DEVICE_POTION_DIFFUSER)));

    public static final Supplier<BlockEntityType<?>> TINKER_BENCH_TILE = BLOCK_ENTITIES.register(ID_TINKER_BENCH, () -> new BlockEntityType<>(TinkerBenchBlockEntity::new, BLOCKS.get(ID_TINKER_BENCH)));
    public static final Supplier<BlockEntityType<?>> CHARGE_BENCH_TILE = BLOCK_ENTITIES.register(ID_CHARGE_BENCH, () -> new BlockEntityType<>(ChargeBenchBlockEntity::new, BLOCKS.get(ID_CHARGE_BENCH)));

    public static final Supplier<BlockEntityType<?>> ENERGY_CELL_TILE = BLOCK_ENTITIES.register(ID_ENERGY_CELL, () -> new BlockEntityType<>(EnergyCellBlockEntity::new, BLOCKS.get(ID_ENERGY_CELL)));
    public static final Supplier<BlockEntityType<?>> FLUID_CELL_TILE = BLOCK_ENTITIES.register(ID_FLUID_CELL, () -> new BlockEntityType<>(FluidCellBlockEntity::new, BLOCKS.get(ID_FLUID_CELL)));

}
