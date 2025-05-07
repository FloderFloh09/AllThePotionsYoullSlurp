package net.floderfloh.allthepotionsyoullslurp.effect;

import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AllThePotionsYoullSlurp.MOD_ID);

    public static final RegistryObject<MobEffect> PERSISTENCE_EFFECT = MOB_EFFECTS.register("persistence",
            () -> new PersistenceEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));

    public static final RegistryObject<MobEffect> AFFECTION = MOB_EFFECTS.register("affection",
            () -> new AffectionEffect(MobEffectCategory.BENEFICIAL, 0xFF69B4));

    public static final RegistryObject<MobEffect> WEIGHT = MOB_EFFECTS.register("weight",
            () -> new AffectionEffect(MobEffectCategory.HARMFUL, 0x4F6994).addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(AllThePotionsYoullSlurp.MOD_ID, "weight"),
                    -0.12F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final RegistryObject<MobEffect> DOGGEDNESS = MOB_EFFECTS.register("doggedness",
            () -> new DoggednessEffect(MobEffectCategory.HARMFUL, 0x4F6994));

    public static final RegistryObject<MobEffect> REINFORCEMENT = MOB_EFFECTS.register("reinforcement",
            () -> new ReinforcementEffect(MobEffectCategory.BENEFICIAL, 0x15a9b4));

    public static final RegistryObject<MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability",
            () -> new ReinforcementEffect(MobEffectCategory.HARMFUL, 0x85a9bb));

    public static final RegistryObject<MobEffect> FRAGILITY = MOB_EFFECTS.register("fragility",
            () -> new ReinforcementEffect(MobEffectCategory.HARMFUL, 0xa5aa2b));

    public static final RegistryObject<MobEffect> TRANSFER = MOB_EFFECTS.register("transfer",
            () -> new ReinforcementEffect(MobEffectCategory.BENEFICIAL, 0xa5aa2b));

    public static final RegistryObject<MobEffect> COUNTERATTACK = MOB_EFFECTS.register("counterattack",
            () -> new ReinforcementEffect(MobEffectCategory.BENEFICIAL, 0xa5aa2b));

    public static final RegistryObject<MobEffect> OVERLOAD_EFFECT = MOB_EFFECTS.register("overload_effect",
            () -> new ReinforcementEffect(MobEffectCategory.HARMFUL, 0x66aa2b));




    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
