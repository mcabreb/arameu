# E2-S01: Define Room Database & Entities

## Status
To Do

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
- [ ] Room database compiles with all 6 entities: Unit, Lesson, Exercise, Vocabulary, LessonProgress, VocabularyProgress
- [ ] Entity relationships correctly defined: Unit→Lesson (1:N), Lesson→Exercise (1:N), Unit→Vocabulary (1:N)
- [ ] CourseDao provides queries: all units, lessons by unitId, exercises by lessonId, vocabulary by unitId
- [ ] ProgressDao provides queries: lesson progress by lessonId, save lesson result, all vocabulary progress, upsert vocabulary progress
- [ ] In-memory Room database test passes — insert and query round-trip works
- [ ] Exercise entity supports JSON-encoded fields: options (for multiple choice), acceptedVariants (for transliteration)

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
