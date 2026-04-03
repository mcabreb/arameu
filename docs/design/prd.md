# Arameu — Product Requirements Document

**Revision:** 1
**Revision Date:** 2026-04-03
**Author:** Marti Cabré + AI assistant (Flora, Forrest, Spark, Melody)
**Status:** Draft

## 1. Product Overview

### 1.1 Vision

Arameu is a personal Android app that teaches Biblical Aramaic from zero through structured, bite-sized lessons. Built as a gift for a single learner, it follows the Duolingo lesson model stripped to its essence: no gamification, no social features, no accounts — just a clean, progressive course that takes a complete beginner from the alphabet to reading the words of Daniel and Ezra. The UI speaks Catalan; the teaching bridges Catalan and Aramaic.

### 1.2 Goals

- G1: Deliver a working MVP (Units 1-3) by April 11, 2026
- G2: Teach the complete Aramaic alphabet (22 letters) with correct academic pronunciation
- G3: Progress from zero to intermediate Biblical Aramaic reading ability across ~20 units
- G4: Operate fully offline with zero ongoing costs
- G5: Provide a complete learning loop: read, write, listen, speak

### 1.3 Non-Goals

- No gamification (points, streaks, badges, leaderboards)
- No social features or user accounts
- No cloud sync or server infrastructure
- No iOS version
- No support for other Aramaic dialects
- No automated speech recognition
- No Play Store publishing for MVP
- No advanced level (beyond intermediate)

## 2. Users & Personas

### 2.1 Nuria (Primary Learner)

- **Role:** Sole user of the app
- **Technical level:** Standard smartphone user — comfortable with apps, not a developer
- **Language:** Catalan native, fluent in Spanish
- **Background:** Catholic, motivated by religious study of Biblical texts
- **Primary goal:** Learn Biblical Aramaic from zero via Catalan — read and understand passages from Daniel and Ezra
- **Pain points:** No accessible mobile resource exists for Biblical Aramaic; existing materials are academic textbooks or scattered online resources; no structured progressive path
- **Device:** Recent Android phone with Hebrew keyboard available

### 2.2 Marti (Developer)

- **Role:** Sole developer, maintainer, and content author
- **Technical level:** Experienced programmer, first Android app
- **Primary goal:** Build, test, and iterate on the app independently
- **Device:** MacBook Pro M3 Pro, 18GB, macOS 26, Java 24

## 3. Functional Requirements

### 3.1 Course Structure

#### FR-001: Linear Unit Progression
- **Priority:** Must
- **Description:** The app presents a linear sequence of units (1-20), each containing 4-6 lessons. Units unlock sequentially — the next unit becomes available when all lessons in the current unit are completed.
- **Trigger:** User opens the app / completes a lesson
- **Expected outcome:** Course map shows completed units, current unit (active), and locked future units
- **Acceptance criteria:**
  - Units display as locked/active/completed
  - Completing the last lesson in a unit unlocks the next
  - User cannot skip ahead to locked units

#### FR-002: Lesson Structure
- **Priority:** Must
- **Description:** Each lesson consists of 4 phases: Introduction (new concept with audio + transliteration + meaning), Guided Practice (3-4 easy exercises on new material), Mixed Practice (3-4 exercises mixing new and review material), Summary (what was learned).
- **Trigger:** User taps on an available lesson
- **Expected outcome:** Lesson plays through all 4 phases sequentially, taking 5-8 minutes
- **Acceptance criteria:**
  - Lesson displays introduction with audio playback
  - Exercises load in the correct phase order
  - Summary shows at completion
  - Lesson duration averages 5-8 minutes

