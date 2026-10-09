package com.herouniverselite.registry;

import com.herouniverselite.HeroUniverseLite;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, HeroUniverseLite.MODID);

    public static final RegistryObject<Item> HERO_CORE = ITEMS.register("hero_core",
        () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SOLAR_CELL = ITEMS.register("solar_cell",
        () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> VIBRANIUM_MODULE = ITEMS.register("vibranium_module",
        () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ARC_REACTOR = ITEMS.register("arc_reactor",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WEB_SHOOTER = ITEMS.register("web_shooter",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HERO_BATON = ITEMS.register("hero_baton",
        () -> new Item(new Item.Properties().stacksTo(1).durability(250)));

    private ModItems() {}
}
