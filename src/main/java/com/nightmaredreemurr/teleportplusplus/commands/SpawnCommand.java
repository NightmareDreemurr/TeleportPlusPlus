package com.nightmaredreemurr.teleportplusplus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.nightmaredreemurr.teleportplusplus.data.TeleportData;
import com.nightmaredreemurr.teleportplusplus.data.TeleportData.TeleportLocation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Commands for spawn point management: /spawn, /setspawn
 */
public class SpawnCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /spawn - Teleport to spawn
        dispatcher.register(Commands.literal("spawn")
            .requires(source -> source.isPlayer())
            .executes(SpawnCommand::teleportSpawn));

        // /setspawn - Set spawn at current location (requires operator)
        dispatcher.register(Commands.literal("setspawn")
            .requires(source -> source.hasPermission(2))
            .executes(SpawnCommand::setSpawn));
    }

    private static int teleportSpawn(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        TeleportLocation location = TeleportData.getSpawn();

        if (location == null) {
            player.sendSystemMessage(Component.literal("§cSpawn point not set! Use /setspawn to set it."));
            return 0;
        }

        player.teleportTo(
            player.getServer().getLevel(location.dimension),
            location.x, location.y, location.z,
            location.yaw, location.pitch
        );
        player.sendSystemMessage(Component.literal("§aTeleported to spawn"));
        return 1;
    }

    private static int setSpawn(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayerOrException();
        TeleportLocation location = new TeleportLocation(
            player.getX(), player.getY(), player.getZ(),
            player.getYRot(), player.getXRot(),
            player.level().dimension()
        );

        TeleportData.setSpawn(location);
        player.sendSystemMessage(Component.literal("§aSpawn point set at current location"));
        return 1;
    }
}
