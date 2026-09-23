package cofh.thermal.core.init.registries;

import cofh.core.common.block.EntityBlockActive;
import cofh.core.common.block.EntityBlockActive4Way;
import cofh.core.common.block.EntityBlockCoFH;
import cofh.core.common.item.BlockItemCoFH;
import cofh.lib.common.block.GunpowderBlock;
import cofh.lib.common.block.RubberBlock;
import cofh.thermal.core.common.block.*;
import cofh.thermal.core.common.block.device.EntityBlockComposter;
import cofh.thermal.core.common.block.entity.ChargeBenchBlockEntity;
import cofh.thermal.core.common.block.entity.TinkerBenchBlockEntity;
import cofh.thermal.core.common.block.entity.device.*;
import cofh.thermal.core.common.block.entity.storage.EnergyCellBlockEntity;
import cofh.thermal.core.common.block.entity.storage.FluidCellBlockEntity;
import cofh.thermal.core.common.config.ThermalCoreConfig;
import cofh.thermal.core.common.item.EnergyCellBlockItem;
import cofh.thermal.core.common.item.FluidCellBlockItem;
import cofh.thermal.lib.common.block.StorageCellBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.IntSupplier;

import static cofh.lib.util.FlagManager.getFlag;
import static cofh.lib.util.constants.BlockStatePropertiesCoFH.ACTIVE;
import static cofh.lib.util.helpers.BlockHelper.lightValue;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.registries.TCoreBlockEntities.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.*;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.util.ThermalAugmentRules.ENERGY_STORAGE_VALIDATOR;
import static cofh.thermal.lib.util.ThermalIDs.*;
import static net.minecraft.world.level.material.MapColor.*;

public class TCoreBlocks {

    private TCoreBlocks() {

    }

    public static void register() {

        registerVanilla();
        registerResources();
        registerBuildingBlocks();
        registerTileBlocks();
    }

    public static void setup() {

        setupVanilla();
        setupResources();
    }

