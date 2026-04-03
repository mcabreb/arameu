# E1-S03: Configure Catalan Locale & Manuscript Theme

## Status
Done

## Epic
E1 - Project Setup & Dev Environment

## Priority
High

## Estimate
S

## Description
[ARAMEU] Define the app's visual identity and language. The UI speaks Catalan. The visual design evokes an ancient manuscript — terracotta warmth, parchment backgrounds, aged ink text. This is not a default Material 3 app; it is a beautiful book that was waiting for the learner to open it.

The design follows Nuria's own words: "like coming home to a beautiful book." Minimal, generous whitespace, one thing at a time. The craft is in the care, not the decoration.

**Colour palette (from user research):**
- Terracotta — dominant warm tone, Mediterranean clay (#C67B5C or similar)
- Parchment cream — soft warm backgrounds (#F5ECD7 or similar)
- Aged ink / dark ochre — text colour, not harsh black (#4A3728 or similar)
- Accent: muted gold for active/highlighted elements

**Typography:**
- Body text: warm serif or humanist sans (consider Merriweather or Source Serif)
- Aramaic script: Noto Sans Hebrew at ≥24sp
- Generous line spacing, large margins

## Acceptance Criteria
- [x] Default strings.xml contains all UI labels in Catalan (Comença, Repassa, Següent, Escolta, Has après, Correcte, Torna-ho a provar, etc.)
- [x] String resources externalised in res/values/strings.xml for future localisation
- [x] Custom Material 3 colour scheme applied: terracotta primary, parchment background, aged ink text
- [x] Typography defined with warm readable fonts and Noto Sans Hebrew for Aramaic script
- [x] Aramaic script renders correctly with nikkud at ≥24sp
- [x] Touch targets ≥48dp per Material Design guidelines
- [x] Generous whitespace: content padding ≥24dp, card spacing ≥16dp
- [x] Visual feel: warm, calm, manuscript-like — not clinical or app-like

## Tasks
- **T1: Create Catalan string resources** — Write `res/values/strings.xml` with all known UI labels in Catalan. Include: app_name="Arameu", welcome title, button labels, exercise prompts (Escriu en transliteració, Escolta i tria, Emparella, Tria la resposta correcta), feedback messages (Correcte!, No del tot, Torna-ho a provar), navigation labels.
- **T2: Define colour palette** — Create `ui/theme/Color.kt` with the terracotta/parchment/aged ink palette. Define light theme colours mapping: primary=terracotta, background=parchment cream, onBackground=aged ink, surface=warm white, surfaceVariant=lighter parchment. Consider a subtle dark theme variant using deep warm browns.
- **T3: Define typography** — Create `ui/theme/Type.kt`. Body text in a warm readable font (Merriweather via Google Fonts or bundled). Aramaic display text in Noto Sans Hebrew, minimum 24sp. Set generous line heights (1.6x for body, 1.8x for Aramaic display).
- **T4: Apply theme** — Create `ui/theme/Theme.kt` wrapping Material 3 `MaterialTheme` with custom colour and typography. Apply in `MainActivity`. Set status bar colour to match.
- **T5: Bundle Noto Sans Hebrew font** — Add Noto Sans Hebrew to `res/font/` or use Google Fonts Compose provider. Verify nikkud renders correctly.

## Technical Notes
- Material 3 dynamic colour should be DISABLED — the terracotta palette is intentional, not system-derived
- Consider using `CompositionLocalProvider` to provide custom spacing values (generous margins)
- The parchment background should feel textured/warm even without actual texture images — achieve through colour and whitespace
- Test Aramaic script rendering with full nikkud: מַלְכָּא should display correctly with all vowel points
- Font loading via Google Fonts Compose may require network on first load — consider bundling for offline-first

## Dependencies
- E1-S02 (Scaffold Android Project) -- provides the project structure and Compose setup to apply the theme to.

## Implementation Summary

**Files Created/Modified:**
- `app/src/main/java/com/arameu/ui/theme/Color.kt` — Manuscript palette: Terracotta, Parchment, AgedInk, MutedGold + full Material 3 light scheme (~35 lines)
- `app/src/main/java/com/arameu/ui/theme/Type.kt` — ArameuTypography: Serif body (1.6x line height), NotoSansHebrew display (≥24sp, 1.8x line height) (~100 lines)
- `app/src/main/java/com/arameu/ui/theme/Theme.kt` — ArameuTheme composable with ManuscriptLightColorScheme, status bar colour, CompositionLocalProvider for spacing (~30 lines)
- `app/src/main/java/com/arameu/ui/theme/Spacing.kt` — ArameuSpacing: contentPadding=24dp, cardSpacing=16dp, touchTarget=48dp + LocalSpacing CompositionLocal (~20 lines)
- `app/src/main/java/com/arameu/MainActivity.kt` — Updated to use themed welcome screen with Catalan strings (~55 lines)
- `app/src/main/res/values/strings.xml` — 30+ Catalan UI strings: welcome, nav, exercises, feedback, progress, accessibility
- `app/src/main/res/font/noto_sans_hebrew_regular.ttf` — Bundled static TTF (offline-first)
- `app/src/main/res/font/noto_sans_hebrew_bold.ttf` — Bundled static TTF bold
- `app/src/test/java/com/arameu/ui/theme/ThemeTest.kt` — 12 tests: colours, scheme mapping, typography sizes, spacing values

**Key Decisions:**
- Bundled Noto Sans Hebrew as static TTF files (not Google Fonts provider) for offline-first operation
- Used system Serif FontFamily for body text rather than bundling Merriweather — reduces APK size, Serif on Android (Noto Serif) is warm and readable
- Dynamic colour disabled — intentional manuscript palette overrides Material You
- CompositionLocalProvider for spacing — allows screens to access ArameuSpacing via LocalSpacing.current

**Tests:** 12 new tests (14 total), all passing
**Branch:** hive/E1-project-setup
**Date:** 2026-04-03
