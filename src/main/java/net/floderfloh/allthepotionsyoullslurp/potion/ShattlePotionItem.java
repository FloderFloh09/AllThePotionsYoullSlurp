package net.floderfloh.allthepotionsyoullslurp.potion;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShattlePotionItem extends ThrowablePotionItem {
    public ShattlePotionItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        PotionContents potioncontents = pStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        potioncontents.addPotionTooltip(pTooltipComponents::add, 0.2F, pContext.tickRate());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pLevel.playSound(
                null,
                pPlayer.getX(),
                pPlayer.getY(),
                pPlayer.getZ(),
                SoundEvents.LINGERING_POTION_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            ShattlePotionEntity thrownpotion = new ShattlePotionEntity(pPlayer, pLevel);

            thrownpotion.setItem(itemstack);
            thrownpotion.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), -10.0F, 1.2F, 1.0F);
            pLevel.addFreshEntity(thrownpotion);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, pPlayer);
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
    // Überschreiben der getThrowableEntity Methode, um die richtige Entität zu erzeugen

}
