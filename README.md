# TeleportPlusPlus

A comprehensive teleportation mod for NeoForge with features inspired by EssentialsX.

## Features

TeleportPlusPlus provides essential teleportation commands for Minecraft servers running on NeoForge:

### Home Management
- `/home [name]` - Teleport to your home (default: "home")
- `/sethome [name]` - Set a home at your current location
- `/delhome <name>` - Delete a home
- `/homes` - List all your homes

### Spawn Management
- `/spawn` - Teleport to the server spawn point
- `/setspawn` - Set the spawn point at your current location (requires operator)

### Teleport Requests
- `/tpa <player>` - Request to teleport to another player
- `/tpaccept` - Accept a pending teleport request
- `/tpdeny` - Deny a pending teleport request

### Warp Management
- `/warp <name>` - Teleport to a server warp point
- `/setwarp <name>` - Create a warp at your current location (requires operator)
- `/delwarp <name>` - Delete a warp (requires operator)
- `/warps` - List all available warps

## Installation

1. Download the latest release from the [Releases](https://github.com/NightmareDreemurr/TeleportPlusPlus/releases) page
2. Place the JAR file in your server's `mods` folder
3. Restart your server

## Building from Source

### Requirements
- Java 21 or higher
- Gradle (wrapper included)

### Build Steps
```bash
./gradlew build
```

The compiled mod JAR will be in `build/libs/`.

## Inspiration

This mod is inspired by the functionality of [EssentialsX](https://github.com/EssentialsX/Essentials), a popular Bukkit/Spigot plugin. TeleportPlusPlus brings similar teleportation features to the NeoForge platform with a fresh implementation.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## Support

For bugs, feature requests, or questions, please [open an issue](https://github.com/NightmareDreemurr/TeleportPlusPlus/issues) on GitHub.