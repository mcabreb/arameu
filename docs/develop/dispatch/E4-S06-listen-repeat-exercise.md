# E4-S06: Implement Listen & Repeat Exercise

## Status
To Do

## Epic
E4 - Lesson Engine & Exercise Types

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Build the speaking exercise — the learner listens to an Aramaic word or phrase, then speaks it aloud. Self-assessed: she decides if she got it right.

This is the heart of the app. Nuria's learner profile says: "What brings Nuria back is the feeling of saying something ancient out loud for the first time." Every lesson ends with this exercise. The ancient word passes from the speaker, through her ears, and out of her mouth. Connection across three thousand years.

No automated speech recognition — Nuria is the judge of her own voice.

## Acceptance Criteria
- [ ] Audio plays automatically when the exercise appears
- [ ] Aramaic script displayed large and centred
- [ ] Transliteration displayed below the script
- [ ] Catalan meaning displayed below the transliteration
- [ ] Replay button clearly visible
- [ ] Two self-assessment buttons: "Sí, l'he encertat" (Got it) and "Repeteix" (Repeat)
- [ ] "Repeteix" replays the audio and keeps the exercise active
- [ ] "Sí, l'he encertat" advances to the next exercise (counts as correct)
- [ ] The screen feels spacious and inviting — this is a moment, not a test
- [ ] Audio can be replayed unlimited times

## Tasks
- **T1: Create ListenRepeatExercise composable** — In `ui/exercise/ListenRepeatExercise.kt`. Centre layout, generous padding. Top: speaker icon (large, tappable for replay). Centre: Aramaic script (≥32sp, Noto Sans Hebrew), transliteration below, meaning below. Bottom: two buttons side by side. On composition, trigger AudioManager.play(audioId).
- **T2: Wire self-assessment** — "Repeteix" → call AudioManager.play() again, no state change. "Sí, l'he encertat" → call onAnswer(correct=true), advance. The exercise always counts as "correct" for scoring — it's self-assessed, and the act of practising is the value.

## Technical Notes
- This exercise type is simpler than the others — no validation logic, just audio + display + two buttons
- The "always correct" scoring means this exercise doesn't penalise the learner's score — it's pure practice
- Audio replay should handle rapid taps gracefully (stop current playback before restarting)
- The exercise type string in content.json is "listen_repeat"
- Consider a subtle visual cue while audio is playing (gentle pulse on the speaker icon)
- This should be the LAST exercise in every lesson (before summary) — enforced by content authoring, not code

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework.
- E5-S03 (In-App Audio Playback) -- provides AudioManager.
