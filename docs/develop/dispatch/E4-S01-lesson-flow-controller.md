# E4-S01: Build Lesson Flow Controller

## Status
To Do

## Epic
E4 - Lesson Engine & Exercise Types

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Create the engine that drives a lesson from introduction through exercises to summary, managing phases, scoring, and the rhythm of learning.

A lesson is an encounter — not a test. It follows a natural arc:
1. **Introduction** — audio plays first (ear-first learning). The learner hears the new sound before seeing the text. Then the script and transliteration appear.
2. **Guided Practice** — 3-4 easy exercises on the new material, building confidence.
3. **Mixed Practice** — 3-4 exercises mixing new and previously learned material, reinforcing connections.
4. **Speaking** — the lesson ends with the learner's voice: a listen-and-repeat exercise.
5. **Summary** — a gentle reflection: "Has après:" (You learned:) with the new items.

The pacing is calm. Transitions are smooth. Feedback is quiet.

## Acceptance Criteria
- [ ] Lesson loads exercises ordered by phase (intro → guided → mixed → speaking → summary)
- [ ] Introduction phase plays audio FIRST, then reveals text after a brief pause
- [ ] Guided and mixed practice exercises advance on user interaction
- [ ] Lesson tracks correct/incorrect answers and calculates a score (0-100%)
- [ ] Lesson ends with a listen-and-repeat exercise before the summary
- [ ] Summary screen displays new items learned
- [ ] Score is saved via ProgressRepository on completion
- [ ] Transitions between exercises are smooth (fade, not abrupt)
- [ ] The overall feeling is calm and unhurried

## Tasks
- **T1: Create LessonViewModel** — In `ui/lesson/LessonViewModel.kt`. Loads exercises for lessonId from CourseRepository, ordered by phase then order. Manages: currentExerciseIndex, currentPhase, correctCount, totalCount. Exposes `StateFlow<LessonUiState>` with: currentExercise, phase, score, isComplete.
- **T2: Create LessonScreen** — In `ui/lesson/LessonScreen.kt`. Observes LessonUiState. Routes to the appropriate exercise composable based on exercise.type. Handles the intro phase specially: plays audio first via AudioManager, then reveals text with an animation after 1-2 seconds.
- **T3: Wire completion flow** — On last exercise complete → transition to SummaryScreen → user taps "Continua" → save progress → navigate back to course map. The transition should feel like closing a chapter, not dismissing a dialog.

## Technical Notes
- Exercise phase ordering: intro exercises first, then guided, mixed, speaking (listen_repeat type), then a virtual "summary" phase
- The intro "audio-first" behaviour: LessonScreen detects phase=="intro" and triggers AudioManager.play() immediately, with promptText/promptScript hidden. After audio completes (or 2 seconds), animate text in with a fade.
- Score calculation: correctCount / totalExercises * 100, excluding intro and summary from the count
- Use `AnimatedContent` for smooth exercise transitions

## Dependencies
- E2-S04 (Repositories) -- provides CourseRepository for loading exercises and ProgressRepository for saving scores.
- E3-S03 (Navigation Graph) -- provides navigation routes for entering and exiting lessons.
