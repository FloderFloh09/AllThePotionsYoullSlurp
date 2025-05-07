package net.floderfloh.allthepotionsyoullslurp.effect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;


public class PersistenceEffect extends MobEffect {
    public PersistenceEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        var effects = entity.getActiveEffects().stream()
                .filter(e -> e.getEffect().value() != this && e.getEffect().value().getCategory() != MobEffectCategory.HARMFUL)
                .toList();

        for (var effectInstance : effects) {
            int extraTicks = (amplifier + 1) * 5;

            entity.addEffect(new MobEffectInstance(
                    effectInstance.getEffect(),
                    effectInstance.getDuration() + extraTicks,
                    effectInstance.getAmplifier(),
                    effectInstance.isAmbient(),
                    effectInstance.isVisible(),
                    effectInstance.showIcon()
            ));
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 40 == 0; // alle 2 Sekunden
    }

}
