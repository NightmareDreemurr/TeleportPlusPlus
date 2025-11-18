# Contributing to TeleportPlusPlus

Thank you for your interest in contributing to TeleportPlusPlus! We welcome contributions from the community.

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle 8.8+ (wrapper included)
- Git

### Building the Project

1. Clone the repository:
```bash
git clone https://github.com/NightmareDreemurr/TeleportPlusPlus.git
cd TeleportPlusPlus
```

2. Build the mod:
```bash
./gradlew build
```

3. The compiled JAR will be in `build/libs/`

### Development Setup

For development, you can run the mod in a development environment:

```bash
./gradlew runClient  # For client testing
./gradlew runServer  # For server testing
```

## How to Contribute

### Reporting Bugs

If you find a bug, please open an issue with:
- A clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Minecraft and mod version
- Any relevant logs

### Suggesting Features

Feature suggestions are welcome! Please open an issue with:
- A clear description of the feature
- Use cases and benefits
- Any relevant examples from other mods/plugins

### Submitting Code

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Test your changes thoroughly
5. Commit with clear messages (`git commit -m 'Add amazing feature'`)
6. Push to your fork (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Code Style

- Follow standard Java conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and concise
- Match the existing code style in the project

### Testing

- Test your changes in both single-player and multiplayer
- Verify commands work as expected
- Check for any errors in the logs
- Test edge cases

## License

By contributing to TeleportPlusPlus, you agree that your contributions will be licensed under the GNU General Public License v3.0.

## Questions?

If you have questions about contributing, feel free to open an issue or discussion on GitHub.
