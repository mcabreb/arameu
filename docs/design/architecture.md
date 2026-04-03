# Arameu — Technical Architecture

**Revision:** 1
**Revision Date:** 2026-04-03
**Author:** Marti Cabré + AI assistant (Forrest, Iris, Cera)
**Status:** Draft
**PRD:** docs/design/prd.md

## 1. Overview

### 1.1 Purpose

Arameu is a native Android app that teaches Biblical Aramaic through structured, progressive lessons. It is an offline-first, single-user app with no server component. All course content (text, audio) is bundled in the APK. Data persistence is local SQLite via Room.

### 1.2 Architectural Drivers

| Driver | Requirement | Impact on Design |
|--------|-------------|------------------|
| 8-day MVP deadline | G1: Ship Units 1-3 by April 11 | Minimal layers, no abstractions beyond what Compose + Room provide |
| Offline-first | NFR-R02: No network required | All content bundled as assets; Room pre-populated from JSON |
| First Android app | Business constraint | Follow standard Android architecture (MVVM); no custom frameworks |
| Single user, single device | No accounts, no sync | No auth layer, no remote data source, no migration complexity |
| Audio playback | FR-020: Bundled audio | Media3 ExoPlayer for playback; audio files in assets/ |
| Catalan UI | FR-060 | String resources in res/values-ca/ with Catalan as default |
| Hebrew keyboard input | FR-016: Script writing | Standard Android IME; no custom keyboard needed |

## 2. Technology Stack

| Layer | Technology | Rationale |
|-------|-----------|-----------|
| Language | Kotlin 2.x | Standard Android language, expressive, null-safe |
| UI | Jetpack Compose + Material 3 | Modern declarative UI, less boilerplate than XML |
| Navigation | Compose Navigation | Single-activity, type-safe routes |
| Data | Room (SQLite) | Google's standard persistence library; offline, no server |
| Audio | Media3 (ExoPlayer) | Google's standard media library; low-latency playback |
| DI | Manual / Hilt | Hilt if time permits; manual constructor injection for MVP |
| Build | Gradle (Kotlin DSL) | Standard Android build system |
| Min SDK | API 31 (Android 12) | Modern Compose support, recent device target |
| Target SDK | API 35 (Android 15) | Latest stable |

## 3. System Architecture

### 3.1 Component Overview

```
┌──────────────────────────────────────────────────┐
│                    UI Layer                       │
│  ┌────────────┐ ┌────────────┐ ┌──────────────┐  │
│  │ CourseMap   │ │ LessonPlay │ │ ReviewScreen │  │
│  │  Screen     │ │  Screen    │ │              │  │
│  └─────┬──────┘ └─────┬──────┘ └──────┬───────┘  │
│        │              │               │           │
│  ┌─────▼──────┐ ┌─────▼──────┐ ┌─────▼────────┐  │
│  │ CourseMap   │ │ Lesson     │ │ Review       │  │
│  │ ViewModel  │ │ ViewModel  │ │ ViewModel    │  │
│  └─────┬──────┘ └─────┬──────┘ └──────┬───────┘  │
└────────┼──────────────┼───────────────┼───────────┘
         │              │               │
┌────────▼──────────────▼───────────────▼───────────┐
│                 Domain Layer                       │
│  ┌──────────────┐ ┌──────────────┐ ┌───────────┐  │
│  │ CourseRepo   │ │ ProgressRepo │ │ AudioMgr  │  │
│  └──────┬───────┘ └──────┬───────┘ └─────┬─────┘  │
└─────────┼────────────────┼───────────────┼─────────┘
          │                │               │
┌─────────▼────────────────▼───────────────▼─────────┐
│                  Data Layer                         │
│  ┌──────────────┐ ┌──────────────┐ ┌────────────┐  │
│  │ Room DB      │ │ Room DAOs    │ │ Asset Mgr  │  │
│  │ (pre-pop)    │ │              │ │ (audio)    │  │
│  └──────────────┘ └──────────────┘ └────────────┘  │
└─────────────────────────────────────────────────────┘
```

**Pattern:** MVVM (Model-View-ViewModel) — standard Android architecture. ViewModels expose UI state as `StateFlow`. Screens are stateless Compose functions that observe state and emit events.

### 3.2 UI Layer

**Responsibility:** Render screens, handle user input, observe ViewModel state.

