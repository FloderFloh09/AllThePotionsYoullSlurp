package net.floderfloh.allthepotionsyoullslurp.item;


import net.floderfloh.allthepotionsyoullslurp.AllThePotionsYoullSlurp;
import net.floderfloh.allthepotionsyoullslurp.item.custom.MixedPotionBottleItem;
import net.floderfloh.allthepotionsyoullslurp.item.custom.ThrowableMilkItem;
import net.floderfloh.allthepotionsyoullslurp.potion.ShattlePotionItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Properties;



import static net.minecraft.world.item.Items.registerItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AllThePotionsYoullSlurp.MOD_ID);


    public static final RegistryObject<Item> SHATTLE_POTION = ITEMS.register("shattle_potion",
            () -> new ShattlePotionItem(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY))
    );
    public static final RegistryObject<Item> THROWABLE_MILK = ITEMS.register("throwable_milk",
            () -> new ThrowableMilkItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> MIXED_POTION_BOTTLE = ITEMS.register("mixed_potion_bottle",
            () -> new MixedPotionBottleItem(new Item.Properties().stacksTo(1)));









    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
