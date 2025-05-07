package net.floderfloh.allthepotionsyoullslurp.recipe;

import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.floderfloh.allthepotionsyoullslurp.recipe.custom.MixedPotionBottleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, AllThePotionsYoullSlurp.MOD_ID);

    public static final RegistryObject<RecipeType<MixedPotionBottleRecipe>> MIXED_BOTTLE_TYPE =
            RECIPE_TYPES.register("mixed_potion_bottle", () -> new RecipeType<>() {
                public String toString() {
                    return AllThePotionsYoullSlurp.MOD_ID + ":mixed_potion_bottle";
                }
            });
//hello
    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}

