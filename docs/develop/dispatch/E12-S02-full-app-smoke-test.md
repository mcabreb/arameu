# E12-S02: Full App Smoke Test

## Status
To Do

## Epic
E12 - Integration & Polish

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Manually test the complete app — all six units, all 35 lessons, all exercise types — on a physical device. This is the final quality gate before the app is handed to Nuria.

Sprint 2 adds three new exercise types, three new units of content, and 20 new lessons. Everything must work. Every audio file must play. Every exercise type must function. Progress must persist. The Hebrew keyboard exercises must be testable.

The test is not just functional — it is experiential. Play the course as Nuria will play it. Notice what feels wrong. Fix it.

## Acceptance Criteria
- [ ] All 35 lessons (Units 1-6) are playable start to finish without crashes or freezes
- [ ] All exercise types functional: multiple_choice, matching, type_transliteration, listen_repeat, listen_choose, script_write, sentence_build
- [ ] Audio plays correctly for all vocabulary items across all six units
- [ ] Progress persists across app restarts: close mid-unit, reopen, correct lesson is unlocked
- [ ] Hebrew keyboard exercises (script_write) are testable on device with Hebrew IME installed
- [ ] Sentence-building exercises render correctly in RTL layout on device
- [ ] Visual theme consistent throughout: no unstyled screens, no layout regressions in new exercise composables
- [ ] No ANR (Application Not Responding) during any normal interaction

## Tasks
- **T1: Verify Unit 4 end-to-end** — Play all 7 Unit 4 lessons in sequence. Test each exercise type that appears: multiple_choice, matching, listen_choose, script_write, listen_repeat. Log any issues. Confirm lesson unlock progresses correctly through the unit.
- **T2: Verify Unit 5 end-to-end** — Play all 7 Unit 5 lessons. Pay particular attention to root pattern exercises (multiple_choice with root prompt) and cross-root mixed exercises. Verify audio for guttural consonants (ayin, ḥet) is audible and correct.
- **T3: Verify Unit 6 end-to-end** — Play all 6 Unit 6 lessons. Test sentence_build exercises in RTL layout. Confirm Daniel 2:4 lesson (35) plays the full verse audio in the intro and listen_repeat exercises. This lesson is the emotional climax — it must work perfectly.
- **T4: Test Hebrew keyboard flow** — Install Hebrew IME on the test device if not already present. Play a lesson containing a script_write exercise. Verify: first-occurrence hint appears, Hebrew keyboard can be activated, input is accepted, nikkud-stripping validation works (type with and without vowel marks).
- **T5: Test progress persistence** — Complete 4 lessons across two units. Force-kill the app. Reopen. Verify correct lessons are unlocked and scores are intact. Repeat after device reboot.
- **T6: Fix issues and re-test** — Address all bugs, regressions, and UX problems found in T1-T5. Re-test each fix. Do not mark this story done until a complete playthrough (T1-T3) passes cleanly.

## Technical Notes
- Hebrew IME: install "Hebrew" keyboard from Android settings before testing. Some Android OEMs require a third-party app (e.g., Google Hebrew keyboard) — verify availability on the test device
- RTL sentence-building: verify on both portrait and landscape orientations
- Logcat: run `adb logcat | grep -E "(ARAMEU|ERROR|CRASH)"` during playthroughs to catch silent errors
- Prioritise testing on a device similar to Nuria's phone — if known, match the Android version and OEM
- Daniel 2:4 lesson is lesson ID 35 — navigate directly for rapid re-testing: `adb shell am start -n com.arameu/.MainActivity --es lessonId 35`

## Dependencies
- E9-S02 (Generate Unit 4 Audio) -- Unit 4 audio must be bundled for lessons 16-22 to function.
- E10-S02 (Generate Unit 5 Audio) -- Unit 5 audio must be bundled for lessons 23-29 to function.
- E11-S02 (Generate Unit 6 Audio) -- Unit 6 audio must be bundled for lessons 30-35 to function.
- E12-S01 (Wire New Exercise Types) -- listen_choose, script_write, and sentence_build must be routed in LessonScreen before any Unit 4-6 lesson can complete.
