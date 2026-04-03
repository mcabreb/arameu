# E1-S02: Scaffold Android Project

## Status
To Do

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
- [ ] New Android project created with package name `com.arameu`
- [ ] Project builds successfully with Kotlin and Jetpack Compose
- [ ] App runs on emulator showing a placeholder Compose screen
- [ ] minSdk is set to 31 (Android 12)
- [ ] targetSdk is set to 35 (Android 15)
- [ ] build.gradle.kts includes dependencies: Compose BOM, Material 3, Room (runtime + compiler), Media3/ExoPlayer, Compose Navigation, Kotlin coroutines, kotlinx.serialization
- [ ] Project compiles without warnings from dependency conflicts

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
