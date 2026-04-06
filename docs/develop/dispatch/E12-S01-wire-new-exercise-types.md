# E12-S01: Wire New Exercise Types into Lesson Engine

## Status
To Do

## Epic
E12 - Integration & Polish

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Connect the three new exercise types — listen_choose, script_write, and sentence_build — into the LessonScreen routing and LessonViewModel scoring so they participate fully in the lesson flow.

The exercise composables were built in E8. This story is the wiring: routing by type string, scoring correct/incorrect answers for each new type, and ensuring audio playback is connected to the types that need it. After this story, any lesson that contains new exercise types will run end-to-end.

## Acceptance Criteria
- [ ] LessonScreen routes `listen_choose` exercises to `ListenAndChooseExercise` composable
- [ ] LessonScreen routes `script_write` exercises to `ScriptWritingExercise` composable
- [ ] LessonScreen routes `sentence_build` exercises to `SentenceBuildingExercise` composable
- [ ] LessonViewModel scores all three new types consistently with existing types (correct/incorrect count, 0-100% final score)
- [ ] `onPlayAudio` callback is wired to `ListenAndChooseExercise` and `SentenceBuildingExercise` (both require audio)

## Tasks
- **T1: Update LessonScreen routing** — In `ui/lesson/LessonScreen.kt`, add `when` branches for `"listen_choose"`, `"script_write"`, and `"sentence_build"` in the exercise type router. Pass appropriate callbacks to each composable.
- **T2: Update LessonViewModel scoring** — Confirm `onAnswer(isCorrect: Boolean)` is the single scoring interface for all exercise types. All three new composables report via this callback — no additional scoring logic needed if the callback contract is already correct. Verify and document.
- **T3: Wire audio callbacks** — Confirm `onPlayAudio(audioId: String)` is threaded through to `ListenAndChooseExercise` (for auto-play and replay) and `SentenceBuildingExercise` (for correct-sentence playback). `ScriptWritingExercise` does not require audio.

## Technical Notes
- The exercise type strings in content.json must match the routing `when` branches exactly: `"listen_choose"`, `"script_write"`, `"sentence_build"` — verify these strings are consistent across content authoring (E9-E11) and the router
- If `AudioManager` playback is async, ensure `onPlayAudio` in LessonScreen forwards the call correctly and does not block the UI thread
- After this story, run the app with a test lesson containing all three new exercise types to confirm routing before full content is authored

## Dependencies
- E8-S01 (Listen-and-Choose Exercise) -- the composable being routed to.
- E8-S02 (Script Writing Exercise) -- the composable being routed to.
- E8-S03 (Sentence Building Exercise) -- the composable being routed to.
