package com.nightmaredreemurr.teleportplusplus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Commands for teleport requests: /tpa, /tpaccept, /tpdeny
 */
public class TpaCommand {
    private static final Map<UUID, UUID> pendingRequests = new HashMap<>(); // target -> requester

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /tpa <player> - Request to teleport to another player
        dispatcher.register(Commands.literal("tpa")
            .requires(source -> source.isPlayer())
            .then(Commands.argument("player", EntityArgument.player())
                .executes(TpaCommand::requestTeleport)));

        // /tpaccept - Accept a teleport request
        dispatcher.register(Commands.literal("tpaccept")
            .requires(source -> source.isPlayer())
            .executes(TpaCommand::acceptTeleport));

        // /tpdeny - Deny a teleport request
        dispatcher.register(Commands.literal("tpdeny")
            .requires(source -> source.isPlayer())
            .executes(TpaCommand::denyTeleport));
    }

    private static int requestTeleport(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer requester = context.getSource().getPlayerOrException();
            ServerPlayer target = EntityArgument.getPlayer(context, "player");

            if (requester.getUUID().equals(target.getUUID())) {
                requester.sendSystemMessage(Component.literal("§cYou cannot teleport to yourself!"));
                return 0;
            }

            pendingRequests.put(target.getUUID(), requester.getUUID());
            
            requester.sendSystemMessage(Component.literal("§aTeleport request sent to " + target.getName().getString()));
            target.sendSystemMessage(Component.literal("§e" + requester.getName().getString() + " wants to teleport to you. Use /tpaccept or /tpdeny"));
            
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int acceptTeleport(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer target = context.getSource().getPlayerOrException();
            UUID requesterUUID = pendingRequests.remove(target.getUUID());

            if (requesterUUID == null) {
                target.sendSystemMessage(Component.literal("§cYou have no pending teleport requests"));
                return 0;
            }

            ServerPlayer requester = target.getServer().getPlayerList().getPlayer(requesterUUID);
            if (requester == null) {
                target.sendSystemMessage(Component.literal("§cThe requester is no longer online"));
                return 0;
            }

            requester.teleportTo(
                target.serverLevel(),
                target.getX(), target.getY(), target.getZ(),
                target.getYRot(), target.getXRot()
            );

            requester.sendSystemMessage(Component.literal("§aTeleport request accepted by " + target.getName().getString()));
            target.sendSystemMessage(Component.literal("§aTeleport request accepted"));
            
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static int denyTeleport(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer target = context.getSource().getPlayerOrException();
            UUID requesterUUID = pendingRequests.remove(target.getUUID());

            if (requesterUUID == null) {
                target.sendSystemMessage(Component.literal("§cYou have no pending teleport requests"));
                return 0;
            }

            ServerPlayer requester = target.getServer().getPlayerList().getPlayer(requesterUUID);
            if (requester != null) {
                requester.sendSystemMessage(Component.literal("§cTeleport request denied by " + target.getName().getString()));
            }
            
            target.sendSystemMessage(Component.literal("§aTeleport request denied"));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
