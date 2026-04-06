# E8-S01: Implement Listen-and-Choose Exercise

## Status
To Do

## Epic
E8 - New Exercise Types

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Build the listen-and-choose exercise type — audio plays automatically, and the learner taps the written word they heard. The ear leads; the eye confirms.

This is the inverse of a typical vocabulary exercise. There is no visible prompt text — only a sound. Four written options appear on screen. The learner listens, recognises, and taps. It trains the ear-to-script connection that is central to Arameu's pedagogical arc.

The feedback is the same quiet warmth as multiple choice: a soft terracotta tint on the correct card, no harsh colours, a brief pause, then onward.

## Acceptance Criteria
- [ ] Audio plays automatically on exercise load — no tap required to start
- [ ] A replay button is visible so the learner can hear the word again
- [ ] 4 written options displayed as tappable cards (same layout as MultipleChoiceExercise)
- [ ] Correct tap: card warms to soft terracotta tint, brief pause, advances
- [ ] Incorrect tap: selected card dims, correct card highlighted with warm tint, brief pause, advances
- [ ] No harsh red/green colours — warm tints only, consistent with the manuscript palette
- [ ] Advances to next exercise after ~1.5 second feedback pause

## Tasks
- **T1: Create ListenAndChooseExercise composable** — In `ui/exercise/ListenAndChooseExercise.kt`. Layout: speaker icon at top centre (large, prominent), 4 option Cards stacked vertically below. On composition, trigger `onPlayAudio` immediately via `LaunchedEffect`. State: `selectedOption`, `showFeedback`, `isCorrect`.
- **T2: Implement auto-play and replay** — Use `LaunchedEffect(exerciseId)` to fire audio on first render. Replay button (speaker icon, outlined style) calls `onPlayAudio` when tapped. Disable replay button while audio is playing to prevent overlap.
- **T3: Implement feedback** — Reuse the same `animateColorAsState` feedback pattern from MultipleChoiceExercise. After 1.5s, call `onAnswer`. Wire to LessonViewModel score counter.

## Technical Notes
- `promptText` is intentionally absent from the UI — the audio IS the prompt. Do not display it.
- `promptAudioId` in the exercise data must be non-null; validate this in LessonViewModel and skip the exercise (logging a warning) if audio is missing
- Auto-play should key on the exercise's unique ID so navigating back/forward between exercises re-triggers correctly: `LaunchedEffect(exercise.id) { onPlayAudio(exercise.promptAudioId) }`
- Options order shuffled in ViewModel on exercise load, same as MultipleChoiceExercise

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework and `onPlayAudio` callback that this exercise type plugs into.
