package com.herouniverselite.command;

import com.herouniverselite.registry.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class HeroCommands {
    private static final String SOLAR = "hul_solar";
    private static final String MODE = "hul_strength_mode";
    private static final String COOLDOWN = "hul_cooldown";

    public static void register(CommandDispatcher<CommandSourceStack> d) {
        d.register(Commands.literal("hero")
            .then(Commands.literal("help").executes(c -> help(c.getSource())))
            .then(Commands.literal("give")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("core").executes(c -> give(c.getSource(), ModItems.HERO_CORE.get(), 1)))
                .then(Commands.literal("solar_cell").executes(c -> give(c.getSource(), ModItems.SOLAR_CELL.get(), 1)))
                .then(Commands.literal("vibranium").executes(c -> give(c.getSource(), ModItems.VIBRANIUM_MODULE.get(), 1)))
                .then(Commands.literal("arc_reactor").executes(c -> give(c.getSource(), ModItems.ARC_REACTOR.get(), 1)))
                .then(Commands.literal("web_shooter").executes(c -> give(c.getSource(), ModItems.WEB_SHOOTER.get(), 1)))
                .then(Commands.literal("baton").executes(c -> give(c.getSource(), ModItems.HERO_BATON.get(), 1))))
            .then(Commands.literal("superman").executes(c -> superman(c.getSource())))
            .then(Commands.literal("spiderman").executes(c -> spider(c.getSource())))
            .then(Commands.literal("panther").executes(c -> panther(c.getSource())))
            .then(Commands.literal("ironman").executes(c -> ironman(c.getSource())))
            .then(Commands.literal("flash").executes(c -> flash(c.getSource())))
            .then(Commands.literal("thor").executes(c -> thor(c.getSource())))
            .then(Commands.literal("full_power").executes(c -> strengthMode(c.getSource(), true)))
            .then(Commands.literal("controlled").executes(c -> strengthMode(c.getSource(), false)))
            .then(Commands.literal("cooldown_clear").executes(c -> clearCooldown(c.getSource())))
            .then(Commands.literal("solar")
                .then(Commands.literal("status").executes(c -> solarStatus(c.getSource())))
                .then(Commands.literal("absorb").executes(c -> solarAbsorb(c.getSource())))
                .then(Commands.literal("add").then(Commands.argument("amount", IntegerArgumentType.integer(1, 100))
                    .requires(s -> s.hasPermission(2))
                    .executes(c -> solarAdd(c.getSource(), IntegerArgumentType.getInteger(c, "amount"))))))
        );
    }

    private static int help(CommandSourceStack s) {
        s.sendSuccess(() -> Component.literal("Hero Universe Lite: /hero superman, spiderman, panther, ironman, flash, thor, full_power, controlled, cooldown_clear, solar status|absorb"), false);
        s.sendSuccess(() -> Component.literal("Admin: /hero give <core|solar_cell|vibranium|arc_reactor|web_shooter|baton> and /hero solar add <1-100>"), false);
        return 1;
    }
    private static ServerPlayer player(CommandSourceStack s) throws Exception { return s.getPlayerOrException(); }
    private static int give(CommandSourceStack s, net.minecraft.world.item.Item item, int count) throws Exception {
        ServerPlayer p = player(s); ItemStack stack = new ItemStack(item, count);
        if (!p.getInventory().add(stack)) p.drop(stack, false);
        s.sendSuccess(() -> Component.literal("Item added."), false); return 1;
    }
    private static int superman(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        int solar = p.getPersistentData().getInt(SOLAR);
        int amp = solar >= 100 ? 2 : solar >= 60 ? 1 : 0;
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, amp));
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, amp));
        p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, amp));
        p.addEffect(new MobEffectInstance(MobEffects.JUMP, 20 * 60, amp));
        p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 30, amp));
        p.getPersistentData().putInt(COOLDOWN, 20 * 10);
        s.sendSuccess(() -> Component.literal("Solar power activated (" + solar + "%). Use /hero solar absorb to charge, /hero full_power or /hero controlled."), false);
        return 1;
    }
    private static int spider(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 2));
        p.addEffect(new MobEffectInstance(MobEffects.JUMP, 20 * 60, 2));
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 0));
        p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 60, 0));
        p.getPersistentData().putInt(COOLDOWN, 20 * 8);
        s.sendSuccess(() -> Component.literal("Spider abilities activated for 60 seconds."), false); return 1;
    }
    private static int panther(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 2));
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 1));
        p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 1));
        p.getPersistentData().putInt("hul_kinetic_charge", 0);
        s.sendSuccess(() -> Component.literal("Panther mode active. Kinetic absorption is a prototype; charge UI is planned."), false); return 1;
    }
    private static int ironman(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 1));
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 1));
        p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 60, 0));
        p.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 60, 0));
        s.sendSuccess(() -> Component.literal("Powered armor simulation active."), false); return 1;
    }
    private static int flash(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 45, 4));
        p.addEffect(new MobEffectInstance(MobEffects.JUMP, 20 * 45, 2));
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 45, 1));
        s.sendSuccess(() -> Component.literal("Speed mode active for 45 seconds."), false); return 1;
    }
    private static int thor(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 2));
        p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 2));
        if (p.level() instanceof ServerLevel level) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
            if (bolt != null) {
                bolt.moveTo(p.getX(), p.getY(), p.getZ());
                level.addFreshEntity(bolt);
            }
        }
        s.sendSuccess(() -> Component.literal("Thunder mode active; a lightning bolt was summoned."), false); return 1;
    }
    private static int strengthMode(CommandSourceStack s, boolean full) throws Exception {
        ServerPlayer p = player(s); p.getPersistentData().putBoolean(MODE, full);
        if (full) {
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 3));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 2));
            s.sendSuccess(() -> Component.literal("Full power enabled. Warning: combat damage is greatly increased."), false);
        } else {
            p.removeEffect(MobEffects.DAMAGE_BOOST);
            p.removeEffect(MobEffects.DAMAGE_RESISTANCE);
            s.sendSuccess(() -> Component.literal("Controlled strength enabled; extra strength/resistance effects removed."), false);
        }
        return 1;
    }
    private static int clearCooldown(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s); p.getPersistentData().putInt(COOLDOWN, 0);
        s.sendSuccess(() -> Component.literal("Hero Universe Lite cooldown data cleared."), false); return 1;
    }
    private static int solarStatus(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s); int value = p.getPersistentData().getInt(SOLAR);
        s.sendSuccess(() -> Component.literal("Solar charge: " + value + "% / 100%."), false); return 1;
    }
    private static int solarAbsorb(CommandSourceStack s) throws Exception {
        ServerPlayer p = player(s);
        int current = p.getPersistentData().getInt(SOLAR);
        int add = (p.level().isDay() && p.level().canSeeSky(p.blockPosition())) ? 25 : 10;
        int next = Math.min(100, current + add);
        p.getPersistentData().putInt(SOLAR, next);
        s.sendSuccess(() -> Component.literal("Solar charge +" + (next-current) + "%: " + next + "% / 100%."), false);
        return 1;
    }
    private static int solarAdd(CommandSourceStack s, int amount) throws Exception {
        ServerPlayer p = player(s);
        int next = Math.min(100, p.getPersistentData().getInt(SOLAR) + amount);
        p.getPersistentData().putInt(SOLAR, next);
        s.sendSuccess(() -> Component.literal("Solar charge set to " + next + "%."), false); return 1;
    }
    private HeroCommands() {}
}
