# E7-S03: Build & Deliver Signed APK

## Status
To Do

## Epic
E7 - Integration, Polish & Delivery

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Build the release APK and install it on Nuria's phone. This is the moment: the birthday gift, delivered. A signed, optimised APK that launches as "Arameu" with its own icon, ready for her to open.

No Play Store for MVP — the APK is sideloaded directly onto her device via USB or file sharing.

## Acceptance Criteria
- [ ] Release signing keystore generated and stored securely
- [ ] Release build configuration in build.gradle.kts with signing, minification (R8), and shrinking
- [ ] Signed release APK builds successfully without errors
- [ ] APK size is reasonable (< 100MB including all audio assets)
- [ ] APK installs on target device without "unknown sources" complications (or clear instructions provided)
- [ ] App launches correctly from device home screen
- [ ] App icon displays as "Arameu" with custom launcher icon
- [ ] All functionality verified post-install (quick smoke test: welcome → first lesson → audio plays)

## Tasks
- **T1: Generate signing keystore** — Run: `keytool -genkey -v -keystore arameu-release.ks -keyalg RSA -keysize 2048 -validity 10000 -alias arameu`. Store the keystore file securely (NOT in git). Document the password in a secure location.
- **T2: Configure release build** — In `app/build.gradle.kts`: add signingConfigs block for release with keystore path, passwords. Configure buildTypes.release with signingConfig, `isMinifyEnabled = true`, `isShrinkResources = true`, proguard rules (default + keep Room entities, keep kotlinx.serialization).
- **T3: Build release APK** — In Android Studio: Build → Generate Signed Bundle / APK → APK → select release keystore → build. Alternatively: `./gradlew assembleRelease`. Output: `app/build/outputs/apk/release/app-release.apk`.
- **T4: Transfer and install** — Connect Nuria's phone via USB (or use nearby share / file transfer). Enable "Install unknown apps" for the file manager. Install the APK. Verify launch. Disable "Install unknown apps" after.
- **T5: Smoke test on device** — Quick verification: welcome screen → Comença → first lesson → audio plays → exercise works → back to course map. Confirm everything runs on her specific device.

## Technical Notes
- Keep the signing keystore OUT of version control — add `*.ks` and `*.jks` to .gitignore
- R8 minification may strip Room or serialization classes — test the release build, not just debug
- ProGuard keep rules needed: Room entities, kotlinx.serialization, Media3 classes
- APK vs AAB: for sideloading, use APK (not Android App Bundle which requires Play Store)
- If Nuria's phone has USB-C: direct file transfer via cable is simplest. Otherwise, use nearby share or email the APK.

## Dependencies
- E7-S02 (End-to-End Testing) -- all testing must pass before building the release.
