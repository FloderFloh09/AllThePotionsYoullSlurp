package net.floderfloh.allthepotionsyoullslurp.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

import java.util.List;

public class AffectionEffect extends MobEffect {
    public AffectionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {


        if (entity instanceof Animal animal) {
            if (entity.level().isClientSide) return false;

            // Wenn das Tier schon liebesbereit ist → nichts tun
            if (animal.isInLove()) return false;

            List<Animal> nearbyPartners = entity.level().getEntitiesOfClass(
                            animal.getClass(),
                            entity.getBoundingBox().inflate(3.0D),
                            e -> e != entity
                                    && e instanceof Animal partner
                                    && partner.hasEffect(ModEffects.AFFECTION.getHolder().get())
                                    && !partner.isInLove()
                    ).stream()
                    .map(e -> (Animal) e)
                    .toList();


            if (!nearbyPartners.isEmpty()) {
                Animal partner = nearbyPartners.get(0);

                // Beide Tiere bereit machen
                animal.setInLove(null);
                partner.setInLove(null);

                // Effekt bei beiden entfernen
                animal.removeEffect(ModEffects.AFFECTION.getHolder().get());
                partner.removeEffect(ModEffects.AFFECTION.getHolder().get());

                return true;
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0; // jede Sekunde prüfen
    }
}

