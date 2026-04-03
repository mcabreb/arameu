# E4-S02: Implement Multiple Choice Exercise

## Status
To Do

## Epic
E4 - Lesson Engine & Exercise Types

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Build the most common exercise type — a prompt with 4 answer options and quiet, respectful feedback.

The design follows the "quiet acknowledgement" principle. When Nuria gets an answer right, there's a gentle visual confirmation — not a celebration. When she gets it wrong, it's not a failure — the correct answer is shown kindly, and she moves on.

## Acceptance Criteria
- [ ] Displays prompt text in Catalan on parchment background
- [ ] Shows 4 answer options as tappable cards with generous spacing
- [ ] If prompt has audio, a play button is visible and audio plays on tap
- [ ] Correct answer: card warms subtly (soft terracotta tint), brief pause, advances
- [ ] Incorrect answer: selected card dims slightly, correct answer gently highlighted, brief pause, advances
- [ ] No harsh red/green colours — use warm tints consistent with the manuscript palette
- [ ] Advances to next exercise after ~1.5 second feedback pause
- [ ] Touch targets ≥48dp, text readable, generous whitespace

## Tasks
- **T1: Create MultipleChoiceExercise composable** — In `ui/exercise/MultipleChoiceExercise.kt`. Column layout: prompt text (top, large), 4 option Cards (stacked vertically, generous padding). Each card shows answer text. State: `selectedOption`, `showFeedback`, `isCorrect`.
- **T2: Implement feedback** — On tap: set selectedOption, check against correctAnswer. If correct: animate card background to warm terracotta tint. If incorrect: fade selected card slightly, highlight correct card with warm tint. Use `animateColorAsState` for smooth transitions. After 1.5s delay, call onAnswer callback.
- **T3: Wire to LessonViewModel** — Report answer (correct/incorrect) to LessonViewModel via callback. ViewModel updates score counter and advances exercise index.

## Technical Notes
- Colour feedback: correct = subtle terracotta warmth on the card surface, incorrect = card slightly muted + correct card softly highlighted. NOT traffic-light red/green.
- Use `LaunchedEffect` with `delay(1500)` for auto-advance after feedback
- Options order should be shuffled when the exercise loads (shuffle in ViewModel, not in composable)
- If the exercise has promptAudioId, show a speaker icon button next to the prompt text

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework that hosts this exercise type.
