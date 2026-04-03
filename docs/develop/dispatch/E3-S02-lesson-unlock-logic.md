# E3-S02: Implement Lesson Unlock Logic

## Status
To Do

## Epic
E3 - Course Navigation & Progress

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Ensure the course progresses linearly — each lesson unlocks only when the previous one is completed, and each unit unlocks when all its lessons are done. The learner cannot skip ahead.

This keeps the learning path structured and prevents overwhelm. Nuria starts at lesson 1 and moves forward at her own pace.

## Acceptance Criteria
- [ ] Unit 1, Lesson 1 is unlocked by default on first launch
- [ ] Completing a lesson unlocks the next lesson in the same unit
- [ ] Completing all lessons in a unit unlocks the first lesson of the next unit
- [ ] Locked lessons are visually muted and not tappable
- [ ] Unlock state is derived from progress data (not stored separately)
- [ ] Unlock state persists correctly across app restarts

## Tasks
- **T1: Derive unlock state in ViewModel** — In CourseMapViewModel: for each lesson, `isUnlocked = (it's lesson 1 of unit 1) OR (previous lesson in sequence has completed=true)`. Previous lesson = previous lesson in same unit, or last lesson of previous unit. This is a derived computation, not stored in Room.
- **T2: Gate navigation** — In CourseMapScreen: locked lesson cards have no onClick handler. Optionally show a subtle lock icon or reduce opacity to ~0.3.

## Technical Notes
- The unlock logic is purely derived — no "unlocked" column in the database
- Ordering matters: lessons are ordered by unit.order, then lesson.order
- Edge case: if a user somehow has non-sequential completion (shouldn't happen, but defensive), derive from "all previous lessons completed"
- This can be unit-tested by mocking progress data and checking derived unlock states

## Dependencies
- E3-S01 (Course Map Screen) -- provides the screen and ViewModel where unlock logic is displayed.
