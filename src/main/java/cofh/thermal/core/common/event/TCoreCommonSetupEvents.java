package cofh.thermal.core.common.event;

import cofh.thermal.lib.util.ThermalRecipeManagers;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.RECIPE_TYPES;

@EventBusSubscriber (modid = ID_THERMAL)
public class TCoreCommonSetupEvents {

    private TCoreCommonSetupEvents() {

    }

    //    @SubscribeEvent
    //    public static void setupVillagerTrades(final VillagerTradesEvent event) {
    //
    //        if (!ThermalConfig.enableVillagerTrades.get()) {
    //            return;
    //        }
    //    }

    // region RELOAD
    // Recipes reload during TagsUpdatedEvent
    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent event) {

        if (event instanceof TagsUpdatedEvent.ServerDataLoad serverLoad) {
            ThermalRecipeManagers.instance().setServerRecipeManager(serverLoad.getServerResources().getRecipeManager());
        }
        ThermalRecipeManagers.instance().refreshServer();
        ThermalRecipeManagers.instance().refreshClient();
    }

    // The client only receives the recipe types asked for here; the vanilla types feed the converted recipes.
    @SubscribeEvent
    public static void datapackSync(final OnDatapackSyncEvent event) {

        List<RecipeType<?>> recipeTypes = new ArrayList<>();
        RECIPE_TYPES.getRegistryObjects().values().forEach(holder -> recipeTypes.add(holder.get()));
        recipeTypes.add(RecipeType.CRAFTING);
        recipeTypes.add(RecipeType.SMELTING);
        recipeTypes.add(RecipeType.BLASTING);
        event.sendRecipes(recipeTypes);
    }

    // Capture the recipes and reload when they arrive on the Client side - JEI starts on the same event, so run first.
    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void recipesReceived(final RecipesReceivedEvent event) {

        ThermalRecipeManagers.instance().setClientRecipeMap(event.getRecipeMap());
        ThermalRecipeManagers.instance().refreshClient();
    }
    // endregion
}