#### FR-003: Course Content — Four Levels
- **Priority:** Must (Foundations for MVP; others for later phases)
- **Description:** Content is organised into four levels:
  - Foundations (Units 1-5): Alphabet, transliteration, basic sounds, greetings, simple nouns
  - Building Blocks (Units 6-10): Pronouns, particles, basic verbs (pe'al), construct chains, numbers
  - Sentences (Units 11-15): Verb conjugations, prepositions, adjectives, simple sentences
  - Scripture (Units 16-20): Complex grammar, vocabulary from Daniel/Ezra, real passage fragments
- **Acceptance criteria:**
  - Each unit has 4-6 lessons
  - Vocabulary introduces ~75-100 new items per level
  - Grammar concepts build sequentially without gaps

### 3.2 Exercise Types

#### FR-010: Multiple Choice
- **Priority:** Must
- **Description:** Display a prompt (text, audio, or script) and 4 answer options. One is correct. User taps to select. Immediate feedback (correct/incorrect with the right answer shown).
- **Acceptance criteria:**
  - 4 options displayed, 1 correct
  - Incorrect options are plausible distractors from the same unit/level
  - Correct answer highlighted green, wrong answer red
  - Audio plays on prompt if the exercise is listening-based

#### FR-011: Matching Pairs
- **Priority:** Must
- **Description:** Display two columns (e.g., transliteration and meaning) with 4-5 items each. User taps items in each column to match them. Matched pairs disappear or highlight.
- **Acceptance criteria:**
  - 4-5 pairs per exercise
  - Visual feedback on correct match (pair highlights and clears)
  - Visual feedback on incorrect match (shake/flash)
  - Exercise completes when all pairs matched

#### FR-012: Type Transliteration
- **Priority:** Must
- **Description:** Display a word or phrase in Catalan meaning (or play audio), user types the Aramaic transliteration using the English keyboard. Case-insensitive matching. Minor spelling tolerance (e.g., accept "sh" and "š").
- **Trigger:** Exercise loaded in lesson
- **Expected outcome:** User types answer, system validates, feedback shown
- **Acceptance criteria:**
  - English keyboard input accepted
  - Validation is case-insensitive
  - Common transliteration variants accepted (configurable per word)
  - Correct/incorrect feedback displayed

#### FR-013: Listen and Choose
- **Priority:** Must (Dev Phase 2)
- **Description:** Audio plays an Aramaic word or phrase. User selects the correct transliteration or meaning from 4 options.
- **Acceptance criteria:**
  - Audio plays automatically when exercise loads
  - Replay button available
  - 4 options, 1 correct
  - Standard correct/incorrect feedback

#### FR-014: Listen and Repeat (Self-Assessed)
- **Priority:** Must (Dev Phase 2)
- **Description:** Audio plays a word or phrase. The app displays the transliteration and script. User listens, practises speaking aloud, then self-assesses: "Sí, l'he encertat" (Got it) or "Repeteix" (Try again).
- **Acceptance criteria:**
  - Audio plays with replay button
  - Transliteration and Aramaic script shown
  - Two self-assessment buttons
  - "Try again" replays audio; "Got it" advances

#### FR-015: Script Reading
- **Priority:** Must (Dev Phase 2)
- **Description:** Display a word in Square Aramaic script (e.g., מַלְכָּא). User selects the correct transliteration from 4 options.
- **Acceptance criteria:**
  - Aramaic script renders correctly with nikkud
  - 4 transliteration options, 1 correct
  - Standard feedback

#### FR-016: Script Writing
- **Priority:** Must (Dev Phase 2)
- **Description:** Display a word in transliteration (e.g., `malka`). User types the Aramaic script using the Hebrew keyboard (e.g., מלכא). Matching accepts with or without nikkud.
- **Acceptance criteria:**
  - Hebrew keyboard input accepted
  - Validation matches consonantal skeleton (nikkud optional)
  - Correct/incorrect feedback with the full pointed form shown on feedback

### 3.3 Audio System

#### FR-020: Bundled Audio Playback
- **Priority:** Must
- **Description:** All vocabulary items and phrases have pre-generated audio files bundled with the app. Audio plays on tap (in introductions and glossary) and automatically (in listening exercises).
- **Acceptance criteria:**
  - Audio files in MP3 or OGG format, bundled in APK/assets
  - Playback latency < 500ms from tap
  - Audio plays correctly on device speaker and headphones
  - Volume follows system media volume

#### FR-021: AI-Generated Audio Content
- **Priority:** Must
- **Description:** All pronunciation audio is generated using free-tier AI TTS or open-source TTS tools during development. Standard academic Biblical Aramaic pronunciation.
- **Acceptance criteria:**
  - Every vocabulary item in shipped units has corresponding audio
  - Pronunciation follows standard academic conventions
  - Audio quality sufficient for learning (clear, no artefacts)

### 3.4 Progress Tracking

#### FR-030: Lesson Completion Tracking
- **Priority:** Must
- **Description:** The app records which lessons have been completed and the score (percentage correct) for each. Data persisted locally in SQLite.
- **Acceptance criteria:**
  - Completion state survives app restart
  - Score recorded per lesson (0-100%)
  - Course map reflects completion state

#### FR-031: Current Position
- **Priority:** Must
- **Description:** On app launch, the user sees the course map with their current position clearly indicated — the next uncompleted lesson is highlighted and easy to tap.
- **Acceptance criteria:**
  - Current lesson visually distinguished (colour, size, or animation)
  - One tap to resume learning
  - Scroll position defaults to current unit

#### FR-032: Spaced Repetition Review
- **Priority:** Must (Dev Phase 2)
- **Description:** Vocabulary items are tracked with a spaced repetition algorithm (simplified Leitner or SM-2). Words answered incorrectly or not seen recently are resurfaced in Mixed Practice sections and in a dedicated Review mode.
- **Acceptance criteria:**
  - Each vocabulary item has a "next review" timestamp
  - Items due for review appear in mixed practice exercises
  - Algorithm increases interval on correct answers, resets on incorrect

#### FR-033: Progress Dashboard
- **Priority:** Should (Dev Phase 2)
- **Description:** A screen showing: total lessons completed, current unit/level, total vocabulary learned, review items pending.
- **Acceptance criteria:**
  - Accessible from main navigation
  - Displays at least: lessons done / total, vocabulary count, current level name

### 3.5 Scripture Milestone Lessons

#### FR-040: Biblical Passage Lessons
- **Priority:** Must (Dev Phase 3)
- **Description:** At milestone points (end of each level, and throughout Scripture level), lessons feature real Biblical Aramaic verses. The verse is shown in script with word-by-word breakdown: tap a word to see transliteration, morphology, and translation.
- **Acceptance criteria:**
  - Real verses from Daniel 2-7 and Ezra 4-7
  - Word-by-word tap interaction
  - Each word shows: script → transliteration → grammatical note → Catalan meaning
  - Full verse translation available

#### FR-041: Word-by-Word Passage Reader
- **Priority:** Should (Dev Phase 3)
- **Description:** Standalone reader for Biblical passages. Shows the verse in Aramaic script. Tap any word to reveal transliteration, grammar, and meaning. Tap-to-reveal, not always visible.
- **Acceptance criteria:**
  - Passage displayed in Aramaic script with nikkud
  - Tap word → popover/tooltip with details
  - Accessible from lesson or glossary

### 3.6 Reference & Review

#### FR-050: Vocabulary Glossary
- **Priority:** Should (Dev Phase 3)
- **Description:** Searchable list of all learned vocabulary. Each entry shows: Aramaic script, transliteration, Catalan meaning, audio playback button. Searchable by transliteration or Catalan.
- **Acceptance criteria:**
  - Only shows vocabulary from completed lessons (no spoilers)
  - Search filters results as user types
  - Audio plays on tap
  - Sorted alphabetically by transliteration (default)

#### FR-051: Review Mode
- **Priority:** Should (Dev Phase 4)
- **Description:** User can revisit any completed lesson to practise again. Scores update but do not affect unit progression.
- **Acceptance criteria:**
  - Completed lessons show "Repassa" (Review) button
  - Review scores tracked separately from first-attempt scores

### 3.7 UI Language

#### FR-060: Catalan Interface
- **Priority:** Must
- **Description:** All UI elements — navigation labels, buttons, instructions, exercise prompts, feedback messages, settings — are in Catalan. Teaching content (vocabulary meanings) is in Catalan. Aramaic content is in transliteration and/or script.
- **Acceptance criteria:**
  - All static UI strings in Catalan
  - String resources externalised for potential future localisation
  - Exercise prompts in Catalan (e.g., "Escriu en transliteració:", "Escolta i tria:")
  - Vocabulary meanings in Catalan (e.g., malka = "rei")

## 4. Non-Functional Requirements

### 4.1 Performance
- NFR-P01: App cold start < 3 seconds on target device
- NFR-P02: Lesson load time < 1 second
- NFR-P03: Audio playback latency < 500ms from user tap
- NFR-P04: Exercise transition (answer feedback → next exercise) < 300ms

### 4.2 Reliability
- NFR-R01: Progress data persists across app restarts, crashes, and OS kills
- NFR-R02: App functions with no network connectivity (fully offline)
- NFR-R03: No data loss on unexpected app termination mid-lesson

### 4.3 Security
- NFR-S01: No user data leaves the device — all storage is local
- NFR-S02: No network calls required for core functionality
- NFR-S03: No analytics, tracking, or telemetry

### 4.4 Compatibility
- NFR-C01: Minimum Android 12 (API 31)
- NFR-C02: Supports standard screen sizes (phones only — no tablet optimisation required)
- NFR-C03: Hebrew keyboard input supported for script writing exercises
- NFR-C04: Correct rendering of Square Aramaic script with nikkud (vowel pointing)

### 4.5 Usability
- NFR-U01: Target user is a non-technical adult learner — all interactions must be intuitive without instructions
- NFR-U02: Font size for Aramaic script ≥ 24sp for readability
- NFR-U03: Touch targets ≥ 48dp per Material Design guidelines
- NFR-U04: High contrast between text and background for script exercises

## 5. User Flows

### 5.1 First Launch
1. User installs APK (sideloaded)
2. App opens to a welcome screen: "Benvinguda a Arameu" with brief description in Catalan
3. User taps "Comença" (Start)
4. Course map displays: Unit 1 active, all others locked
5. User taps Unit 1, Lesson 1

### 5.2 Complete a Lesson
1. User taps next available lesson
2. Introduction phase: new concept shown with audio, transliteration, Aramaic script, and meaning
3. User taps audio to hear pronunciation (can replay)
4. Guided Practice: 3-4 exercises on the new material
5. For each exercise: prompt displayed → user responds → immediate feedback → next exercise
6. Mixed Practice: 3-4 exercises mixing new and previously learned material
7. Summary: "Has après:" (You learned:) + list of new items
8. Lesson marked complete, score saved, return to course map
9. If unit complete: next unit unlocks with brief celebration message

### 5.3 Review Vocabulary (Phase 2+)
1. User navigates to "Repàs" (Review) from main navigation
2. App selects vocabulary items due for spaced repetition review
3. Review session: 10-15 exercises on due items
4. Correct answers increase review interval; incorrect answers reset
5. Session ends: "Repàs completat" with items reviewed count

### 5.4 Read a Biblical Passage (Phase 3+)
1. User reaches a milestone lesson or opens passage reader
2. Full verse displayed in Aramaic script
3. User taps a word → popover shows transliteration, grammar, Catalan meaning
4. User can play audio for individual words
5. "Mostra traducció" (Show translation) button reveals full verse translation

## 6. Constraints & Assumptions

### 6.1 Technical Constraints
- Kotlin + Jetpack Compose (native Android)
- SQLite for local storage (Room library)
- Minimum Android 12 (API 31)
- All content bundled in APK — no server dependency
- AI-generated audio using free tools only
- Development on macOS (Apple Silicon M3 Pro)

### 6.2 Business Constraints
- Solo developer, first Android app
- MVP deadline: April 11, 2026 (8 days)
- Zero financial budget — all tools and services must be free
- Sideloaded APK distribution (no Play Store for MVP)

### 6.3 Assumptions
- Nuria's phone runs Android 12 or later
- Hebrew keyboard is available on her device (standard Android feature)
- Free-tier AI TTS can produce acceptable Biblical Aramaic pronunciation
- Standard academic pronunciation is appropriate for Catholic liturgical context
- A bounded vocabulary of ~1,500-2,000 items covers beginner-to-intermediate Biblical Aramaic
- Catalan translations of Biblical Aramaic vocabulary are accurate and natural
- 5-8 minute lessons are appropriate session length for adult learner

## 7. Success Metrics

| Metric | Target | How Measured |
|--------|--------|--------------|
| MVP delivery | App on Nuria's phone by April 11 | APK installs and launches |
| Alphabet recognition | All 22 letters taught in Units 1-3 | Unit 3 script exercises passable at >80% |
| Lesson playability | Units 1-3 fully functional | All lessons complete without crashes |
| Audio coverage | 100% of MVP vocabulary has audio | Manual audit of audio assets |
| Offline operation | Works with no internet | Airplane mode test |
| Session length | 5-8 minutes per lesson | Timed playthrough |

## 8. Dependencies

- **Android Studio** — IDE (free, must be installed)
- **Kotlin + Jetpack Compose** — language and UI framework (bundled with Android Studio)
- **Room** — SQLite abstraction library (free, Google)
- **Media3 / ExoPlayer** — audio playback (free, Google)
- **AI TTS service** — for generating audio files during development (free tier — to be determined: Google TTS, edge-tts, or Piper)
- **Hebrew Unicode fonts** — for Aramaic script rendering (bundled with Android, or Noto Sans Hebrew)
- **Biblical Aramaic reference grammars** — for course content accuracy (Rosenthal, Johns, Greenspahn)

## 9. Risks

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| 8-day MVP deadline too tight | M | H | Ruthless scope: 3 units, 3 exercise types only. No script exercises in MVP. |
| Free TTS produces poor Aramaic pronunciation | M | M | Test multiple services early (day 1-2). Fallback: record manually or use Hebrew TTS as approximation. |
| Course content inaccuracy | L | H | Cross-reference with published grammars. Start with alphabet (hard to get wrong). |
| Hebrew keyboard nikkud inconsistency | M | L | Accept consonantal input without nikkud. Show full pointed form in feedback only. |
| First Android project — unknown unknowns | H | M | Follow official Android tutorials. Keep architecture simple. No premature abstractions. |
| Aramaic script rendering issues | L | M | Test early on target device. Use Noto Sans Hebrew font which supports full nikkud. |

## 10. Glossary

| Term | Definition |
|------|------------|
| Biblical Aramaic | The Aramaic dialect found in the Hebrew Bible, primarily in Daniel 2-7, Ezra 4-7, and one verse in Jeremiah (10:11) |
| Transliteration | Representing Aramaic words using English (Latin) alphabet characters (e.g., מַלְכָּא → malka) |
| Square Aramaic script | The block letter script used for Biblical Aramaic, identical to Hebrew block letters (כתב מרובע) |
| Nikkud | Vowel pointing marks added to consonantal text (e.g., the dots and dashes in מַלְכָּא) |
| Pe'al | The basic (G-stem) verb form in Aramaic, equivalent to Hebrew Qal |
| Construct chain | A grammatical construction where two nouns are linked (e.g., "king of kings" — מֶלֶךְ מַלְכַיָּא) |
| Targum | Aramaic translations/paraphrases of Hebrew Biblical texts |
| Spaced repetition | A learning technique that increases review intervals for well-known items and decreases them for difficult items |
| Sideloading | Installing an APK directly on an Android device without using the Play Store |
| Leitner system | A simple spaced repetition algorithm using boxes/buckets to track learning progress |

## Appendix

- Source: `docs/design/brainstorm/arameu-brainstorm.md`
- Brief: `docs/design/brief.md`

---
*Generated by Maya · 2026-04-03 · Deep Think · Claude Opus 4.6 via Claude Code*
