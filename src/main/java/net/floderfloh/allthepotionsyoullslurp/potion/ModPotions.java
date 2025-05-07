package net.floderfloh.allthepotionsyoullslurp.potion;


import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.floderfloh.allthepotionsyoullslurp.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, AllThePotionsYoullSlurp.MOD_ID);

    public static final RegistryObject<Potion> PERSISTENCE_POTION = POTIONS.register("persistence",
            () -> new Potion(new MobEffectInstance(ModEffects.PERSISTENCE_EFFECT.getHolder().get(), 2400, 0)));

    public static final RegistryObject<Potion> LONG_PERSISTENCE_POTION = POTIONS.register("long_persistence",
            () -> new Potion("persistence",new MobEffectInstance(ModEffects.PERSISTENCE_EFFECT.getHolder().get(), 4800, 0)));

    public static final RegistryObject<Potion> STRONG_PERSISTENCE_POTION = POTIONS.register("strong_persistence",
            () -> new Potion("persistence",new MobEffectInstance(ModEffects.PERSISTENCE_EFFECT.getHolder().get(), 1200, 1)));

    public static final RegistryObject<Potion> AFFECTION_POTION = POTIONS.register("affection",
            () -> new Potion(new MobEffectInstance(ModEffects.AFFECTION.getHolder().get(), 3600, 0)));

    public static final RegistryObject<Potion> LONG_AFFECTION_POTION = POTIONS.register("long_affection",
            () -> new Potion("affection",new MobEffectInstance(ModEffects.AFFECTION.getHolder().get(), 12000, 0)));

    public static final RegistryObject<Potion> STRONG_AFFECTION_POTION = POTIONS.register("strong_affection",
            () -> new Potion("affection",new MobEffectInstance(ModEffects.AFFECTION.getHolder().get(), 2400, 2)));

    public static final RegistryObject<Potion> WEIGHT_POTION = POTIONS.register("weight",
            () -> new Potion(new MobEffectInstance(ModEffects.WEIGHT.getHolder().get(), 900, 0)));

    public static final RegistryObject<Potion> LONG_WEIGHT_POTION = POTIONS.register("long_weight",
            () -> new Potion("weight",new MobEffectInstance(ModEffects.WEIGHT.getHolder().get(), 1600, 0)));

    public static final RegistryObject<Potion> STRONG_WEIGHT_POTION = POTIONS.register("strong_weight",
            () -> new Potion("weight",new MobEffectInstance(ModEffects.WEIGHT.getHolder().get(), 600, 1)));

    public static final RegistryObject<Potion> DOGGEDNESS_POTION = POTIONS.register("doggedness",
            () -> new Potion(new MobEffectInstance(ModEffects.DOGGEDNESS.getHolder().get(), 1600, 0)));

    public static final RegistryObject<Potion> LONG_DOGGEDNESS_POTION = POTIONS.register("long_doggedness",
            () -> new Potion("doggedness",new MobEffectInstance(ModEffects.DOGGEDNESS.getHolder().get(), 3000, 0)));

    public static final RegistryObject<Potion> STRONG_DOGGEDNESS_POTION = POTIONS.register("strong_doggedness",
            () -> new Potion("doggedness",new MobEffectInstance(ModEffects.DOGGEDNESS.getHolder().get(), 1200, 1)));

    public static final RegistryObject<Potion> REINFORCEMENT_POTION = POTIONS.register("reinforcement",
            () -> new Potion(new MobEffectInstance(ModEffects.REINFORCEMENT.getHolder().get(), 1800, 0)));

    public static final RegistryObject<Potion> LONG_REINFORCEMENT_POTION = POTIONS.register("long_reinforcement",
            () -> new Potion("reinforcement",new MobEffectInstance(ModEffects.REINFORCEMENT.getHolder().get(), 3600, 0)));

    public static final RegistryObject<Potion> STRONG_REINFORCEMENT_POTION = POTIONS.register("strong_reinforcement",
            () -> new Potion("reinforcement",new MobEffectInstance(ModEffects.REINFORCEMENT.getHolder().get(), 1000, 1)));

    public static final RegistryObject<Potion> VULNERABILITY_POTION = POTIONS.register("vulnerability",
            () -> new Potion(new MobEffectInstance(ModEffects.VULNERABILITY.getHolder().get(), 500, 1)));

    public static final RegistryObject<Potion> LONG_VULNERABILITY_POTION = POTIONS.register("long_vulnerability",
            () -> new Potion("vulnerability",new MobEffectInstance(ModEffects.VULNERABILITY.getHolder().get(), 900, 1)));

    public static final RegistryObject<Potion> STRONG_VULNERABILITY_POTION = POTIONS.register("strong_vulnerability",
            () -> new Potion("vulnerability",new MobEffectInstance(ModEffects.VULNERABILITY.getHolder().get(), 300, 2)));

    public static final RegistryObject<Potion> FRAGILITY_POTION = POTIONS.register("fragility",
            () -> new Potion(new MobEffectInstance(ModEffects.FRAGILITY.getHolder().get(), 2400, 0)));

    public static final RegistryObject<Potion> LONG_FRAGILITY_POTION = POTIONS.register("long_fragility",
            () -> new Potion("fragility",new MobEffectInstance(ModEffects.FRAGILITY.getHolder().get(), 4800, 0)));

    public static final RegistryObject<Potion> STRONG_FRAGILITY_POTION = POTIONS.register("strong_fragility",
            () -> new Potion("fragility",new MobEffectInstance(ModEffects.FRAGILITY.getHolder().get(), 1800, 1)));

    public static final RegistryObject<Potion> TRANSFER_POTION = POTIONS.register("transfer",
            () -> new Potion(new MobEffectInstance(ModEffects.TRANSFER.getHolder().get(), 9600, 0)));

    public static final RegistryObject<Potion> LONG_TRANSFER_POTION = POTIONS.register("long_transfer",
            () -> new Potion("transfer",new MobEffectInstance(ModEffects.TRANSFER.getHolder().get(), 19200, 0)));

    public static final RegistryObject<Potion> STRONG_TRANSFER_POTION = POTIONS.register("strong_transfer",
            () -> new Potion("transfer",new MobEffectInstance(ModEffects.TRANSFER.getHolder().get(), 4000, 2)));

    public static final RegistryObject<Potion> COUNTERATTACK_POTION = POTIONS.register("counterattack",
            () -> new Potion(new MobEffectInstance(ModEffects.COUNTERATTACK.getHolder().get(), 2400, 0)));

    public static final RegistryObject<Potion> LONG_COUNTERATTACK_POTION = POTIONS.register("long_counterattack",
            () -> new Potion("counterattack",new MobEffectInstance(ModEffects.COUNTERATTACK.getHolder().get(), 4800, 0)));

    public static final RegistryObject<Potion> STRONG_COUNTERATTACK_POTION = POTIONS.register("strong_counterattack",
            () -> new Potion("counterattack",new MobEffectInstance(ModEffects.COUNTERATTACK.getHolder().get(), 1200, 1)));


    public static final RegistryObject<Potion> ULTRA_STRENGTH_POTION = POTIONS.register("ultra_strength",
            () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 2)));





    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }


}
