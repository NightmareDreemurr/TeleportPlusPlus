# Changelog

All notable changes to TeleportPlusPlus will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2024-11-18

### Added
- Initial release of TeleportPlusPlus for NeoForge
- Home management system
  - `/home [name]` - Teleport to a saved home location
  - `/sethome [name]` - Set a home at current location
  - `/delhome <name>` - Delete a saved home
  - `/homes` - List all your homes
- Spawn management system
  - `/spawn` - Teleport to server spawn
  - `/setspawn` - Set spawn point (operator only)
- Teleport request system
  - `/tpa <player>` - Request to teleport to another player
  - `/tpaccept` - Accept a teleport request
  - `/tpdeny` - Deny a teleport request
- Warp management system
  - `/warp <name>` - Teleport to a server warp
  - `/setwarp <name>` - Create a warp (operator only)
  - `/delwarp <name>` - Delete a warp (operator only)
  - `/warps` - List all available warps
- Configuration system with customizable limits
- Support for multi-dimensional teleportation
- Color-coded chat messages for better user experience

### Technical Details
- Built for NeoForge 21.1.42+
- Requires Minecraft 1.21+
- Requires Java 21+
- Licensed under GPL-3.0

[Unreleased]: https://github.com/NightmareDreemurr/TeleportPlusPlus/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/NightmareDreemurr/TeleportPlusPlus/releases/tag/v1.0.0
