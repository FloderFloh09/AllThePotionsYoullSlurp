package net.floderfloh.allthepotionsyoullslurp.event;

import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.floderfloh.allthepotionsyoullslurp.effect.ModEffects;
import net.floderfloh.allthepotionsyoullslurp.item.ModItems;
import net.floderfloh.allthepotionsyoullslurp.potion.ModPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = AllThePotionsYoullSlurp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (level.isClientSide) return;

        var affection = ModEffects.AFFECTION.getHolder().get();
        if (!player.hasEffect(affection)) return;

        if (event.getTarget() instanceof TamableAnimal tamable && !tamable.isTame()) {
            tamable.tame(player);

            // ❤️-Partikeleffekt
            level.broadcastEntityEvent(tamable, (byte) 7);

            // NICHT mehr: player.removeEffect(...)
            // => Effekt bleibt erhalten
            event.setCanceled(true);
        }
        // Pferdeartige
        if (event.getTarget() instanceof AbstractHorse horse && !horse.isTamed()) {
            horse.setTamed(true);
            horse.setOwnerUUID(player.getUUID());
            level.broadcastEntityEvent(horse, (byte) 7);
            event.setCanceled(true);
            return;
        }

        // Lamas
        if (event.getTarget() instanceof Llama llama && !llama.isTamed()) {
            llama.setTamed(true);
            llama.setOwnerUUID(player.getUUID());
            level.broadcastEntityEvent(llama, (byte) 7);
            event.setCanceled(true);
            return;
        }

        // Kamele (1.20+)
        if (event.getTarget().getType().toString().toLowerCase().contains("camel") && event.getTarget() instanceof AbstractHorse camel && !camel.isTamed()) {
            camel.setTamed(true);
            camel.setOwnerUUID(player.getUUID());
            level.broadcastEntityEvent(camel, (byte) 7);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.CLOCK, ModPotions.PERSISTENCE_POTION.getHolder().get());
        builder.addMix(ModPotions.PERSISTENCE_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_PERSISTENCE_POTION.getHolder().get());
        builder.addMix(ModPotions.PERSISTENCE_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_PERSISTENCE_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.GOLDEN_APPLE, ModPotions.AFFECTION_POTION.getHolder().get());
        builder.addMix(ModPotions.AFFECTION_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_AFFECTION_POTION.getHolder().get());
        builder.addMix(ModPotions.AFFECTION_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_AFFECTION_POTION.getHolder().get());
        builder.addMix(Potions.TURTLE_MASTER, Items.GLASS_PANE, ModPotions.WEIGHT_POTION.getHolder().get());
        builder.addMix(ModPotions.WEIGHT_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_WEIGHT_POTION.getHolder().get());
        builder.addMix(ModPotions.WEIGHT_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_WEIGHT_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.OMINOUS_BOTTLE, ModPotions.DOGGEDNESS_POTION.getHolder().get());
        builder.addMix(ModPotions.DOGGEDNESS_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_DOGGEDNESS_POTION.getHolder().get());
        builder.addMix(ModPotions.DOGGEDNESS_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_DOGGEDNESS_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.HEART_OF_THE_SEA, ModPotions.REINFORCEMENT_POTION.getHolder().get());
        builder.addMix(ModPotions.REINFORCEMENT_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_REINFORCEMENT_POTION.getHolder().get());
        builder.addMix(ModPotions.REINFORCEMENT_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_REINFORCEMENT_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.ECHO_SHARD, ModPotions.VULNERABILITY_POTION.getHolder().get());
        builder.addMix(ModPotions.VULNERABILITY_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_VULNERABILITY_POTION.getHolder().get());
        builder.addMix(ModPotions.VULNERABILITY_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_VULNERABILITY_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.FLINT, ModPotions.FRAGILITY_POTION.getHolder().get());
        builder.addMix(ModPotions.FRAGILITY_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_FRAGILITY_POTION.getHolder().get());
        builder.addMix(ModPotions.FRAGILITY_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_FRAGILITY_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.HONEY_BOTTLE, ModPotions.TRANSFER_POTION.getHolder().get());
        builder.addMix(ModPotions.TRANSFER_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_TRANSFER_POTION.getHolder().get());
        builder.addMix(ModPotions.TRANSFER_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_TRANSFER_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.GLOW_INK_SAC, ModPotions.COUNTERATTACK_POTION.getHolder().get());
        builder.addMix(ModPotions.COUNTERATTACK_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_COUNTERATTACK_POTION.getHolder().get());
        builder.addMix(ModPotions.COUNTERATTACK_POTION.getHolder().get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_COUNTERATTACK_POTION.getHolder().get());
        builder.addMix(Potions.STRONG_STRENGTH, Items.REDSTONE, ModPotions.ULTRA_STRENGTH_POTION.getHolder().get());
        builder.addContainer(ModItems.SHATTLE_POTION.get());
        builder.addContainerRecipe(Items.POTION, Items.FIREWORK_STAR, ModItems.SHATTLE_POTION.get());

    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> weightEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.WEIGHT.get());
        if (entity.hasEffect(weightEffect)) {
            int amplifier = entity.getEffect(weightEffect).getAmplifier() + 1;

            Vec3 motion = entity.getDeltaMovement();

            // Erhöhe Schwerkraft
            if (!entity.onGround() && motion.y < 0) {
                double extraGravity = 0.05 * amplifier;
                double maxFallSpeed = -0.5 - (0.1 * amplifier);

                if (motion.y > maxFallSpeed) {
                    entity.addDeltaMovement(new Vec3(0, -extraGravity, 0));
                }
            }
        }

        boolean hasReinforcement = entity.hasEffect(ModEffects.REINFORCEMENT.getHolder().get());

        List<MobEffectInstance> toRemove = new ArrayList<>();
        List<MobEffectInstance> toAdd = new ArrayList<>();

        for (MobEffectInstance effect : entity.getActiveEffects()) {
            // Nicht den Verstärkungs-Effekt selbst anfassen
            if (effect.getEffect().equals(ModEffects.REINFORCEMENT.getHolder().get())) continue;

            // Verstärkte (temporäre) Effekte erkennen (z.B. per showIcon=false)
            boolean isTempBoosted = !effect.showIcon();

            if (hasReinforcement) {
                int boost = entity.getEffect(ModEffects.REINFORCEMENT.getHolder().get()).getAmplifier() + 1;

                if (!isTempBoosted) {
                    // Nur normale Effekte verstärken
                    if (effect.getAmplifier() + boost <= 127) {
                        toRemove.add(effect);

                        MobEffectInstance boosted = new MobEffectInstance(
                                effect.getEffect(),
                                effect.getDuration(),
                                effect.getAmplifier() + boost,
                                effect.isAmbient(),
                                effect.isVisible(),
                                false // showIcon=false als Marker
                        );
                        toAdd.add(boosted);
                    }
                }
            } else {
                // Reinforcement ist weg → entferne temporär verstärkte Effekte
                if (isTempBoosted) {
                    toRemove.add(effect);

                    MobEffectInstance original = new MobEffectInstance(
                            effect.getEffect(),
                            effect.getDuration(),
                            effect.getAmplifier() - 1, // Rückgängig machen (sofern 1 Boost vorher)
                            effect.isAmbient(),
                            effect.isVisible(),
                            true // showIcon wieder anzeigen
                    );
                    toAdd.add(original);
                }
            }
        }

        // Jetzt anwenden
        for (MobEffectInstance effect : toRemove) {
            entity.removeEffect(effect.getEffect());
        }
        for (MobEffectInstance effect : toAdd) {
            entity.addEffect(effect);
        }

    }
    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> weightEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.WEIGHT.get());
        if (entity.hasEffect(weightEffect)) {
            int amplifier = entity.getEffect(weightEffect).getAmplifier() + 1;

            Vec3 motion = entity.getDeltaMovement();
            // Reduziere Y-Geschwindigkeit beim Sprung
            double jumpModifier = 1.0 - (0.2 * amplifier); // z. B. 75%, 50%, etc.
            event.getEntity().setDeltaMovement(motion.x, motion.y * jumpModifier, motion.z);
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> weightEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.WEIGHT.get());
        if (entity.hasEffect(weightEffect)) {
            int amplifier = entity.getEffect(weightEffect).getAmplifier() + 1;
            event.setDistance(event.getDistance() + 1.5f * amplifier); // z. B. +2.5 Blöcke
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.FRAGILITY.getHolder().get())) {
            int amplifier = entity.getEffect(ModEffects.FRAGILITY.getHolder().get()).getAmplifier() + 1;

            // Verstärkter Haltbarkeitsverlust für jede Rüstung
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    ItemStack armorPiece = entity.getItemBySlot(slot);
                    if (armorPiece.isDamageableItem()) {
                        armorPiece.hurtAndBreak(amplifier, entity, slot );
                    }
                }
            }
        }


    }

    @SubscribeEvent
    public static void onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        applyCorrosionToHeldItem(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerAttack(PlayerInteractEvent.LeftClickBlock event) {
        applyCorrosionToHeldItem(event.getEntity());
    }

    private static void applyCorrosionToHeldItem(Player player) {
        if (player.hasEffect(ModEffects.FRAGILITY.getHolder().get())) {
            int amplifier = player.getEffect(ModEffects.FRAGILITY.getHolder().get()).getAmplifier() + 1;

            ItemStack main = player.getMainHandItem();
            if (main.isDamageableItem()) {
                main.hurtAndBreak(amplifier, player, EquipmentSlot.MAINHAND);

            }

            ItemStack off = player.getOffhandItem();
            if (off.isDamageableItem()) {
                off.hurtAndBreak(amplifier, player, EquipmentSlot.OFFHAND);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (player.level().isClientSide || event.phase != TickEvent.Phase.END) return;

        if (player.tickCount % 40 != 0) return;

        if (player.hasEffect(ModEffects.TRANSFER.getHolder().get()) && player.getHealth() >= player.getMaxHealth()) {
            int amplifier = player.getEffect(ModEffects.TRANSFER.getHolder().get()).getAmplifier() * (-1) - 1;

            FoodData foodData = player.getFoodData();

            // Nur wenn der Spieler genug Sättigung hat
            if (foodData.getFoodLevel() > 6 || (foodData.getFoodLevel() == 6 && foodData.getSaturationLevel() > 0)) {
                boolean repaired = false;

                // Repariere Rüstung
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                        ItemStack armor = player.getItemBySlot(slot);
                        if (armor.isDamaged()) {
                            armor.hurtAndBreak(amplifier, player, slot );
                            repaired = true;
                            break;
                        }
                    }
                }

                // Repariere Tool in Mainhand oder Offhand, wenn nötig
                if (!repaired) {
                    for (ItemStack stack : List.of(player.getMainHandItem(), player.getOffhandItem())) {
                        if (stack.isDamaged()) {
                            stack.hurtAndBreak(amplifier, player, EquipmentSlot.MAINHAND );
                            repaired = true;
                            break;
                        }
                    }
                }

                // Reduziere Sättigung (und ggf. Nahrung)
                if (repaired) {
                    float satDrain = (0.5f * amplifier) * (-1);
                    float foodDrain = (0.1f * amplifier) * (-1);

                    foodData.setSaturation(Math.max(0, foodData.getSaturationLevel() - satDrain));
                    if (foodData.getSaturationLevel() <= 0) {
                        foodData.setFoodLevel(Math.max(0, foodData.getFoodLevel() - (int) Math.ceil(foodDrain)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.VULNERABILITY.getHolder().get())) {
            int amplifier = entity.getEffect(ModEffects.VULNERABILITY.getHolder().get()).getAmplifier() + 1;

            float originalDamage = event.getAmount();
            float modifiedDamage = originalDamage * amplifier;

            event.setAmount(modifiedDamage);
        }

        // Angreifer macht mehr Schaden bei COUNTERATTACK
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(ModEffects.COUNTERATTACK.getHolder().get())) {
                int amplifier = player.getEffect(ModEffects.COUNTERATTACK.getHolder().get()).getAmplifier() + 1;

                float currentHealth = player.getHealth();
                float maxHealth = player.getMaxHealth();
                float healthRatio = (maxHealth - currentHealth) / maxHealth;

                float damageMultiplier = 1.0F + healthRatio * amplifier;

                event.setAmount(event.getAmount() * damageMultiplier);
            }
        }
    }

















}
