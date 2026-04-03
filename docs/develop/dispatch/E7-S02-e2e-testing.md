# E7-S02: End-to-End Testing on Device

## Status
To Do

## Epic
E7 - Integration, Polish & Delivery

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Manually test the complete app flow on a physical Android device. This is the final quality gate before the birthday gift is delivered. Every lesson, every exercise, every audio file — tested end to end.

The test must verify: the welcome screen works, all 3 units are playable, all exercise types function, audio plays for every vocabulary item, progress saves and persists, the app works offline, and Aramaic script renders correctly.

## Acceptance Criteria
- [ ] Welcome screen appears on first launch and only on first launch
- [ ] Course map displays all 3 units with correct Catalan titles
- [ ] Unit 1 is unlocked, Units 2-3 are locked
- [ ] All Unit 1 lessons are playable start to finish without crashes
- [ ] All Unit 2 lessons are playable after Unit 1 completion
- [ ] All Unit 3 lessons are playable after Unit 2 completion
- [ ] Multiple choice exercises: correct/incorrect feedback works
- [ ] Matching exercises: pair selection, feedback, and clearing work
- [ ] Type transliteration exercises: keyboard input, validation, variant acceptance work
- [ ] Listen and repeat exercises: audio plays, self-assessment buttons work
- [ ] Audio plays for every vocabulary item in every lesson introduction
- [ ] Progress saves: close app mid-course, reopen, progress is intact
- [ ] Offline: enable airplane mode, app functions fully
- [ ] Aramaic script with nikkud renders correctly (no missing glyphs, correct positioning)
- [ ] Visual theme consistent: terracotta/parchment/aged ink throughout
- [ ] No ANR (Application Not Responding) during any normal interaction

## Tasks
- **T1: Test on emulator** — Complete playthrough: welcome → Unit 1 (all lessons) → Unit 2 (all lessons) → Unit 3 (all lessons, including milestone). Log any issues.
- **T2: Test on physical device** — Install APK on a physical Android phone. Repeat full playthrough. Test audio on speaker AND headphones. Test with screen rotation. Test with large font accessibility setting.
- **T3: Test offline** — Enable airplane mode before launching app. Verify everything works. Disable airplane mode, verify no changes.
- **T4: Test progress persistence** — Complete 2 lessons, force-kill app (swipe from recent apps), reopen. Verify progress intact. Repeat after device reboot.
- **T5: Fix issues** — Address any bugs, crashes, rendering issues, or UX problems found during testing. Re-test fixes.

## Technical Notes
- Prioritise testing on a device similar to Nuria's phone (recent Android, standard screen size)
- Check logcat for any warnings or errors during playthrough
- Pay special attention to Hebrew keyboard availability and Aramaic script rendering — these vary across Android OEMs
- Test with both dark and light system themes to ensure the custom theme overrides correctly

## Dependencies
- E3-S04 (Save & Display Progress) -- progress tracking must work.
- E4-S05 (Intro & Summary Screens) -- all exercise screens must be implemented.
- E4-S06 (Listen & Repeat) -- the speaking exercise must work.
- E5-S03 (In-App Audio Playback) -- audio must play.
- E6-S03 (Author Unit 3) -- all content must be authored and loaded.
