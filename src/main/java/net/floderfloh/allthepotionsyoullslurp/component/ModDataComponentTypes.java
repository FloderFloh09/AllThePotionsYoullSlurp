package net.floderfloh.allthepotionsyoullslurp.component;

import com.mojang.serialization.Codec;
import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AllThePotionsYoullSlurp.MOD_ID);



    public static final RegistryObject<DataComponentType<List<MobEffectInstance>>> STORED_POTION_EFFECTS =
            register("stored_potion_effects", builder ->
                    builder.persistent(MobEffectInstance.CODEC.listOf()) // speichert mehrere Effekte
            );



    private static <T>RegistryObject<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
