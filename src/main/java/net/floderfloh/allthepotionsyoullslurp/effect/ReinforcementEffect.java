package net.floderfloh.allthepotionsyoullslurp.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ReinforcementEffect extends MobEffect {
    public ReinforcementEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // Wird pro Tick aufgerufen, aber wir machen alles lieber in den Events.
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true; // jede Sekunde prüfen
    }
}