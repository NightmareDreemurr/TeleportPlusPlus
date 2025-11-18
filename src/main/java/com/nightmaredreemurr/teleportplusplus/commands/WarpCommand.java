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
 * Commands for managing server warps: /warp, /setwarp, /delwarp, /warps
 */
public class WarpCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /warp <name> - Teleport to a warp
        dispatcher.register(Commands.literal("warp")
            .requires(source -> source.isPlayer())
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> teleportWarp(context, StringArgumentType.getString(context, "name")))));

        // /setwarp <name> - Set a warp at current location (requires operator)
        dispatcher.register(Commands.literal("setwarp")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> setWarp(context, StringArgumentType.getString(context, "name")))));

        // /delwarp <name> - Delete a warp (requires operator)
        dispatcher.register(Commands.literal("delwarp")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(context -> deleteWarp(context, StringArgumentType.getString(context, "name")))));

        // /warps - List all warps
        dispatcher.register(Commands.literal("warps")
            .requires(source -> source.isPlayer())
            .executes(WarpCommand::listWarps));
    }

    private static int teleportWarp(CommandContext<CommandSourceStack> context, String warpName) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            TeleportLocation location = TeleportData.getWarp(warpName);

            if (location == null) {
                player.sendSystemMessage(Component.literal("§cWarp '" + warpName + "' not found!"));
                return 0;
            }

            player.teleportTo(
                player.getServer().getLevel(location.dimension),
                location.x, location.y, location.z,
                location.yaw, location.pitch
            );
            player.sendSystemMessage(Component.literal("§aTeleported to warp '" + warpName + "'"));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int setWarp(CommandContext<CommandSourceStack> context, String warpName) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            TeleportLocation location = new TeleportLocation(
                player.getX(), player.getY(), player.getZ(),
                player.getYRot(), player.getXRot(),
                player.level().dimension()
            );

            TeleportData.setWarp(warpName, location);
            player.sendSystemMessage(Component.literal("§aWarp '" + warpName + "' set at current location"));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int deleteWarp(CommandContext<CommandSourceStack> context, String warpName) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            TeleportData.deleteWarp(warpName);
            player.sendSystemMessage(Component.literal("§aWarp '" + warpName + "' deleted"));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int listWarps(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            var warps = TeleportData.getWarps();

            if (warps.isEmpty()) {
                player.sendSystemMessage(Component.literal("§cNo warps available"));
                return 0;
            }

            player.sendSystemMessage(Component.literal("§aAvailable warps: §f" + String.join(", ", warps.keySet())));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
