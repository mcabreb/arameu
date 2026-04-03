# Arameu — Brainstorm

**Revision:** 1
**Revision Date:** 2026-04-03
**Participants:** Marti Cabré, AI assistant (Cassandra, Spark, Sense, Melody, Stinger)

## Problem Statement

There is no accessible, focused app for learning Biblical Aramaic from zero. Existing resources are academic textbooks, university courses, or scattered online materials — none offer a structured, progressive, mobile learning experience. Nuria, a Catholic woman with an interest in religious study, wants to learn Biblical Aramaic (the language of Daniel, Ezra, and the Targumim) but has no starting point that fits her learning style: short sessions, clear progression, on her phone.

## Scope

### In Scope
- Android app for learning Biblical Aramaic
- Structured course from beginner to intermediate (~20 units)
- Four skills: reading, writing, listening, speaking
- English transliteration as primary writing system
- Square Aramaic script (Hebrew block letters) for reading and writing via Hebrew keyboard
- Standard academic pronunciation
- AI-generated audio for all vocabulary and phrases
- Offline-first with bundled content
- Progress tracking
- Real Biblical passages as milestone content (Daniel 2-7, Ezra 4-7)
- Spaced repetition vocabulary review

### Out of Scope
- Other Aramaic dialects (Neo-Aramaic, Syriac, Turoyo, Western)
- iOS version
- Social features, leaderboards, rewards, achievements, gamification
- User accounts, cloud sync, server infrastructure
- In-app purchases or monetisation
- Advanced level (beyond intermediate)
- Automated speech recognition for Aramaic

## Users & Stakeholders

| Role | Description | Success Criteria |
|------|-------------|------------------|
| Primary learner | Nuria — Catholic background, zero Aramaic knowledge, Android user, motivated by religious study | Can read and understand Biblical Aramaic passages (Daniel, Ezra) at intermediate level |
| Developer | Marti — first Android app, comfortable with any programming language, M3 Pro MacBook | Can build, test, and iterate on the app independently |

## Chosen Approach

**Linear Course Path (Duolingo Model)** — a single sequential progression divided into units → lessons → exercises. Each lesson is a 5-8 minute session. Units unlock sequentially. This was chosen over a branching skill-tree model because: (a) the target user starts from absolute zero and shouldn't need to make navigation choices she lacks context for, (b) the bounded corpus of Biblical Aramaic maps naturally to a linear progression, (c) it is significantly simpler to build for a first Android app.

**Technology stack:** Kotlin + Jetpack Compose (native Android). Chosen because: single platform target, best audio/recording performance, modern declarative UI, first-class Android tooling, natural transition from existing Java environment.

### Key Advantages
- Simple, focused UX — one path, no decisions to make
- Bounded corpus (~1,500-2,000 vocabulary items) makes comprehensive coverage feasible
- Hebrew keyboard on Android enables real Aramaic script writing
- Offline-first means no server costs or infrastructure
- Native Android ensures best performance for audio playback and recording

### Key Risks

| Risk | Impact | Mitigation |
|------|--------|------------|
| Biblical Aramaic audio content scarcity | No audio → no listening/speaking skills | AI text-to-speech for initial content; replace with authentic recordings later |
| Android speech recognition doesn't support Aramaic | No automated pronunciation checking | Self-assessed listen-and-repeat model (Nuria taps "got it" or "try again") |
| Course content accuracy | Incorrect grammar or vocabulary undermines learning | Research against academic sources (Rosenthal, Johns, Greenspahn); validate with published grammars |
| First Android app learning curve | Slower development, potential architecture mistakes | Full Maya pipeline with setup guides; Kotlin + Compose has extensive documentation |
| Hebrew keyboard nikkud (vowel pointing) variability | Inconsistent script input across Android devices | Provide clear keyboard setup instructions; accept input with or without nikkud |

## Features (MoSCoW)

### Must Have
- Linear course structure (units → lessons → exercises)
- Content for all 4 levels (~20 units): Foundations, Building Blocks, Sentences, Scripture
- Multiple choice exercises (reading comprehension)
- Matching pair exercises (vocabulary)
- Type-the-answer exercises (English transliteration)
- Listen-and-choose exercises (listening)
- Script reading exercises (Aramaic script → transliteration)
- Script writing exercises (transliteration → Aramaic script via Hebrew keyboard)
- Listen-and-repeat speaking exercises (self-assessed)
- Bundled AI-generated audio for all vocabulary and phrases
- Local SQLite database for content and progress
- Progress tracking: lessons completed, scores, current position
- Lesson flow: introduction → guided practice → mixed practice → summary
- Spaced repetition review system for vocabulary
- Milestone lessons with real Biblical passage breakdown

### Should Have
- Review/practice mode (revisit completed lessons)
- Vocabulary glossary (searchable, with audio)
- Progress dashboard (units completed, streak, vocabulary count)
- Word-by-word passage reader with tap-to-reveal translation
- Nikkud (vowel pointing) display toggle for script exercises

### Could Have
- Dark mode
- Session reminders (daily notification)
- Bookmarked verses
- Export progress data
- Multiple pronunciation tradition options

### Won't Have (this time)
- Social features, leaderboards, rewards, achievements
- User accounts or cloud sync
- Other Aramaic dialects
- iOS version
- In-app purchases or monetisation
- Advanced level (beyond intermediate)

## Development Phases

### Dev Phase 1: MVP
- App skeleton: navigation, lesson player, progress storage
- Units 1-3 (Foundations: alphabet, sounds, basic nouns)
- Exercise types: multiple choice, matching, type transliteration
- Bundled audio for Units 1-3
- Basic progress tracking
- **Milestone:** Nuria can open the app and complete her first lessons

### Dev Phase 2: Core Complete
- Units 4-10 (complete Foundations + Building Blocks)
- Script reading and script writing exercises (Hebrew keyboard)
- Listen & choose, listen & repeat exercises
- Spaced repetition review system
- Progress dashboard
- **Milestone:** All exercise types working, solid learning loop established

### Dev Phase 3: Scripture Path
- Units 11-20 (Sentences + Scripture levels)
- Real Biblical passage milestone lessons (Daniel, Ezra)
- Word-by-word passage reader with tap-to-reveal
- Vocabulary glossary
- **Milestone:** Full course from zero to intermediate, Nuria reads Daniel

### Dev Phase 4: Polish
- Review mode, bookmarks, dark mode
- Daily session notifications
- Performance tuning, accessibility
- **Milestone:** Gift-ready

## Open Questions

- Which AI TTS service produces the best Biblical Aramaic pronunciation? (Google Cloud TTS, Azure, ElevenLabs — need to test)
- What is the optimal lesson length for sustained engagement? (Start at 5-8 min, adjust based on Nuria's feedback)
- Should verb paradigm tables be available as reference material outside lessons?
- How to handle the transition from transliteration-primary to script-primary as the course advances?
- Are there free, licensed Biblical Aramaic audio corpora available from academic institutions?

## Next Steps

- [ ] Write brief: `/meadow:brief docs/design/brainstorm/arameu-brainstorm.md`
- [ ] Write PRD: `/meadow:prd docs/design/brainstorm/arameu-brainstorm.md`

---
*Generated by Maya · 2026-04-03 · Deep Think · Claude Opus 4.6 via Claude Code*
