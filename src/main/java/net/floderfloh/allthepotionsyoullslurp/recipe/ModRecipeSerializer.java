package net.floderfloh.allthepotionsyoullslurp.recipe;

import com.mojang.authlib.properties.PropertyMap;
import com.mojang.serialization.MapCodec;
import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.floderfloh.allthepotionsyoullslurp.recipe.custom.MixedPotionBottleRecipe;
import net.floderfloh.allthepotionsyoullslurp.recipe.custom.MixedPotionBottleRecipeSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializer {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AllThePotionsYoullSlurp.MOD_ID);


    public static final RegistryObject<RecipeSerializer<MixedPotionBottleRecipe>> MIXED_BOTTLE_SERIALIZER =
            SERIALIZER.register("mixed_bottle_serializer", MixedPotionBottleRecipeSerializer::new);



    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
    }


}
