# E10-S01: Write Unit 5 Content JSON

## Status
To Do

## Epic
E10 - Author Unit 5: Roots & Word Families

## Priority
Critical

## Estimate
L

## Description
[ARAMEU] Write the complete content for Unit 5 of Arameu: the root system. Aramaic, like all Semitic languages, builds its vocabulary from three-letter roots. Understanding roots is not an advanced topic — it is the grammar of the language itself. Unit 5 makes this visible.

Each lesson focuses on one root family and explores how the same three consonants generate a constellation of related words: the verb, the noun, the agent, the action. The learner begins to see that Aramaic words are not arbitrary — they are woven from patterns.

By the end of Unit 5, Nuria will recognise a word she has never seen, because she knows its root.

## Acceptance Criteria
- [ ] 7 lessons authored, with IDs 23-29 in the content JSON
- [ ] ~30 vocabulary items total across the unit, each with: script, transliteration, meaningCa, audioId
- [ ] Lesson 23: Root k-t-b — writing (katav, ketav, katba, safra, bet safra)
- [ ] Lesson 24: Root sh-l-m — peace/wholeness (shlem, shalom, shlemuta, meshlem)
- [ ] Lesson 25: Root q-r-' — calling/reading (qra, qeryan, qara, metqre)
- [ ] Lesson 26: Root a-m-r — saying (amar, memra, amra, itamar)
- [ ] Lesson 27: Body vocabulary (resh, yad, regel, ayna, pum, lev)
- [ ] Lesson 28: Home vocabulary (bayta, tar'a, shulḥana, arsa, nuhra, kheshukha)
- [ ] Lesson 29: Mixed review — all Unit 5 vocabulary with root pattern exercises
- [ ] Root pattern exercises present the root consonants and ask the learner to identify or complete a derived form
- [ ] Valid JSON matching the content.json schema, loadable without errors

## Tasks
- **T1: Research root families** — For each root (k-t-b, sh-l-m, q-r-', a-m-r): verify all derived forms against Rosenthal or Jastrow. Confirm Biblical Aramaic attestation for each vocabulary item. Note the semantic range of each root in the Catalan descriptions.
- **T2: Write lessons 23-26 (root lessons)** — Each root lesson follows a consistent structure: intro (the root itself spoken and displayed), guided exercises (identify derived forms, match verb to noun), mixed exercises (cross-root comparison), listen_repeat closing. At least one script_write and one listen_choose per root lesson.
- **T3: Write lessons 27-28 (thematic vocabulary)** — Body and home lessons follow the standard structure (intro, guided, mixed, listen_repeat). Mixed exercises in these lessons draw from root vocabulary (Unit 5) and letter/number vocabulary (Units 1-4).
- **T4: Write lesson 29 (review with root patterns)** — Design exercises where the root consonants (e.g., כ-ת-ב) are displayed and the learner identifies a matching derived form from multiple options. Covers all four Unit 5 roots.
- **T5: Validate JSON** — Run content loader. Confirm lessons 23-29 load, all exercise option arrays valid, no colliding IDs.

## Technical Notes
- Root exercises: use `multiple_choice` type with `promptText` presenting the root in consonant form (e.g., "כ-ת-ב") and options showing different derived words — one correct, three from other roots or non-words
- Vocabulary IDs must not collide with Units 1-4 (IDs assigned to vocabulary items must be globally unique in content.json)
- Body and home lessons (27-28) are intentionally thematic rather than root-based — they provide a contrast and a rest from the structural intensity of lessons 23-26
- Mixed exercises from lesson 25 onward should include at least one review item from Unit 4 (greetings or numbers)

## Dependencies
- E9-S01 (Write Unit 4 Content JSON) -- Unit 5 mixed exercises include review items from Unit 4; content.json must contain Units 1-4 before Unit 5 is authored to avoid ID conflicts.
