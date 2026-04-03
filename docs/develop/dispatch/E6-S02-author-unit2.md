# E6-S02: Author Unit 2 — More Letters (ו-ל)

## Status
To Do

## Epic
E6 - Content Authoring — Units 1-3

## Priority
High

## Estimate
M

## Description
[ARAMEU] Write content for the second unit: the next six letters of the Aramaic alphabet — Vav (ו), Zayin (ז), Heth (ח), Teth (ט), Yod (י), and Kaph (כ). This unit builds on Unit 1 with cumulative review.

The learner now knows five letters. This unit introduces six more while weaving in review of the first five. Mixed practice exercises should feel natural — revisiting old friends while meeting new ones. By the end, Nuria knows 11 of 22 letters — halfway through the alphabet.

## Acceptance Criteria
- [ ] 5-6 lessons covering Vav (ו), Zayin (ז), Heth (ח), Teth (ט), Yod (י), Kaph (כ)
- [ ] Each lesson follows the same structure: audio-first intro, guided, mixed, listen-repeat finale
- [ ] Mixed practice exercises include Unit 1 letters for cumulative review
- [ ] Matching exercises pair letters from both Unit 1 and Unit 2
- [ ] Vocabulary entries for all new letters with Catalan descriptions
- [ ] Audio generated for all new vocabulary
- [ ] Content JSON valid and loads correctly when appended to content.json
- [ ] Heth (ח) and Kaph (כ) bgdkpt variation introduced simply

## Tasks
- **T1: Research letters ו-כ** — Names, sounds, transliteration, special features. Vav as consonant (v) and vowel marker (o/u). Yod as consonant (y) and vowel marker (i). Kaph hard/soft. Heth as pharyngeal fricative (explain in Catalan: "com una 'h' forta, des del fons de la gola").
- **T2: Write lesson JSON** — 5-6 lessons. Introduce 1-2 letters per lesson. Include cumulative review: matching exercises with 2-3 Unit 1 letters mixed in. Listen-repeat exercises feature new letters.
- **T3: Write Catalan descriptions** — Continue the warm, descriptive style. Connect sounds to familiar Catalan/Spanish sounds where possible.
- **T4: Generate audio** — Run pipeline for Unit 2 vocabulary.

## Technical Notes
- Vav and Yod are matres lectionis (vowel letters) — introduce this concept simply: "Vav pot ser consonant (v) o vocal (o, u)"
- Cumulative review: in mixed exercises, include 30-40% content from Unit 1
- Keep lesson count balanced: don't try to cram all 6 letters into too few lessons

## Dependencies
- E6-S01 (Author Unit 1) -- provides the first 5 letters that Unit 2 reviews, and establishes the content format.
