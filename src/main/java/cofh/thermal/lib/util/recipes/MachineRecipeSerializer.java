package cofh.thermal.lib.util.recipes;

import cofh.lib.common.fluid.FluidIngredient;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.recipes.JsonMapCodec;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class MachineRecipeSerializer<T extends ThermalRecipe> {

    protected final int defaultEnergy;
    protected final IFactory<T> factory;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);

    public MachineRecipeSerializer(IFactory<T> factory, int defaultEnergy) {

        this.factory = factory;
        this.defaultEnergy = defaultEnergy;
    }

    public RecipeSerializer<T> toVanilla() {

        return new RecipeSerializer<>(codec(), streamCodec());
    }

    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {

        return streamCodec;
    }

    public MapCodec<T> codec() {

        return JsonMapCodec.of(this::fromJson, this::toJson);
    }

    protected T fromJson(JsonObject json) {

        int energy = defaultEnergy;
        float experience = 0.0F;

        ArrayList<Ingredient> inputItems = new ArrayList<>();
        ArrayList<FluidIngredient> inputFluids = new ArrayList<>();
        ArrayList<ItemStackTemplate> outputItems = new ArrayList<>();
        ArrayList<Float> outputItemChances = new ArrayList<>();
        ArrayList<FluidStackTemplate> outputFluids = new ArrayList<>();

        /* INPUT */
        if (json.has(INGREDIENT)) {
            parseInputs(inputItems, inputFluids, json.get(INGREDIENT));
        } else if (json.has(INGREDIENTS)) {
            parseInputs(inputItems, inputFluids, json.get(INGREDIENTS));
        } else if (json.has(INPUT)) {
            parseInputs(inputItems, inputFluids, json.get(INPUT));
        } else if (json.has(INPUTS)) {
            parseInputs(inputItems, inputFluids, json.get(INPUTS));
        }

        /* OUTPUT */
        if (json.has(RESULT)) {
            parseOutputTemplates(outputItems, outputItemChances, outputFluids, json.get(RESULT));
        } else if (json.has(RESULTS)) {
            parseOutputTemplates(outputItems, outputItemChances, outputFluids, json.get(RESULTS));
        } else if (json.has(OUTPUT)) {
            parseOutputTemplates(outputItems, outputItemChances, outputFluids, json.get(OUTPUT));
        } else if (json.has(OUTPUTS)) {
            parseOutputTemplates(outputItems, outputItemChances, outputFluids, json.get(OUTPUTS));
        }

        /* ENERGY */
        if (json.has(ENERGY)) {
            energy = json.get(ENERGY).getAsInt();
        }
        if (json.has(ENERGY_MOD)) {
            energy *= json.get(ENERGY_MOD).getAsFloat();
        }
        energy = MathHelper.clamp(energy, 0, Integer.MAX_VALUE);

        /* XP */
        if (json.has(EXPERIENCE)) {
            experience = json.get(EXPERIENCE).getAsFloat();
        } else if (json.has(XP)) {
            experience = json.get(XP).getAsFloat();
        }
        if (inputItems.isEmpty() && inputFluids.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty() || energy <= 0) {
            throw new JsonSyntaxException("Invalid Thermal Series recipe! Please check your datapacks!");
        }
        return factory.create(energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    protected JsonObject toJson(T recipe) {

        return null;
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {

        int energy = buffer.readVarInt();
        float experience = buffer.readFloat();

        int numInputItems = buffer.readVarInt();
        ArrayList<Ingredient> inputItems = new ArrayList<>(numInputItems);
        for (int i = 0; i < numInputItems; ++i) {
            inputItems.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
        }

        int numInputFluids = buffer.readVarInt();
        ArrayList<FluidIngredient> inputFluids = new ArrayList<>(numInputFluids);
        for (int i = 0; i < numInputFluids; ++i) {
            inputFluids.add(FluidIngredient.fromNetwork(buffer));
        }

        int numOutputItems = buffer.readVarInt();
        ArrayList<ItemStackTemplate> outputItems = new ArrayList<>(numOutputItems);
        ArrayList<Float> outputItemChances = new ArrayList<>(numOutputItems);
        for (int i = 0; i < numOutputItems; ++i) {
            outputItems.add(ItemStackTemplate.STREAM_CODEC.decode(buffer));
            outputItemChances.add(buffer.readFloat());
        }

        int numOutputFluids = buffer.readVarInt();
        ArrayList<FluidStackTemplate> outputFluids = new ArrayList<>(numOutputFluids);
        for (int i = 0; i < numOutputFluids; ++i) {
            outputFluids.add(FluidStackTemplate.STREAM_CODEC.decode(buffer));
        }
        if (inputItems.isEmpty() && inputFluids.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty()) {
            throw new JsonSyntaxException("Invalid Thermal Series recipe! Please check your datapacks!");
        }
        return factory.create(energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {

        buffer.writeVarInt(recipe.energy);
        buffer.writeFloat(recipe.xp);

        List<Ingredient> inputItems = recipe.getInputItems();
        buffer.writeVarInt(inputItems.size());
        for (Ingredient ingredient : inputItems) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }
        List<FluidIngredient> inputFluids = recipe.getInputFluids();
        buffer.writeVarInt(inputFluids.size());
        for (FluidIngredient ingredient : inputFluids) {
            ingredient.toNetwork(buffer);
        }
        List<ItemStackTemplate> outputItems = recipe.getOutputItemTemplates();
        buffer.writeVarInt(outputItems.size());
        for (int i = 0; i < outputItems.size(); ++i) {
            ItemStackTemplate.STREAM_CODEC.encode(buffer, outputItems.get(i));
            buffer.writeFloat(recipe.outputItemChances.get(i));
        }
        List<FluidStackTemplate> outputFluids = recipe.getOutputFluidTemplates();
        buffer.writeVarInt(outputFluids.size());
        for (FluidStackTemplate template : outputFluids) {
            FluidStackTemplate.STREAM_CODEC.encode(buffer, template);
        }
    }

    public interface IFactory<T extends ThermalRecipe> {

        T create(int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStackTemplate> outputItems, List<Float> chance, List<FluidStackTemplate> outputFluids);

    }

}
