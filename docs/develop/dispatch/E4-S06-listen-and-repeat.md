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
[ARAMEU] Build the speaking exercise — the learner hears a word or phrase, sees it displayed, and practises saying it aloud. This is self-assessed: Nuria decides if she got it right.

This exercise type is the heart of the learning experience. Nuria's lore is clear: "the feeling of saying something ancient out loud for the first time" is the magic moment. Every lesson should end with her voice, not a multiple choice answer. The listen-and-repeat exercise makes this possible.

The exercise is simple by design: hear it, see it, say it, judge yourself. No speech recognition, no automated scoring. Just a woman and an ancient language, meeting through sound.

## Acceptance Criteria
- [ ] Audio plays automatically when exercise loads
- [ ] Screen displays: Aramaic script (large), transliteration below, Catalan meaning below
- [ ] Replay button available for re-listening
- [ ] Two self-assessment buttons: "Sí, l'he encertat" (Got it) and "Repeteix" (Repeat)
- [ ] "Repeteix" replays the audio and keeps the exercise active
- [ ] "Sí, l'he encertat" marks as correct and advances to next exercise
- [ ] Visual layout is spacious and calm — the focus is on the sound and the word
- [ ] Exercise type registered as "listen_repeat" in the Exercise entity type enum
- [ ] LessonViewModel handles this exercise type in scoring (always counts as correct when user advances)

## Tasks
- **T1: Create ListenRepeatExercise composable** — Centred layout on parchment background. Top: Aramaic script in Noto Sans Hebrew ≥32sp. Below: transliteration. Below: Catalan meaning. Audio plays via LaunchedEffect on compose. Replay button (subtle speaker icon or tap-on-script). Two bottom buttons: "Repeteix" (secondary style) and "Sí, l'he encertat" (primary style, terracotta).
- **T2: Wire to LessonViewModel** — Register "listen_repeat" as a valid exercise type. When user taps "Got it", report correct to ViewModel. When "Repeat", replay audio without advancing. Scoring: listen_repeat exercises always count as correct (self-assessed pass).
- **T3: Add to exercise type routing** — In ExerciseScreen/LessonScreen, route "listen_repeat" type to ListenRepeatExercise composable. Ensure it appears in lesson flow alongside other exercise types.

## Technical Notes
- Auto-play audio: LaunchedEffect(exerciseId) { audioManager.play(promptAudioId) }
- This exercise type has no "wrong" answer — it's always a pass. The value is in the repetition and the act of speaking.
- Place listen_repeat exercises at the END of lessons (in the "mixed" phase or as the final exercise) to honour the lore principle: "every lesson ends with Nuria's voice"
- Content JSON for listen_repeat exercises: type="listen_repeat", promptScript, promptAudioId, promptText (Catalan meaning), correctAnswer can be empty or the transliteration

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonViewModel and exercise routing.
- E5-S03 (In-App Audio Playback) -- provides AudioManager for audio playback.
