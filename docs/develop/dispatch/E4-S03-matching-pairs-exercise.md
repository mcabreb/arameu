# E4-S03: Implement Matching Pairs Exercise

## Status
To Do

## Epic
E4 - Lesson Engine & Exercise Types

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Build the vocabulary matching exercise — two columns of items that the learner pairs by tapping one from each side. A tactile, visual way to reinforce vocabulary connections.

The interaction should feel like a quiet puzzle, not a timed test. Items fade softly when matched. Mismatches are gentle — a brief visual cue, then try again.

## Acceptance Criteria
- [ ] Displays two columns with 4-5 items each (e.g., transliteration left, Catalan meaning right)
- [ ] User taps one item in each column to attempt a match
- [ ] Correct pairs fade out softly with a warm glow
- [ ] Incorrect pairs briefly pulse/wobble and deselect — no harsh error state
- [ ] Selected items show a warm highlight (terracotta border)
- [ ] Exercise completes when all pairs are matched
- [ ] Score: tracked as correct on first attempt per pair
- [ ] Layout works on various phone screen widths

## Tasks
- **T1: Create MatchingExercise composable** — In `ui/exercise/MatchingExercise.kt`. Row layout with two Columns. Each column contains tappable items. State: `selectedLeft: Int?`, `selectedRight: Int?`, `matchedPairs: Set<Int>`, `incorrectFlash: Boolean`.
- **T2: Implement pair validation** — When both a left and right item are selected, check if they form a correct pair (matched by index or ID from exercise data). Correct: add to matchedPairs, animate fade. Incorrect: trigger wobble animation, clear selection after 500ms.
- **T3: Animate feedback** — Matched pairs: `AnimatedVisibility(exit = fadeOut)` with warm glow during fade. Unmatched: `Modifier.offset` with spring animation for wobble. Selected items: `border(2.dp, terracotta)`.

## Technical Notes
- Exercise data: `options` field contains JSON like `[{"left":"aleph","right":"primera lletra"},...]`
- Parse pairs in ViewModel, shuffle both columns independently so positions don't match
- Matched items should remain visible but very muted (0.2 alpha) rather than disappearing completely — maintains spatial reference
- Track "first attempt" per pair for scoring: if a pair was mismatched before being correctly matched, it doesn't count as correct for the score

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework that hosts this exercise type.
