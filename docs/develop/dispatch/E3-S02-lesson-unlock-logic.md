# E3-S02: Implement Lesson Unlock Logic

## Status
Done

## Epic
E3 - Course Navigation & Progress

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Ensure the course progresses linearly — each lesson unlocks only when the previous one is completed, and each unit unlocks when all its lessons are done. The learner cannot skip ahead.

## Acceptance Criteria
- [x] Unit 1, Lesson 1 is unlocked by default on first launch
- [x] Completing a lesson unlocks the next lesson in the same unit
- [x] Completing all lessons in a unit unlocks the first lesson of the next unit
- [x] Locked lessons are visually muted and not tappable
- [x] Unlock state is derived from progress data (not stored separately)
- [x] Unlock state persists correctly across app restarts

## Tasks
- **T1: Derive unlock state in ViewModel** — Done (CourseMapViewModel)
- **T2: Gate navigation** — Done (CourseMapScreen: locked cards have no onClick)

## Technical Notes
- The unlock logic is purely derived — no "unlocked" column in the database
- Ordering matters: lessons are ordered by unit.order, then lesson.order

## Dependencies
- E3-S01 (Course Map Screen) -- provides the screen and ViewModel where unlock logic is displayed.

## Implementation Summary

**Files Created/Modified:**
- `app/src/test/java/com/arameu/ui/course/UnlockLogicTest.kt` — 6 dedicated unlock logic tests

**Key Decisions:**
- Unlock logic already implemented in CourseMapViewModel (E3-S01) — this story adds dedicated test coverage
- First lesson always unlocked; subsequent require previous completion; cross-unit unlock via last lesson completion

**Tests:** 6 new tests, all passing
**Date:** 2026-04-06
