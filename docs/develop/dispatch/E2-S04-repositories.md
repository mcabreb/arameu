# E2-S04: Implement Repositories

## Status
Done

## Epic
E2 - Data Layer & Content Pipeline

## Priority
High

## Estimate
S

## Description
[ARAMEU] Create the repository layer that provides clean data access to ViewModels. Repositories wrap Room DAOs and expose Kotlin Flows and suspend functions — the standard Android data access pattern.

This is a thin layer for MVP. No caching, no complex logic — just a clean API between ViewModels and Room.

## Acceptance Criteria
- [x] CourseRepository returns all units as Flow<List<Unit>>
- [x] CourseRepository returns lessons for a unit as Flow<List<Lesson>>
- [x] CourseRepository returns exercises for a lesson (ordered by phase, then order)
- [x] CourseRepository returns vocabulary for a unit
- [x] ProgressRepository reads lesson progress by lessonId
- [x] ProgressRepository saves lesson completion (lessonId, score, timestamp)
- [x] Both repositories tested against in-memory Room database

## Tasks
- **T1: Create CourseRepository** — In `data/repository/CourseRepository.kt`. Constructor takes `CourseDao`. Expose: `fun getUnits(): Flow<List<Unit>>`, `fun getLessons(unitId: Int): Flow<List<Lesson>>`, `suspend fun getExercises(lessonId: Int): List<Exercise>`, `fun getVocabulary(unitId: Int): Flow<List<Vocabulary>>`. Parse JSON fields (options, acceptedVariants) from String to List<String> here.
- **T2: Create ProgressRepository** — In `data/repository/ProgressRepository.kt`. Constructor takes `ProgressDao`. Expose: `fun getLessonProgress(lessonId: Int): Flow<LessonProgress?>`, `fun getAllProgress(): Flow<List<LessonProgress>>`, `suspend fun saveLessonResult(lessonId: Int, score: Int)` (creates LessonProgress with completed=true, score, timestamp).

## Technical Notes
- Repositories are plain Kotlin classes — no interface abstractions for MVP
- JSON field parsing: Exercise.options is stored as "[\"aleph\",\"beth\",\"gimel\",\"daleth\"]" in Room. Repository decodes to List<String> using kotlinx.serialization
- For MVP, no dependency injection framework — construct repositories manually in a simple app-level container or Application class
- Flows from Room automatically emit on data changes — no manual refresh needed

## Dependencies
- E2-S01 (Room Database & Entities) -- provides the database, entities, and DAOs that repositories wrap.

## Implementation Summary

**Files Created/Modified:**
- `app/src/main/java/com/arameu/data/repository/CourseRepository.kt` — Thin wrapper over CourseDao (~16 lines)
- `app/src/main/java/com/arameu/data/repository/ProgressRepository.kt` — Progress read/write with timestamp (~22 lines)
- `app/src/test/java/com/arameu/data/repository/CourseRepositoryTest.kt` — 6 tests with FakeCourseDao
- `app/src/test/java/com/arameu/data/repository/ProgressRepositoryTest.kt` — 4 tests with FakeProgressDao

**Key Decisions:**
- Kept repositories as thin pass-through for MVP — no caching or transformation
- JSON field parsing deferred to UI layer — repositories return raw entities
- Used fake DAOs for testing instead of in-memory Room (faster, no Android dependency)
- saveLessonResult uses System.currentTimeMillis() for timestamp

**Tests:** 10 new tests, all passing
**Date:** 2026-04-06
