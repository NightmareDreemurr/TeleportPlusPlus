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
 * Command for returning to the last death location: /back
 */
public class BackCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /back - Teleport to the last death location
        dispatcher.register(Commands.literal("back")
            .requires(source -> source.isPlayer())
            .executes(BackCommand::teleportBack));
    }

    private static int teleportBack(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            TeleportLocation location = TeleportData.getDeathLocation(player.getUUID());

            if (location == null) {
                player.sendSystemMessage(Component.literal("§cNo death location found!"));
                return 0;
            }

            player.teleportTo(
                player.getServer().getLevel(location.dimension),
                location.x, location.y, location.z,
                location.yaw, location.pitch
            );
            player.sendSystemMessage(Component.literal("§aTeleported to your last death location"));
            
            // Clear the death location after use
            TeleportData.clearDeathLocation(player.getUUID());
            
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
