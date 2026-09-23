package cofh.thermal.core.client.renderer.model;

import cofh.core.client.renderer.model.ModelUtils;
import cofh.core.client.renderer.model.ModelUtils.WrappedBakedModelBuilder;
import cofh.core.util.helpers.ItemHelper;
import cofh.lib.util.crafting.ComparableItemStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.model.data.ModelData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.constants.NBTTags.TAG_SIDES;
import static cofh.thermal.core.client.ThermalTextures.*;
import static cofh.thermal.lib.util.Constants.DEFAULT_MACHINE_SIDES_RAW;
import static net.minecraft.core.Direction.*;

public class ReconfigurableBakedModel extends UnderlayBakedModel {

    private static final Int2ObjectMap<BakedQuad[]> SIDE_QUAD_CACHE = new Int2ObjectOpenHashMap<>();

    private static final Int2ObjectMap<BakedQuad[]> ITEM_QUAD_CACHE = new Int2ObjectOpenHashMap<>();
    private static final Map<List<Integer>, BlockStateModelPart> MODEL_CACHE = new Object2ObjectOpenHashMap<>();

    public static void clearCache() {

        SIDE_QUAD_CACHE.clear();

        ITEM_QUAD_CACHE.clear();
        MODEL_CACHE.clear();
    }

    public ReconfigurableBakedModel(BlockStateModel originalModel) {

        super(originalModel);
    }

    @Override
    protected void addQuads(WrappedBakedModelBuilder builder, BlockState state, Direction side, ModelData extraData) {

        List<BakedQuad> quads = builder.getQuads(side);
        if (quads.isEmpty()) {
            return;
        }
        BakedQuad baseQuad = quads.get(0);
        int sideIndex = side.get3DDataValue();

        // SIDES
        byte[] sideConfigRaw = extraData.get(ModelUtils.SIDES);
        if (sideConfigRaw == null) {
            // This shouldn't happen, but playing it safe.
            return;
        }
        int configHash = Arrays.hashCode(sideConfigRaw);
        BakedQuad[] cachedSideQuads = SIDE_QUAD_CACHE.get(configHash);
        if (cachedSideQuads == null || cachedSideQuads.length < 6) {
            cachedSideQuads = new BakedQuad[6];
        }
        if (cachedSideQuads[sideIndex] == null) {
            cachedSideQuads[sideIndex] = ModelUtils.retexture(baseQuad, getConfigTexture(sideConfigRaw[sideIndex]));
            SIDE_QUAD_CACHE.put(configHash, cachedSideQuads);
        }
        builder.addFaceQuad(side, cachedSideQuads[sideIndex]);

        // FLUID
        super.addUnderlayQuads(builder, state, side, extraData);
    }

    public static BlockStateModelPart forItem(ItemStack stack, BlockStateModelPart model) {

        CompoundTag tag = ItemHelper.getBlockEntityData(stack);
        byte[] sideConfigRaw = getSideConfigRaw(tag);
        int itemHash = new ComparableItemStack(stack).hashCode();
        int configHash = Arrays.hashCode(sideConfigRaw);

        BlockStateModelPart ret = MODEL_CACHE.get(Arrays.asList(itemHash, configHash));
        if (ret == null) {
            WrappedBakedModelBuilder builder = new WrappedBakedModelBuilder(model);

            // SIDES
            BakedQuad[] cachedQuads = ITEM_QUAD_CACHE.get(configHash);
            if (cachedQuads == null || cachedQuads.length < 6) {
                cachedQuads = new BakedQuad[6];

                cachedQuads[0] = ModelUtils.retexture(builder.getQuads(DOWN).get(0), getConfigTexture(sideConfigRaw[0]));
                cachedQuads[1] = ModelUtils.retexture(builder.getQuads(UP).get(0), getConfigTexture(sideConfigRaw[1]));
                cachedQuads[2] = ModelUtils.retexture(builder.getQuads(NORTH).get(0), getConfigTexture(sideConfigRaw[2]));
                cachedQuads[3] = ModelUtils.retexture(builder.getQuads(SOUTH).get(0), getConfigTexture(sideConfigRaw[3]));
                cachedQuads[4] = ModelUtils.retexture(builder.getQuads(WEST).get(0), getConfigTexture(sideConfigRaw[4]));
                cachedQuads[5] = ModelUtils.retexture(builder.getQuads(EAST).get(0), getConfigTexture(sideConfigRaw[5]));
                ITEM_QUAD_CACHE.put(configHash, cachedQuads);
            }
            builder.addFaceQuad(DOWN, cachedQuads[0]);
            builder.addFaceQuad(UP, cachedQuads[1]);
            builder.addFaceQuad(NORTH, cachedQuads[2]);
            builder.addFaceQuad(SOUTH, cachedQuads[3]);
            builder.addFaceQuad(WEST, cachedQuads[4]);
            builder.addFaceQuad(EAST, cachedQuads[5]);

            ret = builder.build();
            MODEL_CACHE.put(Arrays.asList(itemHash, configHash), ret);
        }
        return ret;
    }

    // region HELPERS
    private static TextureAtlasSprite getConfigTexture(byte side) {

        switch (side) {
            case 1:
                return MACHINE_CONFIG_INPUT;
            case 2:
                return MACHINE_CONFIG_OUTPUT;
            case 3:
                return MACHINE_CONFIG_BOTH;
            case 4:
                return MACHINE_CONFIG_ACCESSIBLE;
            default:
                return MACHINE_CONFIG_NONE;
        }
    }

    private static byte[] getSideConfigRaw(CompoundTag tag) {

        if (tag == null) {
            return DEFAULT_MACHINE_SIDES_RAW;
        }
        byte[] ret = tag.getByteArray(TAG_SIDES).orElse(DEFAULT_MACHINE_SIDES_RAW);
        return ret.length == 0 ? DEFAULT_MACHINE_SIDES_RAW : ret;
    }
    // endregion
}
