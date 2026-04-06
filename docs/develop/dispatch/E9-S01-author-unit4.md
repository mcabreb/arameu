# E9-S01: Write Unit 4 Content JSON

## Status
To Do

## Epic
E9 - Author Unit 4: First Words & Greetings

## Priority
Critical

## Estimate
L

## Description
[ARAMEU] Write the complete content for Unit 4 of Arameu: the learner's first real words. After three units building the alphabet and its sounds, Nuria now speaks — numbers, greetings, the people around her, the natural world, and her first encounter with a verbal root.

Unit 4 is the moment the course becomes a language. The learner hears "shalom" and knows it. They count to ten in the tongue of Daniel. They say "yom" (day) and "layla" (night) and feel something ancient becoming familiar.

All seven lessons must use the full range of exercise types including the new listen-and-choose and script-write exercises introduced in E8.

## Acceptance Criteria
- [ ] 7 lessons authored, with IDs 16-22 in the content JSON
- [ ] ~35 vocabulary items total across the unit, each with: script, transliteration, meaningCa, audioId
- [ ] Lesson 16: Numbers 1-5 (ḥad, tren, tlata, arba, ḥamsha)
- [ ] Lesson 17: Numbers 6-10 (shita, shba, tmanya, tsha, asra)
- [ ] Lesson 18: Greetings and farewells (shalom, mah shlomakh, brikhat, todah)
- [ ] Lesson 19: People (av, em, bar, brat, gavar, ittta)
- [ ] Lesson 20: Nature and time (yoma, lelyah, shemsha, mia, ara)
- [ ] Lesson 21: Root introduction — k-t-b (katav, ketav, katba, safra)
- [ ] Lesson 22: Mixed review lesson — all Unit 4 vocabulary, all exercise types
- [ ] All exercise types used across the unit: multiple_choice, matching, type_transliteration, listen_repeat, listen_choose, script_write
- [ ] Valid JSON matching the content.json schema, loadable without errors

## Tasks
- **T1: Research vocabulary** — Verify each vocabulary item against academic sources (Rosenthal's Grammar of Biblical Aramaic, Jastrow's Dictionary). Confirm: correct Aramaic script (square Hebrew script), standard transliteration, accurate Catalan gloss. Numbers, greetings, people, and nature terms must be Biblical Aramaic register (not later Aramaic dialects).
- **T2: Write lessons 16-21** — For each lesson: 1 intro exercise (audio-first), 3 guided exercises (multiple_choice or matching), 3 mixed exercises (including review of Unit 1-3 material), 1 listen_repeat as final exercise. Lessons 19-22 must include at least one listen_choose exercise each.
- **T3: Write lesson 22 (review)** — Design a varied mixed-review lesson using all six exercise types. At least one script_write exercise. Exercises draw from all Unit 4 vocabulary with some Unit 1-3 review items.
- **T4: Write Catalan descriptions** — Each vocabulary item needs a warm, natural Catalan gloss. Numbers: connect to modern cognates where possible. Greetings: note their living use (shalom is still used today). Root lesson: explain the concept simply — "La rel k-t-b dóna escriure en arameu, hebreu i àrab."
- **T5: Validate JSON** — Run the content loader (E2-S02) against the new content. Confirm all 7 lessons load without errors. Confirm all exercise option arrays have exactly 4 items for multiple_choice and listen_choose.

## Technical Notes
- Lesson IDs must be sequential and not collide with existing lessons (Units 1-3 use IDs 1-15)
- Biblical Aramaic numbers: the forms vary by gender — use masculine forms for the introductory lessons (ḥad, tren, etc.) and note the feminine forms in the Catalan description
- The root lesson (21) uses `type_transliteration` and `script_write` exercises to reinforce the root pattern k-t-b across multiple derived forms
- Audio IDs follow the existing convention: `{transliteration_slug}` — confirm slugs do not collide with existing audio IDs from Units 1-3
- Mixed exercises in lessons 17-22 should draw review items from at least two prior lessons each

## Dependencies
- E8-S01 (Listen-and-Choose Exercise) -- the content uses `listen_choose` exercise type which must be implemented before this content can be tested end-to-end.
- E8-S02 (Script Writing Exercise) -- the content uses `script_write` exercise type which must be implemented before lesson 22 can be tested.
