# E8-S03: Implement Sentence Building Exercise

## Status
To Do

## Epic
E8 - New Exercise Types

## Priority
High

## Estimate
M

## Description
[ARAMEU] Build the sentence-building exercise — shuffled word cards are presented, and the learner taps them one by one to assemble a sentence in the correct order. It is an active, spatial form of grammar practice.

The exercise plays on the learner's sense of construction. Each tap moves a card from the word bank up into the sentence slot. If the order is wrong, individual cards bounce back with a gentle animation. If the order is right, the completed sentence is spoken aloud — the learner hears the whole utterance they just built.

Scoring is first-attempt: the learner's score is based on whether they got the order right without any bounced cards. Subsequent corrections are not penalised further, but they do not improve the score.

## Acceptance Criteria
- [ ] Word cards displayed in shuffled order in a word bank at the bottom of the screen
- [ ] Tapping a card moves it to the answer row at the top, appended to the right (left in RTL)
- [ ] Tapping a placed card in the answer row returns it to the word bank
- [ ] When all cards are placed, the sentence is evaluated automatically
- [ ] Correct order: gentle visual confirmation (terracotta tint on all placed cards), sentence audio plays, then advances
- [ ] Incorrect order: the card(s) in wrong positions animate back to the word bank individually
- [ ] Score records a correct answer only if the first full submission was correct (no bounced cards in session)
- [ ] Touch targets ≥48dp per card, readable at standard font size

## Tasks
- **T1: Create SentenceBuildingExercise composable** — In `ui/exercise/SentenceBuildingExercise.kt`. Layout: answer row (top, RTL direction), word bank (bottom, wrap-flow layout). State: `placedWords: List<WordCard>`, `bankWords: List<WordCard>`, `firstAttemptClean: Boolean`, `showFeedback`.
- **T2: Implement card movement and evaluation** — On bank card tap: move to `placedWords`, remove from `bankWords`. On placed card tap: return to `bankWords`. When `placedWords.size == totalWords`: compare `placedWords.map { it.id }` against `exercise.correctOrder`. If correct: set feedback colour, call `onPlayAudio(exercise.promptAudioId)`, then advance after audio duration or 2s. If incorrect: identify wrong-position cards, animate each back to bank with a spring bounce.
- **T3: Implement bounce animation** — Use `Animatable` with spring spec for the returning cards. Cards slide back to their original bank position. `firstAttemptClean` is set to false on first incorrect submission and stays false for the rest of the exercise.
- **T4: Wire scoring** — Report `isCorrect = firstAttemptClean` to LessonViewModel via `onAnswer` callback on final completion.

## Technical Notes
- Word card data model: `data class WordCard(val id: Int, val text: String)` — IDs assigned by order in the `options` array of the exercise JSON
- `correctOrder` in exercise JSON: a list of word IDs in the correct sequence, e.g. `[2, 0, 1, 3]`
- Use `FlowRow` (from `accompanist-flowlayout` or Compose Material3 experimental) for the word bank so long sentences wrap naturally
- RTL word order: the answer row should use `LayoutDirection.Rtl` since Aramaic sentence order is right-to-left
- Audio for the sentence plays only on a correct final submission — not on incorrect attempts

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework and `onPlayAudio` callback that this exercise type plugs into.
