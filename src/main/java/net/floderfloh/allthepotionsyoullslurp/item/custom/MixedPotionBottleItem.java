package net.floderfloh.allthepotionsyoullslurp.item.custom;

import net.floderfloh.allthepotionsyoullslurp.component.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MixedPotionBottleItem extends Item {

    public MixedPotionBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide()) {
            List<MobEffectInstance> effects = stack.get(ModDataComponentTypes.STORED_POTION_EFFECTS.get());
            if (effects != null) {
                for (MobEffectInstance effect : effects) {
                    entity.addEffect(new MobEffectInstance(effect));
                }
            }
            stack.remove(ModDataComponentTypes.STORED_POTION_EFFECTS.get());
        }

        return stack;
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        List<MobEffectInstance> effects = stack.get(ModDataComponentTypes.STORED_POTION_EFFECTS.get());
        if (effects == null || effects.isEmpty()) {
            tooltip.add(Component.literal("Keine gespeicherten Effekte").withStyle(ChatFormatting.GRAY));
        } else {
            for (MobEffectInstance effect : effects) {
                MutableComponent name = Component.translatable(effect.getEffect().value().getDescriptionId());
                int amplifier = effect.getAmplifier();
                int duration = effect.getDuration();
                tooltip.add(Component.literal("• ")
                        .append(name)
                        .append(" " + romanNumeral(amplifier + 1))
                        .append(" (" + formatDuration(duration) + ")")
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }

    private String formatDuration(int ticks) {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds %= 60;
        return minutes + ":" + String.format("%02d", seconds);
    }

    private String romanNumeral(int number) {
        return switch (number) {
            case 1 -> "I"; case 2 -> "II"; case 3 -> "III"; case 4 -> "IV";
            case 5 -> "V"; case 6 -> "VI"; case 7 -> "VII"; case 8 -> "VIII";
            case 9 -> "IX"; case 10 -> "X";
            default -> Integer.toString(number);
        };
    }

    public static void addEffect(ItemStack stack, MobEffectInstance effect) {
        List<MobEffectInstance> effects = new ArrayList<>(stack.getOrDefault(ModDataComponentTypes.STORED_POTION_EFFECTS.get(), List.of()));
        effects.add(effect);
        stack.set(ModDataComponentTypes.STORED_POTION_EFFECTS.get(), effects);
    }
}


