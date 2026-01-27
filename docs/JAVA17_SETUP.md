# AG-01 Java 17 Setup Guide

## Problem
Build failed: Gradle requires Java 17, but system uses Java 8.

## Solution Options

### Option 1: Install Java 17 (Recommended)
1. Download Java 17 from [Adoptium](https://adoptium.net/temurin/releases/?version=17)
2. Install it
3. Set JAVA_HOME environment variable:
   ```powershell
   [System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Path\To\Java17', 'User')
   ```
4. Restart terminal/IDE

### Option 2: Use Gradle Toolchain
Add to `build.gradle`:
```gradle
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

### Option 3: Skip Build (Risk)
Proceed to AG-02 without testing compilation.  
**Warning:** May discover errors later during game launch.

## Next Steps
After Java 17 is set up, run:
```bash
.\gradlew clean build
```
