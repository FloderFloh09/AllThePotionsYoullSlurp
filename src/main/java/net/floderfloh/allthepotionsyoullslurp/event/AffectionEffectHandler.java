package net.floderfloh.allthepotionsyoullslurp.event;

import net.floderfloh.allthepotionsyoullslurp.effect.ModEffects;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "allthepotionsyoullslurp")
public class AffectionEffectHandler {

    @SubscribeEvent
    public static void onPlayerInteractWithVillager(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Villager villager)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getSide().isClient()) return;

        if (player.hasEffect(ModEffects.AFFECTION.getHolder().get())) {
            int amplifier = player.getEffect(ModEffects.AFFECTION.getHolder().get()).getAmplifier() + 1;


            for (MerchantOffer offer : villager.getOffers()) {
                ItemStack price = offer.getBaseCostA();
                double factor = 1 -(0.12 * amplifier);
                double haha = price.getCount() * factor;
                int newSpecialPriceDiff = (int) ((price.getCount() - haha) * (-1));
                offer.setSpecialPriceDiff(newSpecialPriceDiff);
            }
        } else {
            for (MerchantOffer offer : villager.getOffers()) {
                offer.setSpecialPriceDiff(0); // Setzt den Preis wieder auf den Standardwert
            }
        }
    }
}






