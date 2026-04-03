# E1-S03: Configure Catalan Locale & Manuscript Theme

## Status
To Do

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
- [ ] Default strings.xml contains all UI labels in Catalan (Comença, Repassa, Següent, Escolta, Has après, Correcte, Torna-ho a provar, etc.)
- [ ] String resources externalised in res/values/strings.xml for future localisation
- [ ] Custom Material 3 colour scheme applied: terracotta primary, parchment background, aged ink text
- [ ] Typography defined with warm readable fonts and Noto Sans Hebrew for Aramaic script
- [ ] Aramaic script renders correctly with nikkud at ≥24sp
- [ ] Touch targets ≥48dp per Material Design guidelines
- [ ] Generous whitespace: content padding ≥24dp, card spacing ≥16dp
- [ ] Visual feel: warm, calm, manuscript-like — not clinical or app-like

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
