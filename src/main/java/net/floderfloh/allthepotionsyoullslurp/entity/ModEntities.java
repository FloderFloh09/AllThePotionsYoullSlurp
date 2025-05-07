package net.floderfloh.allthepotionsyoullslurp.entity;

import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.floderfloh.allthepotionsyoullslurp.entity.custom.ThrowableMilkEntity;
import net.floderfloh.allthepotionsyoullslurp.potion.ShattlePotionEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AllThePotionsYoullSlurp.MOD_ID);


    public static final RegistryObject<EntityType<ShattlePotionEntity>> SHATTLE_POTION_ENTITY =
            ENTITY_TYPES.register("shattle_potion", () ->
                    EntityType.Builder.<ShattlePotionEntity>of(ShattlePotionEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(10)
                            .updateInterval(20)
                            .build("shattle_potion"));
    public static final RegistryObject<EntityType<ThrowableMilkEntity>> THROWABLE_MILK =
            ENTITY_TYPES.register("throwable_milk", () -> EntityType.Builder.<ThrowableMilkEntity>of(
                            ThrowableMilkEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("throwable_milk"));











    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