**Screens:**
- `CourseMapScreen` — Linear list of units/lessons, shows progress, current position
- `LessonScreen` — Plays through lesson phases (intro → guided → mixed → summary)
- `ExerciseScreen` — Renders exercise by type (multiple choice, matching, typing, etc.)
- `ReviewScreen` — Spaced repetition review session (Phase 2)
- `GlossaryScreen` — Searchable vocabulary list (Phase 3)
- `PassageReaderScreen` — Word-by-word Biblical passage reader (Phase 3)
- `ProgressScreen` — Dashboard with stats (Phase 2)

**Navigation routes:**
```
/course                    → CourseMapScreen
/lesson/{lessonId}         → LessonScreen
/exercise/{exerciseId}     → ExerciseScreen (sub-navigation within lesson)
/review                    → ReviewScreen
/glossary                  → GlossaryScreen
/passage/{passageId}       → PassageReaderScreen
/progress                  → ProgressScreen
```

### 3.3 ViewModel Layer

**Responsibility:** Hold UI state, coordinate between repositories, handle exercise logic.

- `CourseMapViewModel` — Loads units + lessons with progress state. Exposes `StateFlow<CourseMapUiState>`.
- `LessonViewModel` — Manages lesson flow: loads exercises, tracks current phase, validates answers, calculates score. Exposes `StateFlow<LessonUiState>`.
- `ReviewViewModel` — Selects items due for review via spaced repetition algorithm, manages review session.

**Exercise answer validation** lives in the ViewModel:
- Multiple choice: exact match on selected option ID
- Matching: track pair selections, validate on each pair attempt
- Type transliteration: case-insensitive match against accepted variants list
- Script writing: consonantal skeleton match (strip nikkud from input, compare)

### 3.4 Domain Layer (Repositories)

**Responsibility:** Abstract data access. Thin layer — no complex business logic.

- `CourseRepository` — Read-only access to units, lessons, exercises, vocabulary from Room.
- `ProgressRepository` — Read/write lesson completion, scores, spaced repetition data.
- `AudioManager` — Wraps Media3 player. Plays audio from assets by vocabulary/phrase ID.

Repositories are plain Kotlin classes (no interfaces for MVP — single implementation, no testing abstraction needed yet).

### 3.5 Data Layer

**Responsibility:** Room database, DAOs, pre-populated content, audio asset access.

**Room Database:** `ArameuDatabase`
- Pre-populated from a JSON asset file on first launch using `createFromAsset()` or a `Callback` that reads JSON and inserts.
- Content is read-only (course data). Progress tables are read-write.

**Audio Files:**
- Stored in `assets/audio/{unit}/{vocab_id}.mp3`
- Naming convention: `{transliteration_slug}.mp3` (e.g., `malka.mp3`, `shalom.mp3`)
- Loaded via `AssetManager`, played through Media3

## 4. Data Model

### 4.1 Core Entities

```
Unit
├── id: Int (PK)
├── level: String (foundations|building_blocks|sentences|scripture)
├── titleCa: String (Catalan title)
├── order: Int
└── lessons: List<Lesson>

Lesson
├── id: Int (PK)
├── unitId: Int (FK → Unit)
├── titleCa: String
├── order: Int
└── exercises: List<Exercise>

Exercise
├── id: Int (PK)
├── lessonId: Int (FK → Lesson)
├── phase: String (intro|guided|mixed|summary)
├── type: String (multiple_choice|matching|type_transliteration|listen_choose|
│                  listen_repeat|script_read|script_write)
├── order: Int
├── promptText: String? (Catalan prompt or null)
├── promptAudioId: String? (audio asset reference)
├── promptScript: String? (Aramaic script)
├── correctAnswer: String
├── acceptedVariants: String? (JSON array of accepted alternatives)
└── options: String? (JSON array for multiple choice / matching)

Vocabulary
├── id: Int (PK)
├── transliteration: String
├── script: String (Aramaic script with nikkud)
├── meaningCa: String (Catalan meaning)
├── audioId: String (reference to audio file)
├── unitId: Int (FK → Unit, first introduced)
└── grammarNote: String?

LessonProgress
├── lessonId: Int (PK, FK → Lesson)
├── completed: Boolean
├── score: Int (0-100)
├── completedAt: Long? (timestamp)

VocabularyProgress
├── vocabId: Int (PK, FK → Vocabulary)
├── nextReviewAt: Long (timestamp)
├── interval: Int (days)
├── easeFactor: Float (SM-2 algorithm)
├── correctStreak: Int
```

