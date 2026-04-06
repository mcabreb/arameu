# E2-S01: Define Room Database & Entities

## Status
Done

## Epic
E2 - Data Layer & Content Pipeline

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Create the local database schema that stores all course content and learner progress. This is the data foundation of the app — every lesson, exercise, vocabulary item, and progress record lives here.

The database has two concerns: read-only course content (units, lessons, exercises, vocabulary) and read-write learner data (lesson progress, vocabulary review state). Both share one Room database, pre-populated from a bundled JSON file on first launch.

## Acceptance Criteria
- [x] Room database compiles with all 6 entities: Unit, Lesson, Exercise, Vocabulary, LessonProgress, VocabularyProgress
- [x] Entity relationships correctly defined: Unit→Lesson (1:N), Lesson→Exercise (1:N), Unit→Vocabulary (1:N)
- [x] CourseDao provides queries: all units, lessons by unitId, exercises by lessonId, vocabulary by unitId
- [x] ProgressDao provides queries: lesson progress by lessonId, save lesson result, all vocabulary progress, upsert vocabulary progress
- [x] In-memory Room database test passes — insert and query round-trip works
- [x] Exercise entity supports JSON-encoded fields: options (for multiple choice), acceptedVariants (for transliteration)

## Tasks
- **T1: Create entity data classes** — In `data/entity/`: Unit.kt (@Entity, fields: id, level, titleCa, order), Lesson.kt (id, unitId FK, titleCa, order), Exercise.kt (id, lessonId FK, phase, type, order, promptText, promptAudioId, promptScript, correctAnswer, acceptedVariants, options), Vocabulary.kt (id, transliteration, script, meaningCa, audioId, unitId FK, grammarNote), LessonProgress.kt (lessonId PK, completed, score, completedAt), VocabularyProgress.kt (vocabId PK, nextReviewAt, interval, easeFactor, correctStreak).
- **T2: Create DAOs** — CourseDao.kt: @Query functions for getUnits(), getLessonsByUnit(unitId), getExercisesByLesson(lessonId), getVocabularyByUnit(unitId), getVocabularyById(id). ProgressDao.kt: getLessonProgress(lessonId), getAllLessonProgress(), saveLessonProgress(@Upsert), getVocabularyProgress(vocabId), upsertVocabularyProgress().
- **T3: Create ArameuDatabase** — @Database(entities = [...], version = 1) abstract class. Abstract DAO accessors. Singleton pattern with Room.databaseBuilder.

## Technical Notes
- Use @Upsert for progress writes — simpler than checking insert vs update
- Exercise.options and Exercise.acceptedVariants stored as JSON strings, parsed in the repository layer
- Use kotlinx.serialization for JSON field encoding/decoding
- Room auto-generates migration for version 1 — no manual migration needed
- Foreign keys: Lesson.unitId → Unit.id, Exercise.lessonId → Lesson.id, Vocabulary.unitId → Unit.id

## Dependencies
- E1-S02 (Scaffold Android Project) -- provides the project with Room dependency configured.

## Implementation Summary

**Files Created/Modified:**
- `app/src/main/java/com/arameu/data/entity/Unit.kt` — Unit entity (~12 lines)
- `app/src/main/java/com/arameu/data/entity/Lesson.kt` — Lesson entity with FK to Unit (~19 lines)
- `app/src/main/java/com/arameu/data/entity/Exercise.kt` — Exercise entity with FK to Lesson, JSON fields (~24 lines)
- `app/src/main/java/com/arameu/data/entity/Vocabulary.kt` — Vocabulary entity with FK to Unit (~19 lines)
- `app/src/main/java/com/arameu/data/entity/LessonProgress.kt` — Learner progress entity (~12 lines)
- `app/src/main/java/com/arameu/data/entity/VocabularyProgress.kt` — SRS progress entity (~13 lines)
- `app/src/main/java/com/arameu/data/dao/CourseDao.kt` — DAO for course content queries (~40 lines)
- `app/src/main/java/com/arameu/data/dao/ProgressDao.kt` — DAO for progress read/write (~24 lines)
- `app/src/main/java/com/arameu/data/ArameuDatabase.kt` — Room database with singleton + in-memory factory (~42 lines)
- `app/src/test/java/com/arameu/data/entity/EntityTest.kt` — 9 unit tests for entity structure (9 tests)

**Key Decisions:**
- Used @Upsert for progress writes as specified — simpler than insert/update logic
- Foreign keys with CASCADE delete for referential integrity
- Options and acceptedVariants stored as nullable JSON strings, parsed in repository layer
- In-memory database factory exposed as companion method for testing

**Tests:** 9 new tests, all passing
**Date:** 2026-04-06
