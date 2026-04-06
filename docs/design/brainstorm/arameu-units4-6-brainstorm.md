# Arameu Units 4-6 — Brainstorm

**Revision:** 1
**Revision Date:** 2026-04-06
**Participants:** Marti Cabré, AI assistant (Cassandra, Spark, Sense, Melody)

## Problem Statement

Nuria has completed Units 1-3 (the Aramaic alphabet) and is actively engaged — repeating "shlama" after finishing all 15 lessons in one sitting. The app needs more content to sustain her momentum and move from letter recognition to actual language comprehension. The next 20 lessons should take her from knowing the alphabet to reading a real verse from the Book of Daniel.

## Scope

### In Scope
- 20 new lessons across 3 units (Units 4-6)
- ~80 new vocabulary items (words, phrases, short sentences)
- Audio generation for all new vocabulary via edge-tts pipeline
- Script writing exercises using Android Hebrew keyboard
- Listen-and-choose exercise type
- Three-consonant root system as a pedagogical framework
- Biblical milestone: Daniel 2:4 word-by-word breakdown
- Hebrew square script introduction alongside existing Syriac Estrangela

### Out of Scope
- Spaced repetition review system (Phase 3)
- Vocabulary glossary screen (Phase 3)
- Full verb conjugation paradigms
- New exercise types beyond script-write, listen-choose, and sentence-building
- Any backend or server-side features

## Users & Stakeholders

| Role | Description | Success Criteria |
|------|-------------|------------------|
| Primary learner | Nuria — has completed alphabet, ear-first learner, motivated by the feeling of speaking something ancient | Can understand and say basic Aramaic phrases; reads Daniel 2:4 aloud with understanding |
| Developer | Marti — familiar with the codebase now, Android Studio set up | Can extend content.json and add new exercise types without architecture changes |

## Validated User Insights (from Units 1-3)

These are observed facts from Nuria's first session, not assumptions:

1. **She completed all 15 lessons in one sitting** — high engagement, but this is exceptional focus. Don't design for marathon sessions; she'll also use it in 5-minute bursts.
2. **Listen-and-repeat was the magic moment** — hearing herself say "shlama" was the emotional peak. Every unit must end with voice.
3. **The manuscript aesthetic works** — no complaints about the visual design. The terracotta/parchment theme supports sustained attention.
4. **Audio volume was initially too low** — fixed at +50% volume. Monitor for the new batch.
5. **She is pattern-driven** — the root system (Unit 5) is designed specifically for her learning style.

## Content Design

### Unit 4 — First Words & Greetings (7 lessons)

From letters to language. The moment where symbols become meaning.

| Lesson | ID | Title | New Vocabulary | Exercise Types |
|--------|----|-------|----------------|----------------|
| 16 | L16 | Numbers 1-5 | khad, tren, tlata, arba, khamsha | MC, matching, type-trans, listen-repeat |
| 17 | L17 | Numbers 6-10 | shetta, shva, tmanya, tsha, asra | MC, matching, type-trans, listen-repeat |
| 18 | L18 | Greetings & Courtesy | bevakashah, ken, la, basima, bshem | MC, listen-choose, type-trans, listen-repeat |
| 19 | L19 | People | malka, malkta, bar, bat, enash, gavra, atta | MC, matching, script-write, listen-repeat |
| 20 | L20 | The Root Revealed | m-l-k → malka, malkta, malkut; root concept | MC, matching, type-trans, listen-repeat |
| 21 | L21 | Nature | shmaya, ar'a, mayya, yoma, leyla, nura, ruakh | MC, listen-choose, script-write, listen-repeat |
| 22 | L22 | Review: First Words | Cumulative review. Milestone: "shlama, ana ___" | matching, MC, type-trans, listen-repeat |

**Vocabulary count:** ~35 new words
**New exercise types introduced:** listen-and-choose (L18), script-write (L19)
**Milestone moment:** Nuria says "shlama, ana Nuria" — introducing herself in Aramaic

### Unit 5 — Roots & Word Families (7 lessons)

The "aha" unit. Nuria is a pattern learner — this is where it clicks. The three-consonant root system is the key to understanding all Semitic languages.

| Lesson | ID | Title | Root / Topic | Key Words |
|--------|----|-------|-------------|-----------|
| 23 | L23 | Root k-t-b (writing) | כ-ת-ב | ktav, katav, katba, kitba |
| 24 | L24 | Root sh-l-m (peace) | ש-ל-מ | shlama, shlem, ashlem, shulama |
| 25 | L25 | Root q-r-' (reading) | ק-ר-א | qra, qeryana, miqra |
| 26 | L26 | Root a-m-r (saying) | א-מ-ר | amar, memra, amira, mamar |
| 27 | L27 | Body & Self | — | risha, yda, leba, ayna, puma, ragla |
| 28 | L28 | Home & Objects | — | bayta, lekhma, khamra, tara, patora, kasa |
| 29 | L29 | Review: Root Families | — | Root pattern exercises, cumulative review |

**Vocabulary count:** ~30 new words
**Pedagogical approach:** Each root lesson shows the root consonants, then derives 3-4 words from it. The exercises test whether Nuria can identify which words share a root.
**Milestone moment:** Given an unfamiliar word, Nuria identifies its root — "Oh, sh-l-m, that's related to shlama!"

### Unit 6 — Simple Sentences & Daniel's Voice (6 lessons)

The payoff. From words to meaning. She reads a real verse from the Bible.

