package net.floderfloh.allthepotionsyoullslurp.recipe.custom;

import net.floderfloh.allthepotionsyoullslurp.component.CustomEffectHolder;
import net.floderfloh.allthepotionsyoullslurp.component.ModDataComponentTypes;
import net.floderfloh.allthepotionsyoullslurp.item.ModItems;
import net.floderfloh.allthepotionsyoullslurp.item.custom.MixedPotionBottleItem;
import net.floderfloh.allthepotionsyoullslurp.recipe.ModRecipeSerializer;
import net.floderfloh.allthepotionsyoullslurp.recipe.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

import java.util.ArrayList;
import java.util.List;

public class MixedPotionBottleRecipe extends CustomRecipe {
    public MixedPotionBottleRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
        System.out.println("MixedPotionBottleRecipe initialized!");
    }
    private List<MobEffectInstance> extractPotionEffects(ItemStack potionStack) {
        PotionContents contents = potionStack.get(DataComponents.POTION_CONTENTS);
        if (contents == null) {
            System.out.println("WARN: potionStack has no POTION_CONTENTS component!");
            return List.of();
        }
        List<MobEffectInstance> result = new ArrayList<>();

        // 1. Standard-Trankeffekte
        contents.potion().ifPresent(holder -> {
            Potion potion = holder.value();
            result.addAll(potion.getEffects());
        });

        // 2. Custom-Trankeffekte
        result.addAll(contents.customEffects());

        return result;
    }


    @Override
    public boolean matches(CraftingInput pInput, Level pLevel) {
        boolean foundValidPotion = false;
        boolean foundBottle = false;

        for (int i = 0; i < pInput.size(); i++) {
            ItemStack stack = pInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof PotionItem && stack.get(DataComponents.POTION_CONTENTS) != null) {
                    foundValidPotion = true;
                    System.out.println("JOOOOOOOOOOOOOOOO");
                } else if (stack.getItem() == ModItems.MIXED_POTION_BOTTLE.get()) {
                    foundBottle = true;
                }
            }
        }

        return foundValidPotion && foundBottle;

    }


    @Override
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        System.out.println("2222JOOOOOOOOOOOOOOOO");
        ItemStack result = new ItemStack(ModItems.MIXED_POTION_BOTTLE.get());
        List<MobEffectInstance> combinedEffects = new ArrayList<>();

        for (int i = 0; i < pInput.size(); i++) {
            ItemStack stack = pInput.getItem(i);

            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.POTION) {
                    List<MobEffectInstance> effects = extractPotionEffects(stack);
                    combinedEffects.addAll(effects);
                } else if (stack.getItem() instanceof CustomEffectHolder) {
                    CustomEffectHolder holder = (CustomEffectHolder) stack.getItem();
                    combinedEffects.addAll(holder.getCustomEffects());
                }

            }
        }

        if (!combinedEffects.isEmpty()) {
            result.set(ModDataComponentTypes.STORED_POTION_EFFECTS.get(), combinedEffects);
        }

        return result;
    }






    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 2 && pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.MIXED_BOTTLE_SERIALIZER.get();
    }
    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.MIXED_BOTTLE_TYPE.get();
    }

}
