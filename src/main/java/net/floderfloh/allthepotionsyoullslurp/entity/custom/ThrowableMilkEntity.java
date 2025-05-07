package net.floderfloh.allthepotionsyoullslurp.entity.custom;

import net.floderfloh.allthepotionsyoullslurp.entity.ModEntities;
import net.floderfloh.allthepotionsyoullslurp.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class ThrowableMilkEntity extends ThrowableItemProjectile {

    public ThrowableMilkEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.THROWABLE_MILK.get(), pShooter, pLevel);
    }

    public ThrowableMilkEntity(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.THROWABLE_MILK.get(), pX, pY, pZ, pLevel);
    }

    public ThrowableMilkEntity(EntityType<ThrowableMilkEntity> throwableMilkEntityEntityType, Level level) {
        super(throwableMilkEntityEntityType, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THROWABLE_MILK.get();
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            // 1. Splash-Partikel triggern (wird über EntityEvent ausgelöst)
            this.level().broadcastEntityEvent(this, (byte)3);

            // 2. Zerbrech-Sound wie bei Tränken
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);

            // 3. Effekte von Entities im Umkreis entfernen (wie Milch)
            AABB area = new AABB(this.blockPosition()).inflate(4.0D, 2.0D, 4.0D); // Splash-Potion-Radius
            List<LivingEntity> affectedEntities = this.level().getEntitiesOfClass(LivingEntity.class, area);
            for (LivingEntity entity : affectedEntities) {
                entity.removeAllEffects();
            }

            this.discard(); // Entity entfernen
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            int color = 0xFFFFFF; // Weiß wie Milch

            for (int i = 0; i < 40; ++i) {
                double angle = this.random.nextDouble() * 2 * Math.PI;
                double speed = this.random.nextDouble() * 0.5 + 0.2; // Geschwindigkeit seitlich
                double dx = Math.cos(angle) * speed;
                double dz = Math.sin(angle) * speed;
                double dy = this.random.nextGaussian() * 0.05; // wenig vertikale Streuung

                this.level().addAlwaysVisibleParticle(
                        ParticleTypes.EFFECT,
                        this.getX(), this.getY(), this.getZ(),
                        dx, dy, dz
                );
            }
        }
    }



    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        int damage = entity instanceof Blaze ? 3 : 0;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) damage);
    }
}