    // region HELPERS
    private static void registerVanilla() {

        blocksTab(registerBlock(ID_CHARCOAL_BLOCK, id -> new Block(blockProperties(id).mapColor(COLOR_BLACK).strength(5.0F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
                id -> new BlockItemCoFH(BLOCKS.get(ID_CHARCOAL_BLOCK), blockItemProperties(id)).setBurnTime(16000)));
        blocksTab(registerBlock(ID_GUNPOWDER_BLOCK, id -> new GunpowderBlock(blockProperties(id).mapColor(COLOR_GRAY).strength(0.5F).sound(SoundType.SAND))));
        foodsTab(1000, registerBlock(ID_SUGAR_CANE_BLOCK, id -> new RotatedPillarBlock(blockProperties(id).mapColor(PLANT).strength(1.0F).sound(SoundType.CROP)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, double fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.6F, entityIn.damageSources().fall());
            }
        }));
        //        foodsTab(1000, registerBlock(ID_BAMBOO_BLOCK, id -> new RotatedPillarBlock(blockProperties(id).mapColor(PLANT).strength(1.0F).sound(SoundType.BAMBOO)) {
        //
        //            @Override
        //            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        //
        //                entityIn.causeFallDamage(fallDistance, 0.8F, entityIn.damageSources().fall());
        //            }
        //        }));

        foodsTab(1000, registerBlock(ID_APPLE_BLOCK, id -> new Block(blockProperties(id).mapColor(COLOR_RED).strength(1.5F).sound(SoundType.SCAFFOLDING))));
        foodsTab(1000, registerBlock(ID_CARROT_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_ORANGE).strength(1.5F).sound(SoundType.SCAFFOLDING))));
        foodsTab(1000, registerBlock(ID_POTATO_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BROWN).strength(1.5F).sound(SoundType.SCAFFOLDING))));
        foodsTab(1000, registerBlock(ID_BEETROOT_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_RED).strength(1.5F).sound(SoundType.SCAFFOLDING))));

        //        registerBlockOnly("quicksand", () -> new QuicksandBlock(of(Material.SAND, MaterialColor.SAND).strength(100.0F).noLootTable().sound(SoundType.SAND).dynamicShape()).bucket(() -> ITEMS.get("quicksand_bucket")));
        //        registerItem("quicksand_bucket", () -> new SolidBucketItemCoFH(BLOCKS.get("quicksand"), SoundEvents.SAND_HIT, itemProperties().stacksTo(1)));
    }

    private static void registerResources() {

        blocksTab(registerBlock(ID_APATITE_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_LIGHT_BLUE).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_CINNABAR_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_RED).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_NITER_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_WHITE).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_SULFUR_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_YELLOW).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()) {

            @Override
            public boolean isFireSource(BlockState state, LevelReader world, BlockPos pos, Direction side) {

                return side == Direction.UP;
            }
        }, id -> new BlockItemCoFH(BLOCKS.get(ID_SULFUR_BLOCK), blockItemProperties(id)).setBurnTime(12000)));

        blocksTab(registerBlock(ID_SAWDUST_BLOCK, id -> new ColoredFallingBlock(new ColorRGBA(11507581), blockProperties(id).strength(1.0F, 1.0F).sound(SoundType.SAND)),
                id -> new BlockItemCoFH(BLOCKS.get(ID_SAWDUST_BLOCK), blockItemProperties(id)).setBurnTime(2400)));

        blocksTab(registerBlock(ID_COAL_COKE_BLOCK, id -> new Block(blockProperties(id).mapColor(COLOR_BLACK).strength(5.0F, 6.0F).requiresCorrectToolForDrops()),
                id -> new BlockItemCoFH(BLOCKS.get(ID_COAL_COKE_BLOCK), blockItemProperties(id)).setBurnTime(32000)));

        blocksTab(registerBlock(ID_BITUMEN_BLOCK, id -> new Block(blockProperties(id).mapColor(COLOR_BLACK).strength(5.0F, 10.0F).sound(SoundType.NETHERRACK).requiresCorrectToolForDrops()),
                id -> new BlockItemCoFH(BLOCKS.get(ID_BITUMEN_BLOCK), blockItemProperties(id)).setBurnTime(16000)));

        blocksTab(registerBlock(ID_TAR_BLOCK, id -> new Block(blockProperties(id).mapColor(COLOR_BLACK).strength(2.0F, 4.0F).speedFactor(0.8F).jumpFactor(0.8F).sound(SoundType.NETHERRACK)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, double fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.8F, entityIn.damageSources().fall());
            }
        }, id -> new BlockItemCoFH(BLOCKS.get(ID_TAR_BLOCK), blockItemProperties(id)).setBurnTime(8000)));

        blocksTab(registerBlock(ID_ROSIN_BLOCK, id -> new Block(blockProperties(id).mapColor(COLOR_ORANGE).strength(2.0F, 4.0F).speedFactor(0.8F).jumpFactor(0.8F).sound(SoundType.HONEY_BLOCK)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, double fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.8F, entityIn.damageSources().fall());
            }
        }, id -> new BlockItemCoFH(BLOCKS.get(ID_ROSIN_BLOCK), blockItemProperties(id)).setBurnTime(8000)));

        blocksTab(registerBlock(ID_RUBBER_BLOCK, id -> new RubberBlock(blockProperties(id).mapColor(TERRACOTTA_WHITE).strength(3.0F, 3.0F).jumpFactor(1.25F).sound(SoundType.FUNGUS))));
        blocksTab(registerBlock(ID_CURED_RUBBER_BLOCK, id -> new RubberBlock(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(3.0F, 3.0F).jumpFactor(1.25F).sound(SoundType.FUNGUS))));
        blocksTab(registerBlock(ID_SLAG_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_RICH_SLAG_BLOCK, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));

        blocksTab(30, registerBlock(ID_SIGNALUM_BLOCK, id -> new SignalumBlock(blockProperties(id).mapColor(COLOR_RED).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(lightValue(7)).noOcclusion()), Rarity.UNCOMMON));
        blocksTab(30, registerBlock(ID_LUMIUM_BLOCK, id -> new LumiumBlock(blockProperties(id).mapColor(COLOR_YELLOW).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(lightValue(15)).noOcclusion()), Rarity.UNCOMMON));
        blocksTab(30, registerBlock(ID_ENDERIUM_BLOCK, id -> new EnderiumBlock(blockProperties(id).mapColor(COLOR_CYAN).strength(25.0F, 30.0F).sound(SoundType.LODESTONE).requiresCorrectToolForDrops().lightLevel(lightValue(3)).noOcclusion()), Rarity.UNCOMMON));
    }

    private static void registerBuildingBlocks() {

        devicesTab(200, registerBlock(ID_MACHINE_FRAME, id -> new Block(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).noOcclusion())));
        devicesTab(200, registerBlock(ID_ENERGY_CELL_FRAME, id -> new Block(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).noOcclusion())));
        devicesTab(200, registerBlock(ID_FLUID_CELL_FRAME, id -> new Block(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).noOcclusion())));
        // registerBlock(ID_ITEM_CELL_FRAME, id -> new Block(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).harvestTool(ToolType.PICKAXE).noOcclusion()));

        blocksTab(registerBlock(ID_OBSIDIAN_GLASS, id -> new HardenedGlassBlock(blockProperties(id).mapColor(PODZOL).strength(5.0F, 1000.0F).sound(SoundType.GLASS).noOcclusion())));
        blocksTab(registerBlock(ID_SIGNALUM_GLASS, id -> new SignalumGlassBlock(blockProperties(id).mapColor(COLOR_RED).strength(5.0F, 1000.0F).sound(SoundType.GLASS).lightLevel(lightValue(7)).noOcclusion()), Rarity.UNCOMMON));
        blocksTab(registerBlock(ID_LUMIUM_GLASS, id -> new LumiumGlassBlock(blockProperties(id).mapColor(COLOR_YELLOW).strength(5.0F, 1000.0F).sound(SoundType.GLASS).lightLevel(lightValue(15)).noOcclusion()), Rarity.UNCOMMON));
        blocksTab(registerBlock(ID_ENDERIUM_GLASS, id -> new EnderiumGlassBlock(blockProperties(id).mapColor(COLOR_CYAN).strength(5.0F, 1000.0F).sound(SoundType.GLASS).lightLevel(lightValue(3)).noOcclusion()), Rarity.UNCOMMON));

        blocksTab(registerBlock(ID_WHITE_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(SNOW).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_ORANGE_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_ORANGE).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_MAGENTA_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_MAGENTA).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_LIGHT_BLUE_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_LIGHT_BLUE).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_YELLOW_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_YELLOW).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_LIME_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_LIGHT_GREEN).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_PINK_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_PINK).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_GRAY_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_GRAY).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_LIGHT_GRAY_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_LIGHT_GRAY).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_CYAN_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_CYAN).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_PURPLE_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_PURPLE).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_BLUE_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_BLUE).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_BROWN_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_BROWN).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_GREEN_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_GREEN).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_RED_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_RED).strength(2.0F, 6.0F).sound(SoundType.WOOL))));
        blocksTab(registerBlock(ID_BLACK_ROCKWOOL, id -> new Block(blockProperties(id).mapColor(COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOL))));

        blocksTab(registerBlock(ID_POLISHED_SLAG, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_CHISELED_SLAG, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_SLAG_BRICKS, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_CRACKED_SLAG_BRICKS, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_POLISHED_RICH_SLAG, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BROWN).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_CHISELED_RICH_SLAG, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BROWN).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_RICH_SLAG_BRICKS, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BROWN).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
        blocksTab(registerBlock(ID_CRACKED_RICH_SLAG_BRICKS, id -> new Block(blockProperties(id).mapColor(TERRACOTTA_BROWN).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops())));
    }

    private static void registerTileBlocks() {

        IntSupplier deviceAugs = () -> ThermalCoreConfig.deviceAugments;

        devicesTab(100, registerAugmentableBlock(ID_DEVICE_HIVE_EXTRACTOR, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceHiveExtractorBlockEntity.class, DEVICE_HIVE_EXTRACTOR_TILE), deviceAugs, DeviceHiveExtractorBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_HIVE_EXTRACTOR));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_TREE_EXTRACTOR, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceTreeExtractorBlockEntity.class, DEVICE_TREE_EXTRACTOR_TILE), deviceAugs, DeviceTreeExtractorBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_TREE_EXTRACTOR));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_FISHER, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceFisherBlockEntity.class, DEVICE_FISHER_TILE), deviceAugs, DeviceFisherBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_FISHER));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_COMPOSTER, id -> new EntityBlockComposter(blockProperties(id).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceComposterBlockEntity.class, DEVICE_COMPOSTER_TILE), deviceAugs, DeviceComposterBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_COMPOSTER));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_SOIL_INFUSER, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.SCAFFOLDING).strength(2.5F).lightLevel(lightValue(ACTIVE, 10)), DeviceSoilInfuserBlockEntity.class, DEVICE_SOIL_INFUSER_TILE), deviceAugs, DeviceSoilInfuserBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_SOIL_INFUSER));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_WATER_GEN, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F), DeviceWaterGenBlockEntity.class, DEVICE_WATER_GEN_TILE), deviceAugs, DeviceWaterGenBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_WATER_GEN));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_ROCK_GEN, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 14)), DeviceRockGenBlockEntity.class, DEVICE_ROCK_GEN_TILE), deviceAugs, DeviceRockGenBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_ROCK_GEN));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_COLLECTOR, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F), DeviceCollectorBlockEntity.class, DEVICE_COLLECTOR_TILE), deviceAugs, DeviceCollectorBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_COLLECTOR));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_XP_CONDENSER, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 12)), DeviceXpCondenserBlockEntity.class, DEVICE_XP_CONDENSER_TILE), deviceAugs, DeviceXpCondenserBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_XP_CONDENSER));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_NULLIFIER, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 7)), DeviceNullifierBlockEntity.class, DEVICE_NULLIFIER_TILE), deviceAugs, DeviceNullifierBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_NULLIFIER));
        devicesTab(100, registerAugmentableBlock(ID_DEVICE_POTION_DIFFUSER, id -> new EntityBlockActive4Way(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F), DevicePotionDiffuserBlockEntity.class, DEVICE_POTION_DIFFUSER_TILE), deviceAugs, DevicePotionDiffuserBlockEntity.AUG_VALIDATOR), getFlag(ID_DEVICE_POTION_DIFFUSER));

        // registerBlock(ID_CHUNK_LOADER, id -> new TileBlockActive(blockProperties(id).sound(SoundType.NETHERITE_BLOCK).strength(10.0F).harvestTool(ToolType.PICKAXE), DeviceChunkLoaderTile::new), getFlag(ID_CHUNK_LOADER));

        IntSupplier storageAugs = () -> ThermalCoreConfig.storageAugments;

        devicesTab(registerAugmentableBlock(ID_TINKER_BENCH, id -> new EntityBlockCoFH(blockProperties(id).sound(SoundType.SCAFFOLDING).strength(2.5F), TinkerBenchBlockEntity.class, TINKER_BENCH_TILE), storageAugs, TinkerBenchBlockEntity.AUG_VALIDATOR));
        devicesTab(registerAugmentableBlock(ID_CHARGE_BENCH, id -> new EntityBlockActive(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 7)), ChargeBenchBlockEntity.class, CHARGE_BENCH_TILE), storageAugs, ChargeBenchBlockEntity.AUG_VALIDATOR));

        devicesTab(40, registerBlock(ID_ENERGY_CELL, id -> new StorageCellBlock(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).noOcclusion(), EnergyCellBlockEntity.class, ENERGY_CELL_TILE), id -> new EnergyCellBlockItem(BLOCKS.get(ID_ENERGY_CELL), blockItemProperties(id)).setNumSlots(storageAugs).setAugValidator(ENERGY_STORAGE_VALIDATOR)));
        devicesTab(40, registerBlock(ID_FLUID_CELL, id -> new StorageCellBlock(blockProperties(id).sound(SoundType.LANTERN).strength(2.0F).noOcclusion(), FluidCellBlockEntity.class, FLUID_CELL_TILE), id -> new FluidCellBlockItem(BLOCKS.get(ID_FLUID_CELL), blockItemProperties(id)).setNumSlots(storageAugs).setAugValidator(FluidCellBlockEntity.AUG_VALIDATOR)));
    }

    private static void setupVanilla() {

        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFlammable(BLOCKS.get(ID_CHARCOAL_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_GUNPOWDER_BLOCK), 15, 100);
        fire.setFlammable(BLOCKS.get(ID_SUGAR_CANE_BLOCK), 60, 20);
        // fire.setFlammable(BLOCKS.get(ID_BAMBOO_BLOCK), 60, 20);
    }

    private static void setupResources() {

        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFlammable(BLOCKS.get(ID_SAWDUST_BLOCK), 10, 10);
        fire.setFlammable(BLOCKS.get(ID_COAL_COKE_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_BITUMEN_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_TAR_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_ROSIN_BLOCK), 5, 5);
    }
    // endregion
}
