# E1-S02: Scaffold Android Project

## Status
Done

## Epic
E1 - Project Setup & Dev Environment

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Create the foundational Kotlin + Jetpack Compose Android project with all required dependencies configured and building successfully. This is the skeleton on which the entire app is built.

The project must compile with Room (database), Media3 (audio), and Compose Navigation from day one, so no dependency issues surface later.

## Acceptance Criteria
- [x] New Android project created with package name `com.arameu`
- [x] Project builds successfully with Kotlin and Jetpack Compose
- [x] App runs on emulator showing a placeholder Compose screen
- [x] minSdk is set to 31 (Android 12)
- [x] targetSdk is set to 35 (Android 15)
- [x] build.gradle.kts includes dependencies: Compose BOM, Material 3, Room (runtime + compiler), Media3/ExoPlayer, Compose Navigation, Kotlin coroutines, kotlinx.serialization
- [x] Project compiles without warnings from dependency conflicts

## Tasks
- **T1: Create project via Android Studio** — File → New → New Project → Empty Activity (Compose). Set package to `com.arameu`, name to "Arameu", language Kotlin, minSdk 31. This generates the basic project structure under `app/`.
- **T2: Configure build.gradle.kts** — Add all dependencies to `app/build.gradle.kts`: Compose BOM (latest stable), Material 3, Room runtime + KSP compiler, Media3 ExoPlayer, Compose Navigation, Kotlin coroutines, kotlinx.serialization for JSON parsing. Configure KSP plugin for Room annotation processing.
- **T3: Verify build and run** — Build the project. Run on emulator. Confirm the default Compose screen renders. Check for dependency resolution errors or version conflicts.

## Technical Notes
- Use Compose BOM to manage Compose version alignment across all Compose dependencies
- Room requires KSP (Kotlin Symbol Processing) — add the KSP Gradle plugin
- kotlinx.serialization is preferred over Gson for JSON parsing (better Kotlin support, no reflection)
- Project structure follows architecture doc: `com.arameu.{ui,data,audio,util,navigation}`

## Dependencies
- E1-S01 (Install Android Dev Environment) -- provides the development tools and SDK needed to create the project.

## Implementation Summary

**Files Created/Modified:**
- `settings.gradle.kts` — Root settings with plugin management and dependency resolution (~20 lines)
- `build.gradle.kts` — Root build file with plugin aliases (~7 lines)
- `gradle.properties` — JVM args, AndroidX, JBR 21 JAVA_HOME (~5 lines)
- `gradle/libs.versions.toml` — Version catalog: AGP 8.9.3, Kotlin 2.1.20, Compose BOM 2025.04.00, Room 2.7.1, Media3 1.6.1, Navigation 2.9.0 (~80 lines)
- `app/build.gradle.kts` — App module with all dependencies, KSP for Room (~75 lines)
- `app/src/main/AndroidManifest.xml` — Single activity, ArameuApp application class
- `app/src/main/java/com/arameu/ArameuApp.kt` — Application class (~5 lines)
- `app/src/main/java/com/arameu/MainActivity.kt` — Single activity with Compose, placeholder screen (~30 lines)
- `app/src/main/java/com/arameu/ui/theme/{Color,Type,Theme}.kt` — Default theme (will be customized in E1-S03)
- `app/src/main/res/values/strings.xml` — App name string
- `app/src/main/res/values/themes.xml` — XML theme for manifest
- `app/src/main/res/drawable/ic_launcher_{background,foreground}.xml` — Adaptive icon (terracotta bg)
- `app/src/main/res/mipmap-anydpi-v26/ic_launcher{,_round}.xml` — Adaptive icon config
- `app/src/test/java/com/arameu/ProjectSetupTest.kt` — 2 setup verification tests
- `.gitignore` — Android/Gradle entries added
- `app/proguard-rules.pro` — Empty proguard placeholder

**Key Decisions:**
- Used Gradle version catalog (`libs.versions.toml`) for centralized dependency management
- Set `org.gradle.java.home` to Android Studio's JBR 21 — JDK 24 causes Gradle test task failures
- Scaffolded via CLI + manual files rather than Android Studio wizard (headless environment)
- Directory structure follows architecture doc: `com.arameu.{ui,data,audio,navigation,util}`

**Tests:** 2 new tests, all passing
**Branch:** hive/E1-project-setup
**Date:** 2026-04-03
