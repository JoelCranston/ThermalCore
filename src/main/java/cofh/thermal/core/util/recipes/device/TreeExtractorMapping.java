package cofh.thermal.core.util.recipes.device;

import cofh.lib.common.block.BlockIngredient;
import cofh.lib.util.Utils;
import cofh.lib.util.recipes.JsonMapCodec;
import cofh.lib.util.recipes.SerializableRecipe;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import javax.annotation.Nullable;
import java.util.Optional;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.TREE_EXTRACTOR_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.TREE_EXTRACTOR_MAPPING;

public class TreeExtractorMapping extends SerializableRecipe {

    protected final Block sapling;
    protected final BlockIngredient trunk;
    protected final BlockIngredient leaves;
    @Nullable
    protected final FluidStackTemplate fluid;
    private FluidStack fluidStack;
    protected final int minHeight;
    protected final int maxHeight;
    protected final int minLeaves;
    protected final int maxLeaves;

    public TreeExtractorMapping(BlockIngredient trunk, BlockIngredient leaves, Block sapling, @Nullable FluidStackTemplate fluid, int minHeight, int maxHeight, int minLeaves, int maxLeaves) {

        this.trunk = trunk;
        this.leaves = leaves;
        this.sapling = sapling;
        this.fluid = fluid;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minLeaves = minLeaves;
        this.maxLeaves = maxLeaves;
    }

    @Override
    public RecipeSerializer<TreeExtractorMapping> getSerializer() {

        return TREE_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<TreeExtractorMapping> getType() {

        return TREE_EXTRACTOR_MAPPING.get();
    }

    // region GETTERS
    public BlockIngredient getTrunk() {

        return trunk;
    }

    public BlockIngredient getLeaves() {

        return leaves;
    }

    public Block getSapling() {

        return sapling;
    }

    @Nullable
    public FluidStackTemplate getFluidTemplate() {

        return fluid;
    }

    public FluidStack getFluid() {

        if (fluidStack == null) {
            fluidStack = fluid == null ? FluidStack.EMPTY : fluid.create();
        }
        return fluidStack;
    }

    public int getMinLeaves() {

        return minLeaves;
    }

    public int getMaxLeaves() {

        return maxLeaves;
    }

    public int getMinHeight() {

        return minHeight;
    }

    public int getMaxHeight() {

        return maxHeight;
    }
    // endregion

    // region SERIALIZER
    public static final MapCodec<TreeExtractorMapping> CODEC = JsonMapCodec.of(TreeExtractorMapping::fromJson, TreeExtractorMapping::toJson);

    public static final StreamCodec<RegistryFriendlyByteBuf, TreeExtractorMapping> STREAM_CODEC = StreamCodec.of(TreeExtractorMapping::toNetwork, TreeExtractorMapping::fromNetwork);

    public static TreeExtractorMapping fromJson(JsonObject json) {

        BlockIngredient logs = BlockIngredient.EMPTY;
        BlockIngredient leaves = BlockIngredient.EMPTY;
        Block sapling = Blocks.AIR;
        FluidStackTemplate fluid = null;
        int minLeaves = 3;
        int maxLeaves = 3;
        int minHeight = 3;
        int maxHeight = 3;

        if (json.has(TRUNK)) {
            logs = getAsBlockIngredient(json, TRUNK);
        }

        if (json.has(LEAF)) {
            leaves = getAsBlockIngredient(json, LEAF);
        } else if (json.has(LEAVES)) {
            leaves = getAsBlockIngredient(json, LEAVES);
        }

        if (json.has(SAPLING)) {
            sapling = parseBlock(json.get(SAPLING));
        }

        if (json.has(RESULT)) {
            fluid = parseFluidStackTemplate(json.get(RESULT));
        } else if (json.has(FLUID)) {
            fluid = parseFluidStackTemplate(json.get(FLUID));
        }

        if (json.has(MIN_HEIGHT)) {
            minHeight = json.get(MIN_HEIGHT).getAsInt();
        }
        if (json.has(MAX_HEIGHT)) {
            maxHeight = json.get(MAX_HEIGHT).getAsInt();
        }
        if (json.has(MIN_LEAVES)) {
            minLeaves = json.get(MIN_LEAVES).getAsInt();
        }
        if (json.has(MAX_LEAVES)) {
            maxLeaves = json.get(MAX_LEAVES).getAsInt();
        }
        return new TreeExtractorMapping(logs, leaves, sapling, fluid, minHeight, Math.max(maxHeight, minHeight), minLeaves, Math.max(maxLeaves, minLeaves));
    }

    protected static JsonObject toJson(TreeExtractorMapping mapping) {

        return null;
    }

    public static TreeExtractorMapping fromNetwork(RegistryFriendlyByteBuf buffer) {

        BlockIngredient logs = BlockIngredient.fromNetwork(buffer);
        BlockIngredient leaves = BlockIngredient.fromNetwork(buffer);
        Block sapling = BuiltInRegistries.BLOCK.getValue(buffer.readIdentifier());
        FluidStackTemplate fluid = ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC).decode(buffer).orElse(null);
        int minHeight = buffer.readInt();
        int maxHeight = buffer.readInt();
        int minLeaves = buffer.readInt();
        int maxLeaves = buffer.readInt();

        return new TreeExtractorMapping(logs, leaves, sapling, fluid, minHeight, maxHeight, minLeaves, maxLeaves);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buffer, TreeExtractorMapping recipe) {

        recipe.trunk.toNetwork(buffer);
        recipe.leaves.toNetwork(buffer);
        buffer.writeIdentifier(Utils.getRegistryName(recipe.sapling));
        ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC).encode(buffer, Optional.ofNullable(recipe.fluid));
        buffer.writeInt(recipe.minHeight);
        buffer.writeInt(recipe.maxHeight);
        buffer.writeInt(recipe.minLeaves);
        buffer.writeInt(recipe.maxLeaves);
    }
    // endregion
}
