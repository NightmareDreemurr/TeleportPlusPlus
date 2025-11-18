# Building TeleportPlusPlus

This document explains how to build TeleportPlusPlus from source.

## Prerequisites

- **Java 21** or higher (Java Development Kit)
- **Internet connection** to download dependencies from Maven repositories
- **Git** (optional, for cloning the repository)

## Build Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/NightmareDreemurr/TeleportPlusPlus.git
cd TeleportPlusPlus
```

### 2. Build the Mod

On Linux/Mac:
```bash
./gradlew build
```

On Windows:
```bash
gradlew.bat build
```

The build process will:
1. Download the Gradle wrapper (if not already present)
2. Download NeoForge and Minecraft dependencies
3. Compile the Java source code
4. Package everything into a JAR file

### 3. Locate the Built Mod

After a successful build, the mod JAR file will be located at:
```
build/libs/teleportplusplus-1.0.0.jar
```

## Running in Development Mode

For testing during development:

### Client (with GUI):
```bash
./gradlew runClient
```

### Server (no GUI):
```bash
./gradlew runServer
```

## Troubleshooting

### Build Fails with "Could not resolve net.neoforged:neoforge"

This usually means:
- You don't have internet access
- The NeoForge Maven repository is temporarily unavailable
- Your firewall is blocking the connection

**Solution**: Ensure you have a stable internet connection and try again.

### Build Fails with Java Version Error

**Error**: "Unsupported class file major version..."

**Solution**: Make sure you're using Java 21 or higher. Check your version:
```bash
java -version
```

If you need to install Java 21, visit: https://adoptium.net/

### Gradle Wrapper Not Executing

On Linux/Mac, you may need to make the wrapper executable:
```bash
chmod +x gradlew
```

## Clean Build

To clean previous build artifacts and start fresh:
```bash
./gradlew clean build
```

## Additional Gradle Tasks

- `./gradlew tasks` - List all available Gradle tasks
- `./gradlew dependencies` - Show dependency tree
- `./gradlew clean` - Remove build artifacts
- `./gradlew jar` - Build JAR without running tests

## IDE Setup

### IntelliJ IDEA

1. Open the project folder in IntelliJ IDEA
2. IntelliJ will automatically detect it as a Gradle project
3. Wait for Gradle sync to complete
4. Run configurations will be automatically created

### Eclipse

1. Run `./gradlew eclipse` to generate Eclipse project files
2. Import the project into Eclipse as an existing project

### VS Code

1. Install the "Java Extension Pack" from the VS Code marketplace
2. Open the project folder
3. VS Code will automatically detect it as a Gradle project

## Network Requirements

The build process requires access to the following Maven repositories:
- `https://maven.neoforged.net/releases` - NeoForge artifacts
- `https://repo1.maven.org/maven2/` - Maven Central (standard Java libraries)

If you're behind a corporate firewall or proxy, you may need to configure Gradle accordingly.
