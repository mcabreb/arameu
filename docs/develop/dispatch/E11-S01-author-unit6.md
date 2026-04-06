# E11-S01: Write Unit 6 Content JSON

## Status
To Do

## Epic
E11 - Author Unit 6: Sentences & Daniel

## Priority
Critical

## Estimate
L

## Description
[ARAMEU] Write the complete content for Unit 6 of Arameu: the learner's first sentences, and their first encounter with the Biblical text itself.

Units 1-5 built the foundations — the alphabet, the sounds, the vocabulary, the root system. Unit 6 puts it all together. The learner forms sentences, asks questions, expresses possession, and by the final lesson, reads the first words of Daniel chapter 2 verse 4 in their original Aramaic.

The sentence-building exercise type (E8-S03) is used here for the first time with full sentences. This is the unit where the language begins to feel real.

## Acceptance Criteria
- [ ] 6 lessons authored, with IDs 30-35 in the content JSON
- [ ] ~25 vocabulary items total across the unit, each with: script, transliteration, meaningCa, audioId
- [ ] Lesson 30: Basic verb sentences (present tense: katav, qra, amar with subject pronouns)
- [ ] Lesson 31: Questions (man, ma, aykh, emta — who, what, how, when)
- [ ] Lesson 32: Negation and answers (la, ken, la yadana, ithi)
- [ ] Lesson 33: Possession and the construct state (bar malka, bayta d-av, shem d-em)
- [ ] Lesson 34: Possessive suffixes (bayti, baytakh, bayteh — my house, your house, his house)
- [ ] Lesson 35: Daniel 2:4 — the moment (the Aramaic opening verse, parsed word by word, then read as a whole)
- [ ] At least 3 sentence-building exercises across the unit (lessons 30-34)
- [ ] Lesson 35 is structured as an encounter, not a test: intro plays the verse aloud, guided exercises parse it word by word, final exercise is listen_repeat of the full verse
- [ ] Valid JSON matching the content.json schema, loadable without errors

## Tasks
- **T1: Research and verify Daniel 2:4** — Source the verse from the BHS (Biblia Hebraica Stuttgartensia) or equivalent critical edition. Parse each Aramaic word: script, transliteration, morphological role, Catalan gloss. The text begins: "וַעֲנוֹ כשׂדאי לְמַלְכָּא אֲרָמִית..." — verify the exact form.
- **T2: Write lessons 30-34** — Each lesson: 1 intro, 3 guided exercises, 3 mixed exercises, 1 listen_repeat. Lessons 30-34 each include at least one `sentence_build` exercise. Mixed exercises draw from Units 4-5 vocabulary as well as new material.
- **T3: Write lesson 35 (Daniel 2:4)** — This lesson is special. Structure: intro exercise plays the full verse audio and displays the script, then 4-5 guided exercises parse individual words (listen_choose and multiple_choice identifying each word's meaning), then 1 sentence_build exercise assembling the whole verse, final exercise is listen_repeat. The tone is reverential and joyful — Nuria has arrived.
- **T4: Write Catalan descriptions for lesson 35** — Each word of Daniel 2:4 needs a careful Catalan explanation: not just its meaning, but why it matters. E.g., for "ארמית" (in Aramaic): "La primera vegada que la Bíblia diu explícitament que la paraula és en arameu."
- **T5: Validate JSON** — Run content loader. Confirm lessons 30-35 load, all sentence_build exercises have valid `correctOrder` arrays, no ID conflicts.

## Technical Notes
- Sentence-building exercises in lessons 30-34 should use short, grammatically simple sentences (subject + verb, or verb + object) — 3-4 words maximum until lesson 33
- Daniel 2:4 sentence-build exercise: present the verse in 2-3 shorter clause segments rather than as one 8-word exercise, for manageability
- The construct state (lesson 33) introduces the `d-` connector — note in Catalan description: "La 'd' fa de 'de' en català: el fill del rei."
- Possessive suffixes (lesson 34) are introduced as a pattern, not a full paradigm — teach -i, -akh, -eh forms only
- Lesson 35 vocabulary items are the individual words of Daniel 2:4; their `audioId` references must point to files generated in E11-S02

## Dependencies
- E10-S01 (Write Unit 5 Content JSON) -- Unit 6 mixed exercises reference vocabulary from Unit 5; content.json must be complete through Unit 5 before Unit 6 is authored.
- E8-S03 (Sentence Building Exercise) -- the content uses `sentence_build` exercise type which must be implemented before Unit 6 can be tested end-to-end.