### 4.2 Data Flow

```
App Launch
  → Room loads Units + LessonProgress
  → CourseMapViewModel builds UI state (locked/active/completed per unit)
  → User taps lesson

Lesson Start
  → Room loads Exercises for lessonId, ordered by phase + order
  → LessonViewModel holds exercise queue, current index
  → Each exercise renders via ExerciseScreen

Answer Submitted
  → LessonViewModel validates answer against correctAnswer / acceptedVariants
  → Updates in-memory score counter
  → Advances to next exercise

Lesson Complete
  → ProgressRepository writes LessonProgress (score, completedAt)
  → ProgressRepository updates VocabularyProgress for all vocab in lesson
  → Check if unit complete → unlock next unit (derived, not stored)
```

## 5. Error Handling

### 5.1 Strategy

Minimal error surface — the app is offline, single-user, read-heavy. Use Kotlin's standard exception handling. No custom error framework.

### 5.2 Failure Modes

| Failure | Detection | Recovery |
|---------|-----------|----------|
| Audio file missing | Media3 error callback | Show exercise without audio; log warning |
| Database corruption | Room open failure | Re-create from bundled asset (loses progress — acceptable for MVP) |
| Exercise data malformed | Null/missing fields on load | Skip exercise, continue lesson |
| App killed mid-lesson | No explicit detection | Progress saved only on lesson complete; user restarts lesson |
| Hebrew keyboard not installed | Input type check | Show instruction screen: "Instal·la un teclat hebreu" |

## 6. Testing Strategy

### 6.1 Test Levels

| Level | Scope | Framework | Target |
|-------|-------|-----------|--------|
| Unit | ViewModels, answer validation | JUnit 5 + Kotlin Test | Answer validation logic: 100% |
| Integration | Room DAOs, data loading | AndroidX Test + Room in-memory | All DAOs tested |
| UI | Exercise screens, navigation | Compose UI Test | Happy paths for each exercise type |

### 6.2 Test Infrastructure

- Room in-memory database for DAO tests
- No mocks for MVP — test against real (in-memory) database
- Compose test rule for UI tests
- Test content: small fixture dataset (1 unit, 2 lessons, sample exercises)

### 6.3 MVP Testing Priority

