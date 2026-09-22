package cofh.thermal.core.init.data.providers;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import cofh.lib.init.data.LootTableProviderCoFH;
import cofh.thermal.core.init.data.tables.TCoreBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class TCoreLootTableProvider extends LootTableProviderCoFH {

    public TCoreLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {

        // A SubProviderEntry's factory takes the registries now (loot references registry objects).
        super(output, List.of(
                new SubProviderEntry(TCoreBlockLootTables::new, LootContextParamSets.BLOCK)
        ), registries);
    }

}
