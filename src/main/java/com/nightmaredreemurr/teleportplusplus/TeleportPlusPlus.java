package com.nightmaredreemurr.teleportplusplus;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nightmaredreemurr.teleportplusplus.commands.*;
import com.nightmaredreemurr.teleportplusplus.data.TeleportData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * TeleportPlusPlus - A NeoForge teleportation mod
 * 
 * This mod provides comprehensive teleportation features inspired by EssentialsX,
 * including home management, spawn points, teleport requests, and warps.
 */
@Mod(TeleportPlusPlus.MOD_ID)
public class TeleportPlusPlus {
    public static final String MOD_ID = "teleportplusplus";
    public static final Logger LOGGER = LogManager.getLogger();

    public TeleportPlusPlus(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(this::onPlayerDeath);
        
        LOGGER.info("TeleportPlusPlus initializing...");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("TeleportPlusPlus common setup complete");
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        LOGGER.info("Registering TeleportPlusPlus commands");
        HomeCommand.register(event.getDispatcher());
        SpawnCommand.register(event.getDispatcher());
        TpaCommand.register(event.getDispatcher());
        WarpCommand.register(event.getDispatcher());
        BackCommand.register(event.getDispatcher());
    }

    private void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Save the player's death location
            TeleportData.TeleportLocation deathLocation = new TeleportData.TeleportLocation(
                player.getX(), player.getY(), player.getZ(),
                player.getYRot(), player.getXRot(),
                player.level().dimension()
            );
            TeleportData.setDeathLocation(player.getUUID(), deathLocation);
            LOGGER.debug("Saved death location for player: {}", player.getName().getString());
        }
    }
}
