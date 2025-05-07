package net.floderfloh.allthepotionsyoullslurp.potion;

import net.floderfloh.allthepotionsyoullslurp.entity.ModEntities;
import net.floderfloh.allthepotionsyoullslurp.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;

public class ShattlePotionEntity extends ThrownPotion {

    public ShattlePotionEntity(Player pEntityType, Level pLevel) {
        super(pLevel, pEntityType);
        this.setNoGravity(true);
    }


    public ShattlePotionEntity(EntityType<ShattlePotionEntity> shattlePotionEntityEntityType, Level level) {
        super(ModEntities.SHATTLE_POTION_ENTITY.get(), level);
        this.setNoGravity(true);
    }


    @Override
    protected void onHit(HitResult pResult) {
        this.triggerShatterEffect(pResult);
    }

    @Override
    public void tick() {
        super.tick();

        // Nur auf dem Server prüfen
        if (!this.level().isClientSide) {
            // Geschwindigkeit prüfen
            double velocitySq = this.getDeltaMovement().lengthSqr(); // quadratisch, schneller
            boolean tooSlow = velocitySq < (0.3F * 0.3F);

            // Entfernung zum Werfer
            Entity owner = this.getOwner();
            boolean tooFar = false;
            if (owner != null) {
                double distanceSq = this.distanceToSqr(owner);
                tooFar = distanceSq > (32 * 32);
            }

            // Wenn eine der Bedingungen erfüllt ist → auflösen
            if (tooSlow || tooFar) {
                HitResult fakeHit = new BlockHitResult(
                        this.position(),            // Einschlagpunkt (Vec3)
                        Direction.UP,              // Beliebige Richtung (z. B. UP)
                        BlockPos.containing(this.position()), // aktuelle Blockposition
                        false                      // kein tatsächlicher Blocktreffer
                );
                this.triggerShatterEffect(fakeHit);
            }
        }
    }

    public void triggerShatterEffect(HitResult pResult) {
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            PotionContents potioncontents = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (potioncontents.is(Potions.WATER)) {
                this.applyWater();
            } else if (potioncontents.hasEffects()) {
                if (this.isShattle()) {
                    this.makeShattleCloud(potioncontents.getAllEffects(), pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)pResult).getEntity() : null);
                }else {
                    super.onHit(pResult);
                }
            }
            int i = potioncontents.potion().isPresent() && potioncontents.potion().get().value().hasInstantEffects() ? 2007 : 2002;
            this.level().levelEvent(i, this.blockPosition(), potioncontents.getColor());
            this.discard();
        }
    }




    private void applyWater() {
        AABB aabb = this.getBoundingBox().inflate(4.0, 2.0, 4.0);

        for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, aabb, WATER_SENSITIVE_OR_ON_FIRE)) {
            double d0 = this.distanceToSqr(livingentity);
            if (d0 < 16.0) {
                if (livingentity.isSensitiveToWater()) {
                    livingentity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 1.0F);
                }

                if (livingentity.isOnFire() && livingentity.isAlive()) {
                    livingentity.extinguishFire();
                }
            }
        }

        for (Axolotl axolotl : this.level().getEntitiesOfClass(Axolotl.class, aabb)) {
            axolotl.rehydrate();
        }
    }

    private void makeShattleCloud(Iterable<MobEffectInstance> pEffects, @Nullable Entity pEntity) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        Entity effectSource = this.getEffectSource();
        double radius = 4.0;

        for (int i = 0; i < 100; i++) {
            double theta = this.random.nextDouble() * 2 * Math.PI;
            double phi = Math.acos(2 * this.random.nextDouble() - 1);
            double dx = Math.sin(phi) * Math.cos(theta);
            double dy = Math.sin(phi) * Math.sin(theta);
            double dz = Math.cos(phi);

            Vec3 start = this.position();
            Vec3 dir = new Vec3(dx, dy, dz);
            Vec3 end = start.add(dir.scale(radius));

            AABB beamBox = new AABB(start, end).inflate(0.5);

            for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, beamBox)) {
                // Entferne den Vergleich komplett – ist redundant:
                if (!entity.isAffectedByPotions()) continue;


                double distSq = this.distanceToSqr(entity);
                if (distSq > radius * radius) continue;

                double factor = entity == pEntity ? 1.0 : 1.0 - Math.sqrt(distSq) / radius;

                for (MobEffectInstance effect : pEffects) {
                    Holder<MobEffect> holder = effect.getEffect();
                    if (holder.value().isInstantenous()) {
                        holder.value().applyInstantenousEffect(this, this.getOwner(), entity, effect.getAmplifier(), factor);
                    } else {
                        int duration = effect.mapDuration(base -> (int) (factor * base + 0.5));
                        MobEffectInstance scaled = new MobEffectInstance(holder, duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible());
                        if (!scaled.endsWithin(20)) {
                            entity.addEffect(scaled, effectSource);
                        }
                    }
                }
            }
        }
    }




    private boolean isShattle() {
        return this.getItem().is(ModItems.SHATTLE_POTION.get());
    }



}
