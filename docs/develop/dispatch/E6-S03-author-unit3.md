# E6-S03: Author Unit 3 — Final Letters & First Words (ל-ת)

## Status
To Do

## Epic
E6 - Content Authoring — Units 1-3

## Priority
High

## Estimate
M

## Description
[ARAMEU] Complete the alphabet and introduce the first real Aramaic vocabulary words. This is the milestone unit — by the end, Nuria can recognise all 22 letters and read her first words: malka (rei/king), shlam (pau/peace), ktav (escriptura/writing).

The final lesson is a celebration of the complete alphabet — a milestone exercise where all 22 letters are tested. Then the first words appear: real Aramaic vocabulary, with the three-consonant root system revealed for the first time. This is where the language begins to come alive.

## Acceptance Criteria
- [ ] 5-6 lessons covering Lamed (ל) through Tav (ת) — 11 remaining letters
- [ ] 10-15 basic vocabulary words introduced in the final 2 lessons
- [ ] Vocabulary includes: malka/rei, shlam/pau, ktav/escriptura, melekh/rei, sefer/llibre, yom/dia, leil/nit, ard/terra, shmaya/cel, bar/fill, bat/filla
- [ ] Three-consonant root concept introduced with 2-3 examples (m-l-k → malka, melekh)
- [ ] Milestone exercise in final lesson: recognise all 22 letters (mixed matching + multiple choice)
- [ ] All vocabulary has Catalan meanings, transliteration, script, and audio
- [ ] Cumulative review across all 3 units in mixed exercises
- [ ] Content JSON valid, complete, final — ready for app bundling

## Tasks
- **T1: Research letters ל-ת** — Complete the alphabet: Lamed, Mem, Nun, Samekh, Ayin, Pe, Tsade, Qoph, Resh, Shin, Tav. Special features: final forms (Mem, Nun, Pe, Tsade, Kaph), Shin/Sin distinction, Pe/Tav bgdkpt.
- **T2: Write letter lessons (3-4 lessons)** — 2-3 letters per lesson. Cumulative review from all prior units. Audio-first intros, mixed exercises, listen-repeat finales.
- **T3: Write vocabulary lessons (2 lessons)** — Introduce first words. Reveal the root system: show m-l-k root → malka (king), demonstrate the pattern. Each word: hear it, see it in script, see transliteration, learn Catalan meaning. Exercises: match words to meanings, type transliteration, listen and repeat the words.
- **T4: Write milestone exercise** — Final lesson finale: comprehensive alphabet test. Mix of matching (letter → name), multiple choice (hear letter → pick it), and type transliteration. All 22 letters covered. Feel of achievement without fanfare.
- **T5: Generate audio** — Run pipeline for all Unit 3 vocabulary (letters + words). Verify pronunciation of complete words, not just letter names.
- **T6: Assemble final content.json** — Merge Unit 1, 2, 3 content into the complete content.json. Validate all cross-references (vocabulary IDs, audioIds). Run the app and verify full data load.

## Technical Notes
- Final forms (sofit): ם ן ף ץ ך — mention that some letters change shape at end of words, but don't over-teach in Unit 3
- Three-consonant root system is THE key insight for Semitic language learning — introducing it here gives Nuria a powerful mental model for all future units
- Vocabulary selection: choose high-frequency Biblical Aramaic words that appear in Daniel/Ezra
- Milestone exercise should feel like a gentle celebration, not a high-stakes test — quiet acknowledgement per the lore

## Dependencies
- E6-S02 (Author Unit 2) -- provides letters ו-כ content and establishes cumulative review pattern.
