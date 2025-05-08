package net.floderfloh.allthepotionsyoullslurp.event;

import com.google.common.collect.Lists;
import net.floderfloh.allthepotionsyoullslurp.component.ModDataComponentTypes;
import net.floderfloh.allthepotionsyoullslurp.item.custom.MixedPotionBottleItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = "allthepotionsyoullslurp", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModPotionInteractionHandler {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();

        ItemStack usedStack = event.getItemStack(); // z. B. der Trank
        ItemStack otherStack = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        // Prüfen: Eines muss die MixedPotionBottle sein, das andere ein Trank
        if (!(isPotionItem(usedStack.getItem()) && otherStack.getItem() instanceof MixedPotionBottleItem) &&
                !(isPotionItem(otherStack.getItem()) && usedStack.getItem() instanceof MixedPotionBottleItem)) {
            return;
        }

        // Identifiziere, welches welches ist
        ItemStack potionStack = isPotionItem(usedStack.getItem()) ? usedStack : otherStack;
        ItemStack bottleStack = usedStack.getItem() instanceof MixedPotionBottleItem ? usedStack : otherStack;

        // PotionContents extrahieren
        PotionContents contents = potionStack.get(DataComponents.POTION_CONTENTS);
        if (contents == null || !contents.getAllEffects().iterator().hasNext()) {
            return; // Es gibt keine Effekte
        }


        if (contents == null) return;

        Iterable<MobEffectInstance> newEffectsIterable = contents.getAllEffects();
        List<MobEffectInstance> newEffects = Lists.newArrayList(newEffectsIterable);
        if (newEffects.isEmpty()) return;

        List<MobEffectInstance> existingEffects = bottleStack.get(ModDataComponentTypes.STORED_POTION_EFFECTS.get());

        if (existingEffects == null) existingEffects = new ArrayList<>();
        else existingEffects = new ArrayList<>(existingEffects); // Kopie

        existingEffects.addAll(newEffects);
        bottleStack.set(ModDataComponentTypes.STORED_POTION_EFFECTS.get(), existingEffects);

        if (!player.isCreative()) {
            potionStack.shrink(1); // Trank verbrauchen

            // Glasflasche ins Inventar legen
            ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
            if (!player.getInventory().add(emptyBottle)) {
                player.drop(emptyBottle, false);
            }
        }


        if (!level.isClientSide) {
            player.displayClientMessage(Component.literal("Effekte gespeichert!"), true);
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private static boolean isPotionItem(Item item) {
        return item instanceof PotionItem || item instanceof TippedArrowItem;
    }
}
