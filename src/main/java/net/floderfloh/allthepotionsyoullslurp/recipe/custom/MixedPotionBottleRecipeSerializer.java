package net.floderfloh.allthepotionsyoullslurp.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.codec.StreamCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MixedPotionBottleRecipeSerializer implements RecipeSerializer<MixedPotionBottleRecipe> {


    @Override
    public MapCodec<MixedPotionBottleRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC)
                                .forGetter(recipe -> CraftingBookCategory.MISC) // oder speichere dir das ab, wenn du willst
                ).apply(instance, MixedPotionBottleRecipe::new)
        );
    }


    @Override
    public StreamCodec<RegistryFriendlyByteBuf, MixedPotionBottleRecipe> streamCodec() {

        return StreamCodec.unit(new MixedPotionBottleRecipe(CraftingBookCategory.MISC));
    }
}




