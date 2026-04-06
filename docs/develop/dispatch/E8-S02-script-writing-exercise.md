# E8-S02: Implement Script Writing Exercise

## Status
To Do

## Epic
E8 - New Exercise Types

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Build the script-writing exercise — the learner sees a transliteration and types the Hebrew square script by hand. This is the deepest form of recall in Arameu: not recognition, not selection, but production from memory.

The transliteration is the prompt. A text field opens with a hint to use the Hebrew keyboard. The learner types the Aramaic script characters directly. Validation is generous: case-insensitive (not relevant for script, but consistent with other exercise logic), and nikkud (diacritical vowel marks) are ignored on both sides so the learner is not penalised for omitting or including them.

When the answer is wrong, the correct form is shown plainly and kindly — not as a punishment, but as a gift. The learner sees the right answer and continues.

## Acceptance Criteria
- [ ] Displays transliteration as the prompt (large, centred, parchment background)
- [ ] TextField below prompt, keyboard type hint set to Hebrew IME
- [ ] Submission triggered by IME action (Done) and by a submit button
- [ ] Validation strips nikkud from both input and expected answer before comparing
- [ ] Correct answer: warm terracotta tint on field border, brief pause, advances
- [ ] Incorrect answer: field dims slightly, correct Hebrew form appears below the field, brief pause, advances
- [ ] On the first occurrence of this exercise type in a session, a dismissible hint is shown explaining how to switch to the Hebrew keyboard

## Tasks
- **T1: Create ScriptWritingExercise composable** — In `ui/exercise/ScriptWritingExercise.kt`. Layout: prompt text (transliteration, top), `OutlinedTextField` (centre, Hebrew IME hint), submit Button (below field). State: `inputText`, `showFeedback`, `isCorrect`, `correctionText`.
- **T2: Implement nikkud-stripping validation** — Create a utility function `stripNikkud(text: String): String` in `util/HebrewUtils.kt`. Strip Unicode range U+05B0–U+05BD and U+05BF–U+05C7 (Hebrew points and cantillation marks). Compare `stripNikkud(input.trim())` against `stripNikkud(exercise.correctAnswer)`.
- **T3: Implement feedback and correction display** — On incorrect: set `correctionText = exercise.correctAnswer`, display below the field with a label "La forma correcta és:" in a warm but muted style. Use `animateColorAsState` on the field border colour. Auto-advance after 1.5s.
- **T4: Implement first-occurrence keyboard hint** — Track a `hebrewKeyboardHintShown` boolean in LessonViewModel (reset per session). On the first ScriptWritingExercise, show an `AlertDialog` or a bottom sheet with brief instructions: "Per escriure en arameu, canvia el teclat a hebreu als ajustos del dispositiu." Dismisses on tap.

## Technical Notes
- Hebrew keyboard IME hint: set `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)` and rely on the system IME — Android cannot force-select a specific language keyboard, but the hint card guides the user
- The `correctAnswer` field in the exercise JSON stores the expected Hebrew script with nikkud (canonical form) — strip on both sides for comparison
- Unicode nikkud range to strip: U+05B0 (HEBREW POINT SHEVA) through U+05C7 (HEBREW POINT QAMATS QATAN), inclusive
- Do not auto-capitalise or auto-correct: `KeyboardOptions(autoCorrect = false, capitalization = KeyboardCapitalization.None)`
- Right-to-left layout direction must be applied to the TextField: `LocalLayoutDirection provides LayoutDirection.Rtl` in a `CompositionLocalProvider` wrapping just the input row

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework that hosts this exercise type.
