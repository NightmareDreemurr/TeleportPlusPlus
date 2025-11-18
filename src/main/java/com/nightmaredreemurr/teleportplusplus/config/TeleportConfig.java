package com.nightmaredreemurr.teleportplusplus.config;

/**
 * Configuration for TeleportPlusPlus mod
 * 
 * This class manages configurable settings for the mod.
 * Future enhancements can add more configuration options.
 */
public class TeleportConfig {
    
    // Maximum number of homes per player
    public static int maxHomes = 5;
    
    // Teleport cooldown in seconds
    public static int teleportCooldown = 3;
    
    // Whether to show teleport messages to all players
    public static boolean broadcastTeleports = false;
    
    // Timeout for teleport requests in seconds
    public static int tpaRequestTimeout = 60;
    
    /**
     * Load configuration from file
     * TODO: Implement configuration file loading
     */
    public static void load() {
        // Configuration loading will be implemented here
        // For now, using default values
    }
    
    /**
     * Save configuration to file
     * TODO: Implement configuration file saving
     */
    public static void save() {
        // Configuration saving will be implemented here
    }
}
