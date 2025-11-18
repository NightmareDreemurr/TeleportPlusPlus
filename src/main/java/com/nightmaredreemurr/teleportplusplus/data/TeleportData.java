package com.nightmaredreemurr.teleportplusplus.data;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages teleportation data for players including homes, warps, and spawn points
 */
public class TeleportData {
    private static final Map<UUID, Map<String, TeleportLocation>> playerHomes = new HashMap<>();
    private static final Map<String, TeleportLocation> warps = new HashMap<>();
    private static TeleportLocation spawn = null;

    public static class TeleportLocation {
        public final double x;
        public final double y;
        public final double z;
        public final float yaw;
        public final float pitch;
        public final ResourceKey<Level> dimension;

        public TeleportLocation(double x, double y, double z, float yaw, float pitch, ResourceKey<Level> dimension) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.dimension = dimension;
        }

        public BlockPos getBlockPos() {
            return new BlockPos((int) x, (int) y, (int) z);
        }
    }

    // Home management
    public static void setHome(UUID playerId, String name, TeleportLocation location) {
        playerHomes.computeIfAbsent(playerId, k -> new HashMap<>()).put(name, location);
    }

    public static TeleportLocation getHome(UUID playerId, String name) {
        Map<String, TeleportLocation> homes = playerHomes.get(playerId);
        return homes != null ? homes.get(name) : null;
    }

    public static void deleteHome(UUID playerId, String name) {
        Map<String, TeleportLocation> homes = playerHomes.get(playerId);
        if (homes != null) {
            homes.remove(name);
        }
    }

    public static Map<String, TeleportLocation> getHomes(UUID playerId) {
        return playerHomes.getOrDefault(playerId, new HashMap<>());
    }

    // Warp management
    public static void setWarp(String name, TeleportLocation location) {
        warps.put(name, location);
    }

    public static TeleportLocation getWarp(String name) {
        return warps.get(name);
    }

    public static void deleteWarp(String name) {
        warps.remove(name);
    }

    public static Map<String, TeleportLocation> getWarps() {
        return new HashMap<>(warps);
    }

    // Spawn management
    public static void setSpawn(TeleportLocation location) {
        spawn = location;
    }

    public static TeleportLocation getSpawn() {
        return spawn;
    }
}
