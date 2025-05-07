package net.floderfloh.allthepotionsyoullslurp.event;

import net.floderfloh.allthepotionsyoullslurp.item.ModItems;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "allthepotionsyoullslurp", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemColors {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        ItemColors itemColors = event.getItemColors();

        itemColors.register(
                (stack, layer) -> {
                    if (layer != 0) return -1;
                    return getPotionColor(stack); // hier wird unsere Methode verwendet
                },
                ModItems.SHATTLE_POTION.get()
        );
    }


    private static int getPotionColor(ItemStack stack) {
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);

        Iterable<MobEffectInstance> effects = contents.getAllEffects();
        if (!effects.iterator().hasNext()) {
            return 0x385dc6;
        }

        int r = 0, g = 0, b = 0;
        int n = 0;

        for (MobEffectInstance effect : contents.getAllEffects()) {
            int color = effect.getEffect().value().getColor();
            r += (color >> 16) & 0xFF;
            g += (color >> 8) & 0xFF;
            b += color & 0xFF;
            n++;
        }

        return (r / n << 16) | (g / n << 8) | (b / n);
    }
}

