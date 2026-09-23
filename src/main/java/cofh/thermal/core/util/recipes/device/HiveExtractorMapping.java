package cofh.thermal.core.util.recipes.device;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.util.recipes.SerializableRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidStack;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.HIVE_EXTRACTOR_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.HIVE_EXTRACTOR_MAPPING;

public class HiveExtractorMapping extends SerializableRecipe {

    protected final Block hive;
    protected final ItemStack item;
    protected final FluidStack fluid;

    public HiveExtractorMapping(Block hive, ItemStack item, FluidStack fluid) {

        this.hive = hive;
        this.item = item;
        this.fluid = fluid;
    }

    @Override
    public RecipeSerializer<HiveExtractorMapping> getSerializer() {

        return HIVE_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<HiveExtractorMapping> getType() {

        return HIVE_EXTRACTOR_MAPPING.get();
    }

    // region GETTERS
    public Block getHive() {

        return hive;
    }

    public ItemStack getItem() {

        return item;
    }

    public FluidStack getFluid() {

        return fluid;
    }
    // endregion

    // region SERIALIZER
    public static final MapCodec<HiveExtractorMapping> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Block.CODEC.fieldOf(HIVE).forGetter(recipe -> recipe.hive),
                    ItemStack.CODEC.fieldOf(ITEM).forGetter(recipe -> recipe.item),
                    FluidStack.CODEC.fieldOf(FLUID).forGetter(recipe -> recipe.fluid)
            ).apply(builder, HiveExtractorMapping::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, HiveExtractorMapping> STREAM_CODEC = StreamCodec.of(HiveExtractorMapping::toNetwork, HiveExtractorMapping::fromNetwork);

    //        @Override
    //        public HiveExtractorMapping fromJson(ResourceLocation recipeId, JsonObject json) {
    //
    //            Block hive = Blocks.AIR;
    //            ItemStack item = ItemStack.EMPTY;
    //            FluidStack fluid = FluidStack.EMPTY;
    //
    //            if (json.has(HIVE)) {
    //                hive = parseBlock(json.get(HIVE));
    //            }
    //            if (json.has(ITEM)) {
    //                item = parseItemStack(json.get(ITEM));
    //            }
    //            if (json.has(FLUID)) {
    //                fluid = parseFluidStack(json.get(FLUID));
    //            }
    //            return new HiveExtractorMapping(recipeId, hive, item, fluid);
    //        }

    public static HiveExtractorMapping fromNetwork(RegistryFriendlyByteBuf buffer) {

        Block hive = BuiltInRegistries.BLOCK.getValue(buffer.readIdentifier());
        ItemStack item = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
        FluidStack fluid = FluidHelper.readFluidStack(buffer);

        return new HiveExtractorMapping(hive, item, fluid);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buffer, HiveExtractorMapping recipe) {

        buffer.writeIdentifier(getRegistryName(recipe.hive));
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.item);
        FluidHelper.writeFluidStack(buffer, recipe.fluid);
    }
    // endregion
}
