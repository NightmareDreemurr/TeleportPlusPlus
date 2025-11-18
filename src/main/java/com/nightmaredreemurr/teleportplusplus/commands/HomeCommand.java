package com.nightmaredreemurr.teleportplusplus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.nightmaredreemurr.teleportplusplus.data.TeleportData;
import com.nightmaredreemurr.teleportplusplus.data.TeleportData.TeleportLocation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Commands for managing player homes: /home, /sethome, /delhome, /homes
 */
public class HomeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /home [name] - Teleport to a home
        dispatcher.register(Commands.literal("home")
            .requires(source -> source.isPlayer())
            .executes(context -> teleportHome(context, "home"))
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> teleportHome(context, StringArgumentType.getString(context, "name")))));

        // /sethome [name] - Set a home at current location
        dispatcher.register(Commands.literal("sethome")
            .requires(source -> source.isPlayer())
            .executes(context -> setHome(context, "home"))
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> setHome(context, StringArgumentType.getString(context, "name")))));

        // /delhome <name> - Delete a home
        dispatcher.register(Commands.literal("delhome")
            .requires(source -> source.isPlayer())
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> deleteHome(context, StringArgumentType.getString(context, "name")))));

        // /homes - List all homes
        dispatcher.register(Commands.literal("homes")
            .requires(source -> source.isPlayer())
            .executes(HomeCommand::listHomes));
    }

    private static int teleportHome(CommandContext<CommandSourceStack> context, String homeName) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        TeleportLocation location = TeleportData.getHome(player.getUUID(), homeName);

        if (location == null) {
            player.sendSystemMessage(Component.literal("§cHome '" + homeName + "' not found!"));
            return 0;
        }

        player.teleportTo(
            player.getServer().getLevel(location.dimension),
            location.x, location.y, location.z,
            location.yaw, location.pitch
        );
        player.sendSystemMessage(Component.literal("§aTeleported to home '" + homeName + "'"));
        return 1;
    }

    private static int setHome(CommandContext<CommandSourceStack> context, String homeName) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        TeleportLocation location = new TeleportLocation(
            player.getX(), player.getY(), player.getZ(),
            player.getYRot(), player.getXRot(),
            player.level().dimension()
        );

        boolean success = TeleportData.setHome(player.getUUID(), homeName, location);
        if (success) {
            player.sendSystemMessage(Component.literal("§aHome '" + homeName + "' set at current location"));
            return 1;
        } else {
            player.sendSystemMessage(Component.literal("§cYou have reached the maximum number of homes!"));
            return 0;
        }
    }

    private static int deleteHome(CommandContext<CommandSourceStack> context, String homeName) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        TeleportData.deleteHome(player.getUUID(), homeName);
        player.sendSystemMessage(Component.literal("§aHome '" + homeName + "' deleted"));
        return 1;
    }

    private static int listHomes(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        var homes = TeleportData.getHomes(player.getUUID());

        if (homes.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cYou have no homes set"));
            return 0;
        }

        player.sendSystemMessage(Component.literal("§aYour homes: §f" + String.join(", ", homes.keySet())));
        return 1;
    }
}
