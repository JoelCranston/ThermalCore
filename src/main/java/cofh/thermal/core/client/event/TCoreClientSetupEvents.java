package cofh.thermal.core.client.event;

import cofh.core.client.model.SimpleItemModel;
import cofh.core.client.model.SimpleModel;
import cofh.thermal.core.client.renderer.entity.layers.FestiveLayer;
import cofh.thermal.core.client.renderer.model.*;
import cofh.thermal.core.common.config.ThermalClientConfig;
import cofh.thermal.core.common.fluid.*;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSource;

import static cofh.lib.common.fluid.FluidCoFH.WATER_OVERLAY;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

@EventBusSubscriber (value = Dist.CLIENT, modid = ID_THERMAL)
public class TCoreClientSetupEvents {

    private TCoreClientSetupEvents() {

    }

    @SuppressWarnings ({"rawtypes", "unchecked"})
    @SubscribeEvent
    public static void addRenderLayers(final EntityRenderersEvent.AddLayers event) {

        if (!ThermalClientConfig.festiveVanillaMobs.get()) {
            return;
        }
        var blaze = event.getRenderer(EntityType.BLAZE);
        if (blaze instanceof LivingEntityRenderer livingEntityRenderer) {
            livingEntityRenderer.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) blaze, -0.15F, 0.9F));
        }
        //        var creeper = event.getRenderer(EntityType.CREEPER);
        //        if (creeper instanceof LivingEntityRenderer<Creeper, ? extends EntityModel<Creeper>>) {
        //            creeper.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) creeper, 0.0F, 0.9F));
        //        }
        var enderman = event.getRenderer(EntityType.ENDERMAN);
        if (enderman instanceof LivingEntityRenderer livingEntityRenderer) {
            livingEntityRenderer.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) enderman, -1.15F, 0.9F));
        }
        //        var ghast = event.getRenderer(EntityType.GHAST);
        //        if (ghast instanceof LivingEntityRenderer<Ghast, ? extends EntityModel<Ghast>>) {
        //            ghast.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) ghast, 0.75F, 1.5F));
        //        }
    }

    @SubscribeEvent
    public static void colorSetupBlock(final RegisterColorHandlersEvent.BlockTintSources event) {

        // BlockColors colors = event.getBlockColors();

        // colors.register((state, reader, pos, tintIndex) -> (reader == null || pos == null) ? FoliageColors.getDefault() : BiomeColors.getFoliageColor(reader, pos), BLOCKS.get(ID_RUBBER_LEAVES));
    }

    @SubscribeEvent
    public static void registerModels(final RegisterBlockStateModels event) {

        event.registerModel(Identifier.fromNamespaceAndPath(ID_THERMAL, "underlay"), new SimpleModel.Loader(UnderlayBakedModel::new).codec());
        event.registerModel(Identifier.fromNamespaceAndPath(ID_THERMAL, "dynamo"), new SimpleModel.Loader(DynamoBakedModel::new).codec());
        event.registerModel(Identifier.fromNamespaceAndPath(ID_THERMAL, "reconfigurable"), new SimpleModel.Loader(ReconfigurableBakedModel::new).codec());
        event.registerModel(Identifier.fromNamespaceAndPath(ID_THERMAL, "energy_cell"), new SimpleModel.Loader(EnergyCellBakedModel::new).codec());
        event.registerModel(Identifier.fromNamespaceAndPath(ID_THERMAL, "fluid_cell"), new SimpleModel.Loader(FluidCellBakedModel::new).codec());
        event.registerModel(Identifier.fromNamespaceAndPath(ID_THERMAL, "item_cell"), new SimpleModel.Loader(ItemCellBakedModel::new).codec());
    }

    @SubscribeEvent
    public static void registerItemModels(final RegisterItemModelsEvent event) {

        event.register(Identifier.fromNamespaceAndPath(ID_THERMAL, "reconfigurable"), new SimpleItemModel.Loader(ReconfigurableBakedModel::forItem).codec());
        event.register(Identifier.fromNamespaceAndPath(ID_THERMAL, "energy_cell"), new SimpleItemModel.Loader(EnergyCellBakedModel::forItem).codec());
        event.register(Identifier.fromNamespaceAndPath(ID_THERMAL, "fluid_cell"), new SimpleItemModel.Loader(FluidCellBakedModel::forItem).codec());
        event.register(Identifier.fromNamespaceAndPath(ID_THERMAL, "item_cell"), new SimpleItemModel.Loader(ItemCellBakedModel::forItem).codec());
    }

    @SubscribeEvent
    public static void registerFluidModels(final RegisterFluidModelsEvent event) {

        event.register(fluidModel("creosote", false), CreosoteFluid.instance().still(), CreosoteFluid.instance().flowing());
        event.register(fluidModel("crude_oil", true), CrudeOilFluid.instance().still(), CrudeOilFluid.instance().flowing());
        event.register(fluidModel("ender", true), EnderFluid.instance().still(), EnderFluid.instance().flowing());
        event.register(fluidModel("glowstone", false), GlowstoneFluid.instance().still(), GlowstoneFluid.instance().flowing());
        event.register(fluidModel("heavy_oil", false), HeavyOilFluid.instance().still(), HeavyOilFluid.instance().flowing());
        event.register(fluidModel("latex", false), LatexFluid.instance().still(), LatexFluid.instance().flowing());
        event.register(fluidModel("light_oil", false), LightOilFluid.instance().still(), LightOilFluid.instance().flowing());
        event.register(fluidModel("redstone", true), RedstoneFluid.instance().still(), RedstoneFluid.instance().flowing());
        event.register(fluidModel("refined_fuel", false), RefinedFuelFluid.instance().still(), RefinedFuelFluid.instance().flowing());
        event.register(fluidModel("resin", false), ResinFluid.instance().still(), ResinFluid.instance().flowing());
        event.register(fluidModel("sap", false), SapFluid.instance().still(), SapFluid.instance().flowing());
        event.register(fluidModel("syrup", false), SyrupFluid.instance().still(), SyrupFluid.instance().flowing());
        event.register(fluidModel("tree_oil", false), TreeOilFluid.instance().still(), TreeOilFluid.instance().flowing());
    }

    @SubscribeEvent
    public static void registerClientExtensions(final RegisterClientExtensionsEvent event) {

        event.registerFluidType(new CrudeOilFluid.ClientExtensions(), CrudeOilFluid.TYPE.get());
        event.registerFluidType(new EnderFluid.ClientExtensions(), EnderFluid.TYPE.get());
        event.registerFluidType(new RedstoneFluid.ClientExtensions(), RedstoneFluid.TYPE.get());
    }

    // region HELPERS
    private static FluidModel.Unbaked fluidModel(String name, boolean overlay) {

        return new FluidModel.Unbaked(new Material(Identifier.fromNamespaceAndPath(ID_THERMAL, "block/fluids/" + name + "_still")), new Material(Identifier.fromNamespaceAndPath(ID_THERMAL, "block/fluids/" + name + "_flow")), overlay ? new Material(WATER_OVERLAY) : null, (FluidTintSource) null);
    }
    // endregion
}
