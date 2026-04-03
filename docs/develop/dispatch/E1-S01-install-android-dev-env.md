# E1-S01: Install Android Development Environment

## Status
To Do

## Epic
E1 - Project Setup & Dev Environment

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Set up the complete Android development toolchain on the developer's Apple Silicon MacBook Pro (M3 Pro, macOS 26) so the Arameu project can be built, tested, and deployed.

This is the developer's first Android app. The environment must be configured correctly from the start — Android Studio, Kotlin, SDK, and an emulator that can test Compose UI and audio playback.

## Acceptance Criteria
- [ ] Android Studio (latest stable) installed and launches on macOS Apple Silicon
- [ ] Kotlin plugin is active and configured
- [ ] Android SDK 35 (Android 15) downloaded via SDK Manager
- [ ] Android build tools and platform tools installed
- [ ] Android emulator configured with an API 31+ virtual device
- [ ] Emulator boots and displays the Android home screen
- [ ] Hardware acceleration (Hypervisor.framework) enabled for Apple Silicon

## Tasks
- **T1: Download and install Android Studio** — Download Android Studio for Apple Silicon Mac from developer.android.com. Install to /Applications. Launch and complete the setup wizard, which downloads the default SDK components.
- **T2: Configure SDK via SDK Manager** — Open SDK Manager (Settings → Android SDK). Install: SDK Platform for API 35, SDK Build-Tools (latest), Android SDK Platform-Tools, Android Emulator. Verify installation paths in `local.properties`.
- **T3: Create Android Virtual Device** — Open AVD Manager. Create a Pixel 7 (or similar) device with API 31+ system image (arm64-v8a for Apple Silicon). Verify the emulator boots and runs.

## Technical Notes
- Apple Silicon Macs use ARM-based Android emulator images natively — no need for x86 translation
- Android Studio Ladybug (2024+) has excellent Apple Silicon support
- Ensure at least 8GB of disk space free for SDK + emulator images
- Java 24 is already installed on this machine; Android Studio bundles its own JDK (JBR), so no conflict expected

## Dependencies
- None (this is the first ticket in the project).
