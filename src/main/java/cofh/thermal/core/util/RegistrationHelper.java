package cofh.thermal.core.util;

import cofh.core.common.entity.AbstractGrenade;
import cofh.core.common.entity.AbstractTNTMinecart;
import cofh.core.common.item.*;
import cofh.lib.api.IDetonatable;
import cofh.lib.common.block.TntBlockCoFH;
import cofh.lib.common.entity.PrimedTntCoFH;
import cofh.lib.util.Utils;
import cofh.thermal.core.common.entity.explosive.DetonateUtils;
import cofh.thermal.core.common.entity.explosive.Grenade;
import cofh.thermal.core.common.entity.explosive.ThermalTNTEntity;
import cofh.thermal.core.common.entity.explosive.ThermalTNTMinecart;
import cofh.thermal.lib.common.item.BlockItemAugmentable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.constants.ModIds.ID_THERMAL_LOCOMOTION;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.blocksTab;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.itemsTab;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

public final class RegistrationHelper {

    private RegistrationHelper() {

    }

    // region PROPERTIES
    public static BlockBehaviour.Properties blockProperties(Identifier id) {

        return of().setId(ResourceKey.create(Registries.BLOCK, id));
    }

    public static Item.Properties itemProperties(Identifier id) {

        return Utils.itemProperties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    public static Item.Properties blockItemProperties(Identifier id) {

        return itemProperties(id).useBlockDescriptionPrefix();
    }
    // endregion

    // region BLOCKS
    public static DeferredHolder<Item, Item> registerBlock(String name, Function<Identifier, Block> func) {

        return registerBlock(name, func, ID_THERMAL);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Function<Identifier, Block> func, Rarity rarity) {

        return registerBlock(name, func, rarity, ID_THERMAL);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Function<Identifier, Block> func, String modId) {

        return registerBlock(name, func, Rarity.COMMON, modId);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Function<Identifier, Block> func, Rarity rarity, String modId) {

        return registerBlock(name, func, id -> new BlockItemCoFH(BLOCKS.get(name), blockItemProperties(id).rarity(rarity)).setModId(modId));
    }

    public static void registerBlockOnly(String name, Function<Identifier, Block> func) {

        BLOCKS.register(name, func);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Function<Identifier, Block> blockFunc, Function<Identifier, Item> itemFunc) {

        BLOCKS.register(name, blockFunc);
        return registerItem(name, itemFunc);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Supplier<Block> sup) {

        return registerBlock(name, sup, ID_THERMAL);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Supplier<Block> sup, Rarity rarity) {

        return registerBlock(name, sup, rarity, ID_THERMAL);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Supplier<Block> sup, String modId) {

        return registerBlock(name, sup, Rarity.COMMON, modId);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Supplier<Block> sup, Rarity rarity, String modId) {

        BLOCKS.register(name, sup);
        return registerItem(name, id -> new BlockItemCoFH(BLOCKS.get(name), blockItemProperties(id).rarity(rarity)).setModId(modId));
    }

    public static void registerBlockOnly(String name, Supplier<Block> sup) {

        BLOCKS.register(name, sup);
    }

    public static DeferredHolder<Item, Item> registerBlock(String name, Supplier<Block> blockSup, Supplier<Item> itemSup) {

        BLOCKS.register(name, blockSup);
        return registerItem(name, itemSup);
    }
    // endregion

    // region AUGMENTABLE BLOCKS
    public static DeferredHolder<Item, Item> registerAugmentableBlock(String name, Function<Identifier, Block> func, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment) {

        return registerAugmentableBlock(name, func, numSlots, validAugment, ID_THERMAL);
    }

    public static DeferredHolder<Item, Item> registerAugmentableBlock(String name, Function<Identifier, Block> func, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, String modId) {

        return registerAugmentableBlock(name, func, numSlots, validAugment, Rarity.COMMON, modId);
    }

    public static DeferredHolder<Item, Item> registerAugmentableBlock(String name, Function<Identifier, Block> func, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, Rarity rarity, String modId) {

        BLOCKS.register(name, func);
        return registerItem(name, id -> new BlockItemAugmentable(BLOCKS.get(name), blockItemProperties(id).rarity(rarity)).setNumSlots(numSlots).setAugValidator(validAugment).setModId(modId));
    }

    public static DeferredHolder<Item, Item> registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment) {

        return registerAugmentableBlock(name, sup, numSlots, validAugment, ID_THERMAL);
    }

