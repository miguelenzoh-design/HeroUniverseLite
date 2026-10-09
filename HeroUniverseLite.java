package com.herouniverselite;

import com.herouniverselite.registry.ModItems;
import com.herouniverselite.command.HeroCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;

@Mod(HeroUniverseLite.MODID)
public class HeroUniverseLite {
    public static final String MODID = "herouniverselite";

    public HeroUniverseLite() {
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        HeroCommands.register(event.getDispatcher());
    }
}
