package cofh.thermal.core.client.renderer.model;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;

import java.util.List;
import java.util.Map;

// TODO: Adjust this when Dynamos have more model needs
public class DynamoBakedModel extends UnderlayBakedModel {

    private static final Int2ObjectMap<BakedQuad[]> COIL_QUAD_CACHE = new Int2ObjectOpenHashMap<>();

    private static final Int2ObjectMap<BakedQuad[]> ITEM_QUAD_CACHE = new Int2ObjectOpenHashMap<>();
    private static final Map<List<Integer>, BlockStateModelPart> MODEL_CACHE = new Object2ObjectOpenHashMap<>();

    public static void clearCache() {

        COIL_QUAD_CACHE.clear();

        ITEM_QUAD_CACHE.clear();
        MODEL_CACHE.clear();
    }

    public DynamoBakedModel(BlockStateModel originalModel) {

        super(originalModel);
    }

    // TODO: More coil types; block and item quad creation.
}