    public static DeferredHolder<Item, Item> registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, String modId) {

        return registerAugmentableBlock(name, sup, numSlots, validAugment, Rarity.COMMON, modId);
    }

    public static DeferredHolder<Item, Item> registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, Rarity rarity, String modId) {

        BLOCKS.register(name, sup);
        return registerItem(name, id -> new BlockItemAugmentable(BLOCKS.get(name), blockItemProperties(id).rarity(rarity)).setNumSlots(numSlots).setAugValidator(validAugment).setModId(modId));
    }
    // endregion

    // region BLOCK SETS
    public static void registerWoodBlockSet(String woodName, MapColor color, float hardness, float resistance, SoundType soundType, WoodType type, String modId) {

        blocksTab(150, registerBlock(woodName + "_planks", id -> new Block(blockProperties(id).mapColor(color).instrument(NoteBlockInstrument.BASS).strength(hardness, resistance).sound(soundType)), modId));
        blocksTab(150, registerBlock(woodName + "_slab", id -> new SlabBlock(blockProperties(id).mapColor(color).instrument(NoteBlockInstrument.BASS).strength(hardness, resistance).sound(soundType)), modId));
        blocksTab(150, registerBlock(woodName + "_stairs", id -> new StairBlock(BLOCKS.get(woodName + "_planks").defaultBlockState(), blockProperties(id).mapColor(color).instrument(NoteBlockInstrument.BASS).strength(hardness, resistance).sound(soundType)), modId));
        blocksTab(150, registerBlock(woodName + "_door", id -> new DoorBlock(type.setType(), blockProperties(id).mapColor(color).instrument(NoteBlockInstrument.BASS).strength(resistance).sound(soundType).noOcclusion()), modId));
        blocksTab(150, registerBlock(woodName + "_trapdoor", id -> new TrapDoorBlock(type.setType(), blockProperties(id).mapColor(color).instrument(NoteBlockInstrument.BASS).strength(resistance).sound(soundType).noOcclusion().isValidSpawn((state, reader, pos, entityType) -> false)), modId));
        blocksTab(150, registerBlock(woodName + "_button", id -> new ButtonBlock(type.setType(), 30, blockProperties(id).noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY)), modId));
        blocksTab(150, registerBlock(woodName + "_pressure_plate", id -> new PressurePlateBlock(type.setType(), blockProperties(id).mapColor(color).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY)), modId));
        blocksTab(150, registerBlock(woodName + "_fence", id -> new FenceBlock(blockProperties(id).mapColor(color).instrument(NoteBlockInstrument.BASS).strength(hardness, resistance).sound(soundType)), modId));
        blocksTab(150, registerBlock(woodName + "_fence_gate", id -> new FenceGateBlock(type, blockProperties(id).mapColor(color).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(hardness, resistance).ignitedByLava()), modId));
    }
    // endregion

    // region ITEMS
    public static DeferredHolder<Item, Item> registerItem(String name, Function<Identifier, Item> func) {

        return ITEMS.register(name, func);
    }

    public static DeferredHolder<Item, Item> registerItem(String name, Supplier<Item> sup) {

        return ITEMS.register(name, sup);
    }

    public static DeferredHolder<Item, Item> registerItem(String name) {

        return registerItem(name, Rarity.COMMON);
    }

    public static DeferredHolder<Item, Item> registerItem(String name, Rarity rarity) {

        return registerItem(name, id -> new ItemCoFH(itemProperties(id).rarity(rarity)));
    }
    // endregion

    // region METAL SETS
    public static void registerMetalSet(String prefix, Rarity rarity) {

        registerMetalSet(prefix, rarity, false, false, ID_THERMAL);
    }

    public static void registerMetalSet(String prefix) {

        registerMetalSet(prefix, Rarity.COMMON, false, false, ID_THERMAL);
    }

    public static void registerMetalSet(String prefix, String modId) {

        registerMetalSet(prefix, Rarity.COMMON, false, false, modId);
    }

    public static void registerAlloySet(String prefix, Rarity rarity) {

        registerMetalSet(prefix, rarity, false, true, ID_THERMAL);
    }

    public static void registerAlloySet(String prefix) {

        registerMetalSet(prefix, Rarity.COMMON, false, true, ID_THERMAL);
    }

    public static void registerAlloySet(String prefix, String modId) {

        registerMetalSet(prefix, Rarity.COMMON, false, true, modId);
    }

    public static void registerVanillaMetalSet(String prefix) {

        registerMetalSet(prefix, Rarity.COMMON, true, false, ID_THERMAL);
    }

    public static void registerMetalSet(String prefix, Rarity rarity, boolean vanilla, boolean alloy, String modId) {

        registerMetalSet(prefix, rarity, vanilla, alloy, modId, vanilla ? 950 : alloy ? 1050 : 1000);
    }

    public static void registerMetalSet(String prefix, Rarity rarity, boolean vanilla, boolean alloy, String modId, int order) {

        // Hacky but whatever.
        if (prefix.equals("copper") || prefix.equals("netherite")) {
            itemsTab(order, registerItem(prefix + "_nugget", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        }
        if (!vanilla) {
            if (!alloy) {
                itemsTab(order, registerItem("raw_" + prefix, id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
            }
            itemsTab(order, registerItem(prefix + "_ingot", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
            itemsTab(order, registerItem(prefix + "_nugget", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        }
        itemsTab(order, registerItem(prefix + "_dust", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        itemsTab(order, registerItem(prefix + "_gear", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        itemsTab(order, registerItem(prefix + "_plate", id -> new CountedItem(itemProperties(id).rarity(rarity)).setModId(modId)));
        itemsTab(order, registerItem(prefix + "_coin", id -> new CoinItem(itemProperties(id).rarity(rarity)).setModId(modId)));
    }
    // endregion

    // region GEM SETS
    public static void registerGemSet(String prefix, Rarity rarity) {

        registerGemSet(prefix, rarity, false, ID_THERMAL);
    }

    public static void registerGemSet(String prefix) {

        registerGemSet(prefix, Rarity.COMMON, false, ID_THERMAL);
    }

    public static void registerGemSet(String prefix, String modId) {

        registerGemSet(prefix, Rarity.COMMON, false, modId);
    }

    public static void registerVanillaGemSet(String prefix) {

        registerGemSet(prefix, Rarity.COMMON, true, ID_THERMAL);
    }

    public static void registerGemSet(String prefix, Rarity rarity, boolean vanilla, String modId) {

        int order = vanilla ? 1100 : 1150;

        if (!vanilla) {
            itemsTab(order, registerItem(prefix, id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        }
        // itemsTab(registerItem(prefix + "_nugget", () -> new ItemCoFH(itemProperties().group(group).rarity(rarity)).setModId(modId)));
        itemsTab(order, registerItem(prefix + "_dust", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        itemsTab(order, registerItem(prefix + "_gear", id -> new ItemCoFH(itemProperties(id).rarity(rarity)).setModId(modId)));
        // itemsTab(registerItem(prefix + "_plate", () -> new CountedItem(itemProperties().group(group).rarity(rarity)).setModId(modId)));
        // itemsTab(registerItem(prefix + "_coin", () -> new CoinItem(itemProperties().group(group).rarity(rarity)).setModId(modId)));
    }
    // endregion

    // region EXPLOSIVES
    public static DeferredHolder<Item, Item> registerGrenade(String id, IDetonatable.IDetonateAction action) {

        Supplier<EntityType<? extends AbstractGrenade>> entity = ENTITIES.register(id, location -> EntityType.Builder.<Grenade>of((type, world) -> new Grenade(type, world, action), MobCategory.MISC).sized(0.25F, 0.25F).build(ResourceKey.create(Registries.ENTITY_TYPE, location)));
        DetonateUtils.GRENADES.add(entity);
        return registerItem(id, location -> new GrenadeItem(new GrenadeItem.IGrenadeFactory<>() {

            @Override
            public AbstractGrenade createGrenade(Level level, LivingEntity living) {

                return new Grenade(entity.get(), level, action, living);
            }

            @Override
            public AbstractGrenade createGrenade(Level level, double posX, double posY, double posZ) {

                return new Grenade(entity.get(), level, action, posX, posY, posZ);
            }

        }, itemProperties(location).stacksTo(16)));
    }

    public static DeferredHolder<Item, Item> registerTNT(String id, IDetonatable.IDetonateAction action) {

        Supplier<EntityType<? extends PrimedTntCoFH>> tntEntity = ENTITIES.register(id, location -> EntityType.Builder.<ThermalTNTEntity>of((type, world) -> new ThermalTNTEntity(type, world, action), MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ResourceKey.create(Registries.ENTITY_TYPE, location)));
        registerBlockOnly(id, location -> new TntBlockCoFH((world, x, y, z, igniter) -> new ThermalTNTEntity(tntEntity.get(), world, action, x, y, z, igniter), blockProperties(location).mapColor(MapColor.COLOR_YELLOW).strength(0.0F).sound(SoundType.GRASS)));
        DetonateUtils.TNT.add(tntEntity);
        return registerItem(id, location -> new BlockItemCoFH(BLOCKS.get(id), blockItemProperties(location)));

    }

    public static DeferredHolder<Item, Item> registerTNTMinecart(String id, String tntId, IDetonatable.IDetonateAction action) {

        Supplier<EntityType<? extends AbstractTNTMinecart>> entity = ENTITIES.register(id, location -> EntityType.Builder.<ThermalTNTMinecart>of((type, world) -> new ThermalTNTMinecart(type, world, action, BLOCKS.get(tntId)), MobCategory.MISC).sized(0.98F, 0.7F).build(ResourceKey.create(Registries.ENTITY_TYPE, location)));
        DetonateUtils.CARTS.add(entity);
        return registerItem(id, location -> new MinecartItemCoFH((world, x, y, z) -> new ThermalTNTMinecart(entity.get(), world, action, BLOCKS.get(tntId), x, y, z), itemProperties(location)).setModId(ID_THERMAL_LOCOMOTION));
    }
    // endregion

    // region ID AFFIXES
    public static String deepslate(String id) {

        return "deepslate_" + id;
    }

    public static String netherrack(String id) {

        return "netherrack_" + id;
    }

    public static String raw(String id) {

        return "raw_" + id;
    }

    public static String block(String id) {

        return id + "_block";
    }

    public static String seeds(String id) {

        return id + "_seeds";
    }

    public static String spores(String id) {

        return id + "_spores";
    }
    // endregion
}
