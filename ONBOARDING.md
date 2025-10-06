# Foodium Onboarding & Local Setup Guide

A concise, practical guide to get the Foodium Android sample application running locally as fast as possible.

---

## 1. Overview

Foodium is a Kotlin-based sample Android app demonstrating modern Android development practices: MVVM architecture, coroutines, Flow, Room, Retrofit, Hilt, and Material components. It loads a static dummy JSON feed (no authentication or API keys required).

---

## 2. Prerequisites

Ensure the following are installed:

- Android Studio (Arctic Fox or later recommended; Chipmunk+ works fine)
- JDK 8 (Android Gradle Plugin + project sets `sourceCompatibility` / `targetCompatibility` 1.8)
- Android SDK Platform 31 (compileSdkVersion 31)
- Gradle (handled via the project wrapper)
- An Android device or emulator (API level 21+; `minSdkVersion = 21`)

---

## 3. Repository Structure (Essentials)

```
Foodium/
  app/                     # Main Android application module
  build.gradle.kts         # Top-level build logic aggregating subprojects
  app/build.gradle.kts     # Module-specific config (dependencies, build types)
  gradle.properties        # Global Gradle & AndroidX flags
  buildSrc/                # Centralized dependency definitions (Kotlin DSL)
  media/                   # Project images/assets for README
```

Key Concepts:
- Dependency Injection: Hilt
- Persistence: Room
- Reactive/Data Flow: Coroutines + Flow + LiveData
- Networking: Retrofit + Moshi
- UI: Material Components + ViewBinding
- Code Style: ktlint (via Gradle plugin)

---

## 4. Clone the Project

```bash
git clone https://github.com/sam-arth07/Foodium.git
cd Foodium
```

Open the root folder in Android Studio.

---

## 5. First Build & Sync

Android Studio will automatically:
1. Download Gradle wrapper + dependencies
2. Index sources
3. Apply Kotlin DSL scripts

If sync does not start automatically:
- Click: File > Sync Project with Gradle Files

---

## 6. Running the App

### Option A: Android Studio
1. Select a device/emulator (API 21+)
2. Build & Run (Shift + F10 or ▶)

### Option B: Command Line
```bash
./gradlew assembleDebug
# APK output: app/build/outputs/apk/debug/app-debug.apk
```

To install directly on a connected device:
```bash
./gradlew installDebug
```

---

## 7. Running Tests

```bash
./gradlew test
```

This runs JVM unit tests (e.g., Room, repository, coroutine-based logic). Instrumented tests are scaffolded; add them under `androidTest/` if extending.

---

## 8. Code Quality (ktlint)

Check formatting:
```bash
./gradlew ktlintCheck
```

Auto-format (if enabled in plugin configuration):
```bash
./gradlew ktlintFormat
```

---

## 9. Dependency & Build Notes

- Dependencies are centrally managed (likely via objects/constants under `buildSrc/`).
- Hilt + KAPT are already configured (no further setup required).
- Room schema location is defined (`app/schemas`) via annotation processor arguments.
- Packaging excludes `META-INF/*.kotlin_module` to avoid duplicate metadata errors.

---

## 10. Generating a Release Variant

Current `release` build type:
- `isMinifyEnabled = false`
- Uses default + `proguard-rules.pro` (inactive until minification is enabled)

To assemble:
```bash
./gradlew assembleRelease
```

(You may enable shrinking/obfuscation before distribution.)

---

## 11. Troubleshooting

| Issue | Cause | Resolution |
|-------|-------|------------|
| Gradle sync fails with JDK error | Using unsupported JDK | Switch to JDK 8 in Android Studio settings |
| KAPT build slow | Annotation processing overhead | Enable incremental annotation processing where possible (already partly configured) |
| jcenter() warnings | Repository deprecation | For modernization, migrate dependencies to Maven Central (future enhancement) |
| Emulator crashes on start | Low system resources | Cold boot emulator; lower resolution/device profile |
| Lint/style failures | ktlint violations | Run `./gradlew ktlintFormat` before committing |

---

## 12. Contributing

1. Fork the repository
2. Create a feature branch (`feat/<short-description>`)
3. Run tests + `ktlintCheck`
4. Open a Pull Request describing changes
5. Follow any guidelines in [CONTRIBUTING.md](CONTRIBUTING.md)

---

## 13. Quick Commands Reference

```bash
# Build debug
./gradlew assembleDebug

# Install debug build
./gradlew installDebug

# Run unit tests
./gradlew test

# Lint check
./gradlew ktlintCheck

# Format (if available)
./gradlew ktlintFormat
```

Happy hacking! If something feels unclear, refining this guide incrementally in collaboration is encouraged.