| Lesson | ID | Title | Grammar / Topic | Key Content |
|--------|----|-------|----------------|-------------|
| 30 | L30 | Verb basics: "he did" | Past tense 3ms | katav, amar, qra, azal, yehav |
| 31 | L31 | "The king wrote" | Subject + verb + object | malka katav kitba, enash amar memra |
| 32 | L32 | Questions & answers | Interrogatives | man, ma, eykha, lema, eymat |
| 33 | L33 | "of" and belonging | di (of/that), construct | malka di bavel, bayta di malka, bar enash |
| 34 | L34 | Daniel's first words | Daniel 2:4 | מַלְכָּא לְעָלְמִין חֱיִי — word-by-word |
| 35 | L35 | Review: Reading Daniel | Full passage reading | Daniel 2:4-5 with comprehension exercises |

**Vocabulary count:** ~15 new words + ~10 grammar particles
**New exercise type:** sentence-building (arrange words into correct order)
**Milestone moment:** Nuria reads Daniel 2:4 aloud — "malka, l'almin kheyi" (O king, live forever!) — and understands every word. Three thousand years between the text and her voice.

## New Exercise Types

### Script Writing (introduced Unit 4, Lesson 19)
- Prompt: transliteration displayed (e.g., "malka")
- User types in Hebrew square script using device Hebrew keyboard (מלכא)
- Validation: compare against expected Hebrew characters, case-insensitive, ignore nikkud
- First occurrence: show a brief keyboard setup guide ("To write in Aramaic, enable the Hebrew keyboard in your device settings")
- Warm feedback: correct script glows softly, incorrect shows the expected form

### Listen-and-Choose (introduced Unit 4, Lesson 18)
- Audio plays automatically
- 4 written options displayed (transliteration or script)
- User taps the word they heard
- Same warm feedback as multiple choice

### Sentence Building (introduced Unit 6, Lesson 31)
- Word cards displayed in shuffled order
- User taps cards in correct sequence to build a sentence
- Visual: cards arrange themselves into a line as tapped
- Correct order: gentle confirmation, sentence is spoken aloud
- Incorrect: card bounces back, try again

## Script Strategy

Two scripts coexist from Unit 4 onwards:

| Script | Use | Introduced |
|--------|-----|-----------|
| Syriac Estrangela (ܐ ܒ ܓ) | Display, reading exposure, visual beauty | Unit 1 (existing) |
| Hebrew Square (א ב ג) | Writing exercises, keyboard input, Biblical text | Unit 4 (new) |

Nuria sees both. The app doesn't force a choice — Estrangela appears in intros and display contexts, Hebrew square appears in writing exercises and Biblical passages. The connection between them is introduced naturally: "The same letter, two beautiful forms."

## Technical Requirements

### Content Layer
- Extend `content.json` with Units 4-6 (20 lessons, ~80 exercises each = ~160 new exercises)
- New vocabulary entries (~80 items) with audioId references
- New exercise type fields: `script_write`, `listen_choose`, `sentence_build`

### Exercise Engine
- **ScriptWriteExercise.kt** — TextField with Hebrew keyboard IME hint, validation against expected Hebrew string
- **ListenChooseExercise.kt** — Audio auto-play + 4 option cards (similar to MultipleChoice but audio-triggered)
- **SentenceBuildExercise.kt** — Shuffled word cards, tap-to-build, order validation

### Audio
- Run `tools/generate_audio.py` for all new vocabulary
- Hebrew square script words may need separate TTS input (Hebrew characters with nikkud)
- Verify volume consistency with existing Unit 1-3 audio

### Database
- No schema changes needed — Exercise entity already supports all fields
- New exercise types handled by `type` string field

## Features (MoSCoW)

### Must Have
- 20 new lessons across 3 units
- ~80 new vocabulary items with generated audio
- Script writing exercises (Hebrew keyboard)
- Listen-and-choose exercises
- Three-consonant root system lessons
- Daniel 2:4 milestone lesson
- Cumulative review across all 6 units

### Should Have
- Sentence building exercise type
- Hebrew keyboard setup guide (first script-write)
- Root visual indicator in exercise prompts

### Could Have
- Vocabulary glossary screen
- Root reference cards

### Won't Have (this time)
- Spaced repetition
- Full verb conjugation tables
- Progress dashboard
- Dark mode

## Development Phases

### Sprint 2: Content & Exercise Types
- **E8** — New exercise type composables (script-write, listen-choose, sentence-build)
- **E9** — Author Unit 4 content (7 lessons, vocabulary, audio)
- **E10** — Author Unit 5 content (7 lessons, root system, audio)
- **E11** — Author Unit 6 content (6 lessons, sentences, Daniel, audio)
- **E12** — Integration testing & polish

## Open Questions

- Should the Hebrew keyboard setup guide be a one-time overlay or a dedicated screen?
- How to handle devices without a Hebrew keyboard installed? (Suggest Gboard with Hebrew added)
- Should root consonants be highlighted visually in derived words? (e.g., **m**a**lk**a with root letters in terracotta)
- Nuria completed all 15 lessons in one sitting — should we add a gentle "take a break" suggestion after 30+ minutes?

## Next Steps

- [ ] Generate tickets: `/hive:tickets docs/design/brainstorm/arameu-units4-6-brainstorm.md`
- [ ] Write architecture addendum for new exercise types: `/hive:arch --extend`

---
*Generated by Maya · 2026-04-06 · Deep Think · Claude Opus 4.6 via Claude Code*
