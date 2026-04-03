# E7-S01: Build Welcome Screen

## Status
To Do

## Epic
E7 - Integration, Polish & Delivery

## Priority
High

## Estimate
S

## Description
[ARAMEU] Create the first screen Nuria sees when she opens her birthday gift. This is the threshold — the moment the app becomes real. It should feel like opening a beautiful book for the first time: warm, personal, inviting.

The welcome screen says "Benvinguda a Arameu" (Welcome to Arameu) with a brief description in Catalan explaining what the app is. One button: "Comença" (Begin). No login, no setup, no choices. Just a door waiting to be opened.

This screen appears only on first launch. After that, the app opens directly to the course map — like picking up a book where you left off.

## Acceptance Criteria
- [ ] First launch shows welcome screen with "Benvinguda a Arameu" heading
- [ ] Brief Catalan description: "Un viatge per aprendre arameu bíblic, pas a pas." (A journey to learn Biblical Aramaic, step by step.)
- [ ] Single "Comença" button in terracotta, centred at bottom
- [ ] Visual style: parchment background, generous whitespace, warm typography, manuscript feel
- [ ] Welcome screen only shown on first launch — subsequent launches go directly to course map
- [ ] No loading spinner, no splash screen delay — the welcome is immediate
- [ ] App icon displays as "Arameu" on the device home screen

## Tasks
- **T1: Create WelcomeScreen composable** — Full-screen parchment background. Centred content: app name "Arameu" in large warm serif, subtitle "Arameu bíblic" in smaller text, description paragraph in Catalan, "Comença" button in terracotta with parchment text. Consider a subtle Aramaic letter (א) as a watermark/decorative element. Generous padding on all sides.
- **T2: Implement first-launch flag** — SharedPreferences boolean `has_launched`. Check in ArameuNavGraph: if false → /welcome as start destination; if true → /course. Set to true when user taps "Comença".
- **T3: Configure app icon and name** — Set app_name in strings.xml to "Arameu". Create a simple launcher icon (could be the letter Aleph א in terracotta on parchment background, or a simple warm-toned icon). Configure in AndroidManifest.xml.

## Technical Notes
- Welcome screen should feel like an invitation, not a barrier — minimal text, maximum warmth
- SharedPreferences for first-launch is simpler than DataStore for a single boolean — acceptable for MVP
- App icon: if custom icon is too time-consuming, Android Studio's Image Asset tool can generate a basic icon from a letter
- Consider adding the Aramaic letter Aleph as a subtle background element — it's the beginning, the first letter, the start of the journey

## Dependencies
- E3-S03 (Navigation Graph) -- provides the navigation framework and route definitions.
