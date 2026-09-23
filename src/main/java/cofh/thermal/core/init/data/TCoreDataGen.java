package cofh.thermal.core.init.data;

import cofh.thermal.core.init.data.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

@EventBusSubscriber (modid = ID_THERMAL)
public class TCoreDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent.Client event) {

        PackOutput output = event.getGenerator().getPackOutput();

        TCoreDatapackRegistryProvider datapackRegistry = event.createProvider(TCoreDatapackRegistryProvider::new);
        CompletableFuture<HolderLookup.Provider> lookup = datapackRegistry.getRegistryProvider();
        TCoreTagsProvider.Block blockTags = event.addProvider(new TCoreTagsProvider.Block(output, lookup));
        event.addProvider(new TCoreTagsProvider.Item(output, lookup, blockTags.contentsGetter()));
        event.addProvider(new TCoreTagsProvider.Fluid(output, lookup));
        event.addProvider(new TCoreTagsProvider.Entity(output, lookup));
        event.addProvider(new TCoreTagsProvider.DamageType(output, lookup));

        // gen.addProvider(event.includeServer(), new TCoreAdvancementProvider(gen));
        event.addProvider(new TCoreLootTableProvider(output, lookup));
        event.addProvider(new TCoreRecipeProvider.Runner(output, lookup));
    }

}
