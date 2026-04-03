# E6-S01: Author Unit 1 — First Letters (א-ה)

## Status
To Do

## Epic
E6 - Content Authoring — Units 1-3

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Write the complete content for the first unit of Arameu: the first five letters of the Aramaic alphabet — Aleph (א), Beth (ב), Gimel (ג), Daleth (ד), and He (ה). This is where Nuria begins her journey.

Each lesson is an encounter, not a test. The learner hears a letter spoken aloud, sees its ancient form, and begins to recognise it. The lessons follow Nuria's learning style: hear it first, see the pattern, say it aloud.

Content must be academically accurate (standard academic Biblical Aramaic pronunciation) and culturally warm (Catalan explanations that connect the letters to something tangible).

## Acceptance Criteria
- [ ] 5 lessons, one per letter: Aleph (א), Beth (ב), Gimel (ג), Daleth (ד), He (ה)
- [ ] Each lesson has: introduction (audio-first), 3-4 guided exercises, 3-4 mixed exercises, listen-and-repeat as final exercise
- [ ] Each letter entry includes: name, transliteration, pronunciation description in Catalan, Aramaic script
- [ ] Exercise types used: multiple_choice, matching, type_transliteration, listen_repeat
- [ ] Vocabulary entries created for each letter with: script, transliteration, meaningCa, audioId
- [ ] Mixed exercises in lessons 2-5 include review of previously taught letters
- [ ] All content in valid JSON matching the content.json schema
- [ ] Audio generated via TTS pipeline for all vocabulary items
- [ ] Catalan meanings are natural and descriptive (e.g., "Primera lletra de l'alfabet arameu. Silent o amb so de glotal stop.")

## Tasks
- **T1: Research the five letters** — Academic sources (Rosenthal's Grammar of Biblical Aramaic, or equivalent). For each letter: name, IPA sound value, transliteration convention, position in alphabet, any notable features (e.g., Aleph is silent/glottal stop, Beth has two forms bgdkpt).
- **T2: Write lesson JSON** — For each letter, write one lesson in content.json format. Structure: intro exercise (type=intro, promptScript=letter, promptAudioId, promptText=Catalan description), 3-4 guided exercises (multiple_choice: "Quina lletra és aquesta: א?"; matching: pair letters to names; type_transliteration: type the letter name), 3-4 mixed exercises (review + new), 1 listen_repeat as final exercise.
- **T3: Write Catalan descriptions** — Each letter needs a warm, clear Catalan description. Not academic jargon. E.g., for Aleph: "Aleph (א) — la primera lletra. No té so propi, com una pausa suau abans d'una vocal. És el silenci que obre la porta a la paraula."
- **T4: Generate audio** — Run TTS pipeline for all Unit 1 vocabulary. Verify pronunciation quality for each letter name and sound.

## Technical Notes
- Biblical Aramaic alphabet is identical to Hebrew alphabet — same 22 letters, same Unicode code points
- Bgdkpt letters (Beth, Gimel, Daleth, Kaph, Pe, Tav) have two pronunciations (hard/soft) — introduce the concept simply for Beth and Daleth here, full treatment in later units
- Aleph is a glottal stop — explain in Catalan as "pausa suau" (soft pause)
- Exercise options (distractors) should be plausible — other letters from the same unit or common confusions
- Audio for individual letters: generate both the letter name ("aleph") and the letter sound

## Dependencies
- E5-S02 (Audio Generation Pipeline) -- provides the TTS tool and script for generating pronunciation audio.