Given the 8-day deadline, prioritise:
1. Answer validation logic (unit tests — critical for correctness)
2. Manual testing on a physical device (Nuria's phone model if available)
3. Everything else is Phase 2

## 7. Project Structure

```
arameu/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/arameu/
│   │   │   │   ├── ArameuApp.kt              # Application class
│   │   │   │   ├── MainActivity.kt            # Single activity
│   │   │   │   ├── navigation/
│   │   │   │   │   └── ArameuNavGraph.kt      # Navigation routes
│   │   │   │   ├── ui/
│   │   │   │   │   ├── course/
│   │   │   │   │   │   ├── CourseMapScreen.kt
│   │   │   │   │   │   └── CourseMapViewModel.kt
│   │   │   │   │   ├── lesson/
│   │   │   │   │   │   ├── LessonScreen.kt
│   │   │   │   │   │   └── LessonViewModel.kt
│   │   │   │   │   ├── exercise/
│   │   │   │   │   │   ├── ExerciseScreen.kt
│   │   │   │   │   │   ├── MultipleChoiceExercise.kt
│   │   │   │   │   │   ├── MatchingExercise.kt
│   │   │   │   │   │   └── TypeTransliterationExercise.kt
│   │   │   │   │   ├── review/               # Phase 2
│   │   │   │   │   ├── glossary/             # Phase 3
│   │   │   │   │   ├── passage/              # Phase 3
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       └── Type.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── ArameuDatabase.kt     # Room database
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── CourseDao.kt
│   │   │   │   │   │   └── ProgressDao.kt
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Unit.kt
│   │   │   │   │   │   ├── Lesson.kt
│   │   │   │   │   │   ├── Exercise.kt
│   │   │   │   │   │   ├── Vocabulary.kt
│   │   │   │   │   │   ├── LessonProgress.kt
│   │   │   │   │   │   └── VocabularyProgress.kt
│   │   │   │   │   └── repository/
│   │   │   │   │       ├── CourseRepository.kt
│   │   │   │   │       └── ProgressRepository.kt
│   │   │   │   ├── audio/
│   │   │   │   │   └── AudioManager.kt
│   │   │   │   └── util/
│   │   │   │       ├── AnswerValidator.kt    # Transliteration matching logic
│   │   │   │       └── ScriptValidator.kt    # Consonantal skeleton matching
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   │   └── strings.xml           # Default (Catalan)
│   │   │   │   └── values-ca/
│   │   │   │       └── strings.xml           # Catalan (explicit)
│   │   │   └── assets/
│   │   │       ├── course/
│   │   │       │   └── content.json          # Pre-populated course data
│   │   │       └── audio/
│   │   │           ├── unit1/
│   │   │           │   ├── aleph.mp3
│   │   │           │   ├── beth.mp3
│   │   │           │   └── ...
│   │   │           ├── unit2/
│   │   │           └── unit3/
│   │   └── test/
│   │       └── java/com/arameu/
│   │           ├── util/
│   │           │   ├── AnswerValidatorTest.kt
│   │           │   └── ScriptValidatorTest.kt
│   │           └── data/
│   │               └── CourseDaoTest.kt
│   └── build.gradle.kts
├── docs/
│   └── design/
│       ├── brainstorm/
│       ├── brief.md
│       ├── prd.md
│       └── architecture.md
├── build.gradle.kts                          # Root build file
├── settings.gradle.kts
└── gradle.properties
```

## 8. Conventions

### 8.1 Naming
- **Packages:** `com.arameu.{layer}.{feature}` (e.g., `com.arameu.ui.course`)
- **Screens:** `{Feature}Screen.kt` — composable function, stateless
- **ViewModels:** `{Feature}ViewModel.kt` — holds `StateFlow<{Feature}UiState>`
- **Entities:** singular nouns, data classes (e.g., `Lesson`, `Exercise`)
- **DAOs:** `{Domain}Dao.kt` (e.g., `CourseDao`, `ProgressDao`)
- **Audio files:** `{transliteration_slug}.mp3`, lowercase, hyphens for multi-word

### 8.2 Code Style
- Kotlin official style guide
- No custom linter for MVP — Android Studio defaults
- Compose functions: `@Composable` with `PascalCase` names
- State: `StateFlow` in ViewModels, `collectAsState()` in Compose

### 8.3 Commit Messages
- Conventional commits: `feat:`, `fix:`, `docs:`, `chore:`, `test:`
- Scope optional: `feat(lesson): add multiple choice exercise`

## 9. Content Authoring Strategy

### 9.1 Course Content Pipeline

Course content is authored as a JSON file (`assets/course/content.json`) and loaded into Room on first launch. This separates content authoring from code development.

**JSON structure:**
```json
{
  "units": [
    {
      "id": 1,
      "level": "foundations",
      "titleCa": "L'alfabet arameu",
      "lessons": [
        {
          "id": 1,
          "titleCa": "Les primeres lletres (א-ה)",
          "exercises": [
            {
              "type": "multiple_choice",
              "phase": "intro",
              "promptText": "Quina lletra és aquesta: א?",
              "correctAnswer": "aleph",
              "options": ["aleph", "beth", "gimel", "daleth"]
            }
          ]
        }
      ]
    }
  ],
  "vocabulary": [
    {
      "id": 1,
      "transliteration": "aleph",
      "script": "א",
      "meaningCa": "Primera lletra de l'alfabet arameu",
      "audioId": "unit1/aleph",
      "unitId": 1
    }
  ]
}
```

### 9.2 Audio Generation Pipeline

1. Compile vocabulary list per unit (transliteration + script)
2. Generate audio using free TTS tool (to be determined: edge-tts, Piper, or Google TTS free tier)
3. Export as MP3, 44.1kHz, mono
4. Place in `assets/audio/{unitN}/`
5. Reference by `audioId` in content JSON

## Appendix

- PRD: `docs/design/prd.md`
- Brief: `docs/design/brief.md`
- Brainstorm: `docs/design/brainstorm/arameu-brainstorm.md`

---
*Generated by Maya · 2026-04-03 · Deep Think · Claude Opus 4.6 via Claude Code*
