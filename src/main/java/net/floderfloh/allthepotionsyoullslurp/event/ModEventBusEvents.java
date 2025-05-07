package net.floderfloh.allthepotionsyoullslurp.event;

import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AllThePotionsYoullSlurp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

    }
}
