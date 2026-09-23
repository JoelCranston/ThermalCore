package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.HIVE_EXTRACTOR_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.HIVE_EXTRACTOR_MAPPING;

public class HiveExtractorMapping extends SerializableRecipe {

    protected final Block hive;
    protected final ItemStackTemplate item;
    protected final FluidStackTemplate fluid;

    private ItemStack itemStack;
    private FluidStack fluidStack;

    public HiveExtractorMapping(Block hive, ItemStackTemplate item, FluidStackTemplate fluid) {

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

    public ItemStackTemplate getItemTemplate() {

        return item;
    }

    public FluidStackTemplate getFluidTemplate() {

        return fluid;
    }

    public ItemStack getItem() {

        if (itemStack == null) {
            itemStack = item.create();
        }
        return itemStack;
    }

    public FluidStack getFluid() {

        if (fluidStack == null) {
            fluidStack = fluid.create();
        }
        return fluidStack;
    }
    // endregion

    // region SERIALIZER
    public static final MapCodec<HiveExtractorMapping> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Block.CODEC.fieldOf(HIVE).forGetter(recipe -> recipe.hive),
                    ItemStackTemplate.CODEC.fieldOf(ITEM).forGetter(recipe -> recipe.item),
                    FluidStackTemplate.CODEC.fieldOf(FLUID).forGetter(recipe -> recipe.fluid)
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
        ItemStackTemplate item = ItemStackTemplate.STREAM_CODEC.decode(buffer);
        FluidStackTemplate fluid = FluidStackTemplate.STREAM_CODEC.decode(buffer);

        return new HiveExtractorMapping(hive, item, fluid);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buffer, HiveExtractorMapping recipe) {

        buffer.writeIdentifier(getRegistryName(recipe.hive));
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.item);
        FluidStackTemplate.STREAM_CODEC.encode(buffer, recipe.fluid);
    }
    // endregion
}
