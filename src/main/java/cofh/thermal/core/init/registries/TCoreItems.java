package cofh.thermal.core.init.registries;

import cofh.core.common.item.EnergyContainerItem;
import cofh.core.common.item.ItemCoFH;
import cofh.core.common.item.SpawnEggItemCoFH;
import cofh.core.util.filter.FilterRegistry;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.common.block.TntBlockCoFH;
import cofh.lib.common.item.ArmorMaterialCoFH;
import cofh.thermal.core.common.item.*;
import cofh.thermal.lib.common.item.AugmentItem;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Blocks;

import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.FlagManager.getFlag;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.registries.TCoreEntities.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.*;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.util.ThermalAugmentRules.flagUniqueAugment;
import static cofh.thermal.lib.util.ThermalFlags.*;
import static cofh.thermal.lib.util.ThermalIDs.*;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class TCoreItems {

    private TCoreItems() {

    }

    public static void register() {

        registerResources();
        registerMaterials();
        registerParts();
        registerAugments();
        registerTools();
        registerArmor();

        registerSpawnEggs();
    }

    public static void setup() {

        DetonatorItem.registerTNT(Blocks.TNT, PrimedTnt::new);

        DetonatorItem.registerTNT(BLOCKS.get(ID_SLIME_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_SLIME_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_REDSTONE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_REDSTONE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_GLOWSTONE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_GLOWSTONE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_ENDER_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_ENDER_TNT))).getFactory());

        DetonatorItem.registerTNT(BLOCKS.get(ID_FIRE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_FIRE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_EARTH_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_EARTH_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_ICE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_ICE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_LIGHTNING_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_LIGHTNING_TNT))).getFactory());

        DetonatorItem.registerTNT(BLOCKS.get(ID_PHYTO_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_PHYTO_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_NUKE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_NUKE_TNT))).getFactory());

        flagUniqueAugment(ITEMS.get("rs_control_augment"));
        flagUniqueAugment(ITEMS.get("side_config_augment"));
        flagUniqueAugment(ITEMS.get("xp_storage_augment"));

        flagUniqueAugment(ITEMS.get("upgrade_augment_1"));
        flagUniqueAugment(ITEMS.get("upgrade_augment_2"));
        flagUniqueAugment(ITEMS.get("upgrade_augment_3"));

        flagUniqueAugment(ITEMS.get("rf_coil_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_storage_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_xfer_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_creative_augment"));

        flagUniqueAugment(ITEMS.get("fluid_tank_augment"));
        flagUniqueAugment(ITEMS.get("fluid_tank_creative_augment"));

        flagUniqueAugment(ITEMS.get("fluid_filter_augment"));
        flagUniqueAugment(ITEMS.get("item_filter_augment"));

        flagUniqueAugment(ITEMS.get("machine_efficiency_creative_augment"));
        flagUniqueAugment(ITEMS.get("machine_catalyst_creative_augment"));
        flagUniqueAugment(ITEMS.get("machine_cycle_augment"));
        flagUniqueAugment(ITEMS.get("machine_null_augment"));

        flagUniqueAugment(ITEMS.get("dynamo_throttle_augment"));
    }

    // region HELPERS
    private static void registerResources() {

        itemsTab(registerItem("apatite"));
        itemsTab(registerItem("apatite_dust"));
        itemsTab(registerItem("cinnabar"));
        itemsTab(registerItem("cinnabar_dust"));
        itemsTab(registerItem("niter"));
        itemsTab(registerItem("niter_dust"));
        itemsTab(registerItem("sulfur", id -> new ItemCoFH(itemProperties(id)).setBurnTime(1200)));
        itemsTab(registerItem("sulfur_dust", id -> new ItemCoFH(itemProperties(id)).setBurnTime(1200)));

        itemsTab(registerItem("sawdust"));
        itemsTab(registerItem("coal_coke", id -> new ItemCoFH(itemProperties(id)).setBurnTime(3200)));
        itemsTab(registerItem("bitumen", id -> new ItemCoFH(itemProperties(id)).setBurnTime(1600)));
        itemsTab(registerItem("tar", id -> new ItemCoFH(itemProperties(id)).setBurnTime(800)));
        itemsTab(registerItem("rosin", id -> new ItemCoFH(itemProperties(id)).setBurnTime(800)));
        itemsTab(registerItem("rubber"));
        itemsTab(registerItem("cured_rubber"));
        itemsTab(registerItem("slag"));
        itemsTab(registerItem("rich_slag"));

        foodsTab(registerItem("syrup_bottle", id -> new ItemCoFH(itemProperties(id).craftRemainder(GLASS_BOTTLE).food(Foods.HONEY_BOTTLE, Consumables.HONEY_BOTTLE).usingConvertsTo(GLASS_BOTTLE).stacksTo(16))));

        //        registerItem("biomass");
        //        registerItem("rich_biomass");

        itemsTab(registerItem("basalz_rod"));
        itemsTab(registerItem("basalz_powder"));
        itemsTab(registerItem("blitz_rod"));
        itemsTab(registerItem("blitz_powder"));
        itemsTab(registerItem("blizz_rod"));
        itemsTab(registerItem("blizz_powder"));

        itemsTab(registerItem("beekeeper_fabric"), getFlag(FLAG_BEEKEEPER_ARMOR));
        itemsTab(registerItem("diving_fabric"), getFlag(FLAG_DIVING_ARMOR));
        itemsTab(registerItem("hazmat_fabric"), getFlag(FLAG_HAZMAT_ARMOR));
    }

    private static void registerParts() {

        itemsTab(registerItem("redstone_servo"));
        itemsTab(registerItem("rf_coil"));

        itemsTab(registerItem("drill_head", id -> new ItemCoFH(itemProperties(id))), getFlag(FLAG_TOOL_COMPONENTS));
        itemsTab(registerItem("saw_blade", id -> new ItemCoFH(itemProperties(id))), getFlag(FLAG_TOOL_COMPONENTS));

        registerItem("laser_diode", id -> new ItemCoFH(itemProperties(id)));//.setShowInGroups(getFeature(FLAG_TOOL_COMPONENTS))); // TODO: Implement
    }

    private static void registerMaterials() {

        itemsTab(registerItem("ender_pearl_dust"));

        registerVanillaMetalSet("iron");
        registerVanillaMetalSet("gold");
        registerVanillaMetalSet("copper");
        registerVanillaMetalSet("netherite");

        registerVanillaGemSet("lapis");
        registerVanillaGemSet("diamond");
        registerVanillaGemSet("emerald");
        registerVanillaGemSet("quartz");

        Rarity rarity = Rarity.UNCOMMON;

        registerAlloySet("signalum", rarity);
        registerAlloySet("lumium", rarity);
        registerAlloySet("enderium", rarity);
    }

    private static void registerTools() {

        toolsTab(registerItem(ID_WRENCH, id -> new WrenchItem(itemProperties(id).stacksTo(1))));
        toolsTab(registerItem(ID_REDPRINT, id -> new RedprintItem(itemProperties(id).stacksTo(1))));
        toolsTab(registerItem(ID_RF_POTATO, id -> new EnergyContainerItem(itemProperties(id).stacksTo(1), 100000, 40) {
        }));
        toolsTab(registerItem(ID_XP_CRYSTAL, id -> new XpCrystalItem(itemProperties(id).stacksTo(1), 10000)));
        toolsTab(registerItem(ID_LOCK, id -> new LockItem(itemProperties(id))));
        toolsTab(registerItem(ID_SATCHEL, id -> new SatchelItem(itemProperties(id).stacksTo(1), 9)));
        toolsTab(registerItem(ID_DETONATOR, id -> new DetonatorItem(itemProperties(id).stacksTo(1))));

        toolsTab(60, registerItem(ID_FLORB, id -> new FlorbItem(itemProperties(id), BUCKET_VOLUME, (e) -> !e.getFluid().defaultFluidState().createLegacyBlock().isAir())));
        toolsTab(60, registerItem("earth_charge", id -> new EarthChargeItem(itemProperties(id))));
        toolsTab(60, registerItem("ice_charge", id -> new IceChargeItem(itemProperties(id))));
        toolsTab(60, registerItem("lightning_charge", id -> new LightningChargeItem(itemProperties(id))));

        toolsTab(80, registerItem("compost", id -> new FertilizerItem(itemProperties(id), 2)));
        toolsTab(80, registerItem("phytogro", id -> new FertilizerItem(itemProperties(id))));
        // toolsTab(registerItem("fluxed_phytogro", () -> new FertilizerItem(properties(), 5)));

        toolsTab(90, registerItem("junk_net"), getFlag(ID_DEVICE_FISHER));
        toolsTab(90, registerItem("aquachow"), getFlag(ID_DEVICE_FISHER));
        toolsTab(90, registerItem("deep_aquachow"), getFlag(ID_DEVICE_FISHER));
        //        registerItem("rich_aquachow");
        //        registerItem("fluxed_aquachow");
    }

    private static void registerArmor() {

        toolsTab(50, registerItem(ID_BEEKEEPER_HELMET, id -> new BeekeeperArmorItem(BEEKEEPER, ArmorType.HELMET, itemProperties(id))), getFlag(FLAG_BEEKEEPER_ARMOR));
        toolsTab(50, registerItem(ID_BEEKEEPER_CHESTPLATE, id -> new BeekeeperArmorItem(BEEKEEPER, ArmorType.CHESTPLATE, itemProperties(id))), getFlag(FLAG_BEEKEEPER_ARMOR));
        toolsTab(50, registerItem(ID_BEEKEEPER_LEGGINGS, id -> new BeekeeperArmorItem(BEEKEEPER, ArmorType.LEGGINGS, itemProperties(id))), getFlag(FLAG_BEEKEEPER_ARMOR));
        toolsTab(50, registerItem(ID_BEEKEEPER_BOOTS, id -> new BeekeeperArmorItem(BEEKEEPER, ArmorType.BOOTS, itemProperties(id))), getFlag(FLAG_BEEKEEPER_ARMOR));

        toolsTab(50, registerItem(ID_DIVING_HELMET, id -> new DivingArmorItem(DIVING, ArmorType.HELMET, itemProperties(id))), getFlag(FLAG_DIVING_ARMOR));
        toolsTab(50, registerItem(ID_DIVING_CHESTPLATE, id -> new DivingArmorItem(DIVING, ArmorType.CHESTPLATE, itemProperties(id))), getFlag(FLAG_DIVING_ARMOR));
        toolsTab(50, registerItem(ID_DIVING_LEGGINGS, id -> new DivingArmorItem(DIVING, ArmorType.LEGGINGS, itemProperties(id))), getFlag(FLAG_DIVING_ARMOR));
        toolsTab(50, registerItem(ID_DIVING_BOOTS, id -> new DivingArmorItem(DIVING, ArmorType.BOOTS, itemProperties(id))), getFlag(FLAG_DIVING_ARMOR));

        toolsTab(50, registerItem(ID_HAZMAT_HELMET, id -> new HazmatArmorItem(HAZMAT, ArmorType.HELMET, itemProperties(id))), getFlag(FLAG_HAZMAT_ARMOR));
        toolsTab(50, registerItem(ID_HAZMAT_CHESTPLATE, id -> new HazmatArmorItem(HAZMAT, ArmorType.CHESTPLATE, itemProperties(id))), getFlag(FLAG_HAZMAT_ARMOR));
        toolsTab(50, registerItem(ID_HAZMAT_LEGGINGS, id -> new HazmatArmorItem(HAZMAT, ArmorType.LEGGINGS, itemProperties(id))), getFlag(FLAG_HAZMAT_ARMOR));
        toolsTab(50, registerItem(ID_HAZMAT_BOOTS, id -> new HazmatArmorItem(HAZMAT, ArmorType.BOOTS, itemProperties(id))), getFlag(FLAG_HAZMAT_ARMOR));
    }

    // region AUGMENTS
    private static void registerAugments() {

        registerUpgradeAugments();
        registerFeatureAugments();
        registerStorageAugments();
        registerFilterAugments();
        registerMachineAugments();
        registerDynamoAugments();
        registerAreaAugments();
        registerPotionAugments();
    }

    private static void registerUpgradeAugments() {

        final float[] upgradeMods = new float[]{1.0F, 2.0F, 3.0F, 4.0F, 6.0F, 8.5F};
        // final float[] upgradeMods = new float[]{1.0F, 1.5F, 2.0F, 2.5F, 3.0F, 3.5F};

        for (int i = 1; i <= 3; ++i) {
            int tier = i;
            itemsTab(registerItem("upgrade_augment_" + i, id -> new AugmentItem(itemProperties(id),
                    AugmentDataHelper.builder()
                            .type(TAG_AUGMENT_TYPE_UPGRADE)
                            .mod(TAG_AUGMENT_BASE_MOD, upgradeMods[tier])
                            .build())));
        }
    }

    private static void registerFeatureAugments() {

        itemsTab(registerItem("rs_control_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_RS_CONTROL, 1.0F)
                        .build())), getFlag(FLAG_RS_CONTROL_AUGMENT));

        itemsTab(registerItem("side_config_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_SIDE_CONFIG, 1.0F)
                        .build())), getFlag(FLAG_SIDE_CONFIG_AUGMENT));

        itemsTab(registerItem("xp_storage_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_XP_STORAGE, 1.0F)
                        .build())), getFlag(FLAG_XP_STORAGE_AUGMENT));
    }

    private static void registerStorageAugments() {

        itemsTab(registerItem("rf_coil_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 4.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 4.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("rf_coil_storage_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 6.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 2.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("rf_coil_xfer_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 2.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 6.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("rf_coil_creative_augment", id -> new AugmentItem(itemProperties(id).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 16.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 16.0F)
                        .mod(TAG_AUGMENT_RF_CREATIVE, 1.0F)
                        .build())), getFlag(FLAG_CREATIVE_STORAGE_AUGMENTS));

        itemsTab(registerItem("fluid_tank_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FLUID)
                        .mod(TAG_AUGMENT_FLUID_STORAGE, 4.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("fluid_tank_creative_augment", id -> new AugmentItem(itemProperties(id).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FLUID)
                        .mod(TAG_AUGMENT_FLUID_STORAGE, 16.0F)
                        .mod(TAG_AUGMENT_FLUID_CREATIVE, 1.0F)
                        .build())), getFlag(FLAG_CREATIVE_STORAGE_AUGMENTS));
    }

    private static void registerFilterAugments() {

        itemsTab(registerItem("item_filter_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FILTER)
                        .feature(TAG_FILTER_TYPE, FilterRegistry.ITEM_FILTER_TYPE)
                        .build())), getFlag(FLAG_FILTER_AUGMENTS));

        itemsTab(registerItem("fluid_filter_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FILTER)
                        .feature(TAG_FILTER_TYPE, FilterRegistry.FLUID_FILTER_TYPE)
                        .build())), getFlag(FLAG_FILTER_AUGMENTS));
        //
        //        registerItem("dual_filter_augment", () -> new AugmentItem(properties().group(group),
        //                AugmentDataHelper.builder()
        //                        .type(TAG_AUGMENT_TYPE_FILTER)
        //                        .feature(TAG_FILTER_TYPE, FilterRegistry.DUAL_FILTER_TYPE)
        //                        .build()).setShowInGroups(getFlag(FLAG_FILTER_AUGMENTS)));
    }

    private static void registerMachineAugments() {

        itemsTab(registerItem("machine_speed_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_POWER, 1.0F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.1F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_efficiency_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_SPEED, -0.1F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 0.9F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_efficiency_creative_augment", id -> new AugmentItem(itemProperties(id).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 0.0F)
                        .build())), getFlag(FLAG_CREATIVE_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_output_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_SECONDARY, 0.15F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.25F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_catalyst_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_CATALYST, 0.8F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.25F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_catalyst_creative_augment", id -> new AugmentItem(itemProperties(id).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_CATALYST, 0.0F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_cycle_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_FEATURE_CYCLE_PROCESS, 1.0F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_null_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_FEATURE_SECONDARY_NULL, 1.0F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));
    }

    private static void registerDynamoAugments() {

        itemsTab(registerItem("dynamo_output_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_POWER, 1.0F)
                        .mod(TAG_AUGMENT_DYNAMO_ENERGY, 0.9F)
                        .build())), getFlag(FLAG_DYNAMO_AUGMENTS));

        itemsTab(registerItem("dynamo_fuel_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_ENERGY, 1.1F)
                        .build())), getFlag(FLAG_DYNAMO_AUGMENTS));

        itemsTab(registerItem("dynamo_throttle_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_THROTTLE, 1.0F)
                        .build())), getFlag(FLAG_DYNAMO_AUGMENTS));
    }

    private static void registerAreaAugments() {

        itemsTab(registerItem("area_radius_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_AREA_EFFECT)
                        .mod(TAG_AUGMENT_RADIUS, 1.0F)
                        .build())), getFlag(FLAG_AREA_AUGMENTS));
    }

    private static void registerPotionAugments() {

        itemsTab(registerItem("potion_amplifier_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_POTION)
                        .mod(TAG_AUGMENT_POTION_AMPLIFIER, 1.0F)
                        .mod(TAG_AUGMENT_POTION_DURATION, -0.25F)
                        .build())), getFlag(FLAG_POTION_AUGMENTS));

        itemsTab(registerItem("potion_duration_augment", id -> new AugmentItem(itemProperties(id),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_POTION)
                        .mod(TAG_AUGMENT_POTION_DURATION, 1.0F)
                        .build())), getFlag(FLAG_POTION_AUGMENTS));
    }
    // endregion

    private static void registerSpawnEggs() {

        itemsTab(registerItem("basalz_spawn_egg", id -> new SpawnEggItemCoFH(BASALZ::get, 0x363840, 0x080407, itemProperties(id))));
        itemsTab(registerItem("blizz_spawn_egg", id -> new SpawnEggItemCoFH(BLIZZ::get, 0xD8DBE5, 0x91D9FC, itemProperties(id))));
        itemsTab(registerItem("blitz_spawn_egg", id -> new SpawnEggItemCoFH(BLITZ::get, 0xC9EEFF, 0xFFD97E, itemProperties(id))));
    }
    // endregion

    private static final int BEEKEEPER_DURABILITY = 4;
    private static final int DIVING_DURABILITY = 12;
    private static final int HAZMAT_DURABILITY = 6;

    public static final TagKey<Item> REPAIRS_BEEKEEPER_ARMOR = ItemTags.create(Identifier.fromNamespaceAndPath(ID_THERMAL, "repairs_beekeeper_armor"));
    public static final TagKey<Item> REPAIRS_DIVING_ARMOR = ItemTags.create(Identifier.fromNamespaceAndPath(ID_THERMAL, "repairs_diving_armor"));
    public static final TagKey<Item> REPAIRS_HAZMAT_ARMOR = ItemTags.create(Identifier.fromNamespaceAndPath(ID_THERMAL, "repairs_hazmat_armor"));

    public static final ArmorMaterial BEEKEEPER = ArmorMaterialCoFH.create(BEEKEEPER_DURABILITY, new int[]{1, 2, 3, 1}, 16, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.0F, 0.0F, REPAIRS_BEEKEEPER_ARMOR, ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(ID_THERMAL, "beekeeper")));
    public static final ArmorMaterial DIVING = ArmorMaterialCoFH.create(DIVING_DURABILITY, new int[]{1, 4, 5, 2}, 20, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, REPAIRS_DIVING_ARMOR, ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(ID_THERMAL, "diving")));
    public static final ArmorMaterial HAZMAT = ArmorMaterialCoFH.create(HAZMAT_DURABILITY, new int[]{1, 4, 5, 2}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, REPAIRS_HAZMAT_ARMOR, ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(ID_THERMAL, "hazmat")));

}
