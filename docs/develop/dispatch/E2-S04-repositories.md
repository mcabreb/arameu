# E2-S04: Implement Repositories

## Status
To Do

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
- [ ] CourseRepository returns all units as Flow<List<Unit>>
- [ ] CourseRepository returns lessons for a unit as Flow<List<Lesson>>
- [ ] CourseRepository returns exercises for a lesson (ordered by phase, then order)
- [ ] CourseRepository returns vocabulary for a unit
- [ ] ProgressRepository reads lesson progress by lessonId
- [ ] ProgressRepository saves lesson completion (lessonId, score, timestamp)
- [ ] Both repositories tested against in-memory Room database

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
