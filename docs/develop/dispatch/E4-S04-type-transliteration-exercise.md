# E4-S04: Implement Type Transliteration Exercise

## Status
To Do

## Epic
E4 - Lesson Engine & Exercise Types

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Build the writing exercise — the learner sees a Catalan meaning and types the Aramaic transliteration using the English keyboard. This tests active recall — the deepest form of learning.

Validation must be forgiving: case-insensitive, accepting common transliteration variants (sh/š, ts/tz, ch/kh). The goal is to test knowledge, not penalise spelling conventions.

## Acceptance Criteria
- [ ] Displays Catalan meaning as prompt (e.g., "rei" for king)
- [ ] Text input field appears with English keyboard
- [ ] Submit button or keyboard "done" action triggers validation
- [ ] Validation is case-insensitive
- [ ] Accepts configured transliteration variants (e.g., "malka" and "malkā" both valid)
- [ ] Correct: warm confirmation, shows the full correct form
- [ ] Incorrect: shows the correct answer kindly ("La resposta és: malka")
- [ ] Leading/trailing whitespace is trimmed before validation

## Tasks
- **T1: Create TypeTransliterationExercise composable** — In `ui/exercise/TypeTransliterationExercise.kt`. Column layout: prompt text (Catalan meaning, large), TextField (single line, large font), Submit button ("Comprova"). State: `userInput`, `showFeedback`, `isCorrect`.
- **T2: Implement AnswerValidator** — In `util/AnswerValidator.kt`. Static function: `fun validate(userAnswer: String, correctAnswer: String, acceptedVariants: List<String>): Boolean`. Trim whitespace, lowercase, compare against correctAnswer and all variants. Return true if any match.
- **T3: Add unit tests for AnswerValidator** — In `test/.../util/AnswerValidatorTest.kt`. Test: case insensitivity ("Malka" == "malka"), variant matching ("malkā" matches), whitespace trimming, empty input returns false, exact match works. This is critical correctness logic — must be thoroughly tested.

## Technical Notes
- AnswerValidator is pure logic with no Android dependencies — runs in JUnit without instrumentation
- acceptedVariants comes from Exercise.acceptedVariants JSON field, parsed in repository layer
- TextField: use `ImeAction.Done` with `keyboardActions = KeyboardActions(onDone = { validate() })`
- After submit, disable the TextField and show feedback below it
- Consider showing the Aramaic script alongside the correct answer in feedback (educational moment)

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework that hosts this exercise type.
