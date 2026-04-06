# E3-S01: Build Course Map Screen

## Status
Done

## Epic
E3 - Course Navigation & Progress

## Priority
Critical

## Estimate
M

## Description
[ARAMEU] Create the main screen that shows the learner her place in the course. This is not a typical level-picker grid. It is a book — you open it and continue where you left off.

The design principle is "one thing at a time." The screen gently presents the next lesson as the primary action, with completed lessons visible as a quiet trail above. Future lessons are barely suggested. The feeling is warmth and invitation, not a checklist.

Units are soft section headers (like chapter titles in a book), not boxes to tap. The current lesson is the focal point — large, warm, inviting. Everything else recedes.

## Acceptance Criteria
- [x] Screen displays units as a vertical scroll with the current unit centered
- [x] Each unit shows a Catalan title and its lessons
- [x] The next uncompleted lesson is visually prominent — the primary call to action
- [x] Completed lessons appear as a gentle trail (muted, with a quiet check mark)
- [x] Locked future lessons are barely visible (very muted, no interaction)
- [x] Tapping the current lesson navigates to the lesson screen
- [x] Scroll position defaults to the current lesson on app launch
- [x] Visual style matches the manuscript theme: terracotta, parchment, generous whitespace

## Tasks
- **T1: Create CourseMapViewModel** — Done
- **T2: Create CourseMapScreen composable** — Done
- **T3: Design unit and lesson cards** — Done

## Technical Notes
- Use `LazyColumn` with `rememberLazyListState()` for scroll control
- Auto-scroll: use `LaunchedEffect(currentLessonIndex)` to scroll to the active item
- The "book" metaphor means vertical scroll, not horizontal swipe or grid
- Keep the screen minimal — no top bar navigation clutter, just the course flow
- Match padding to theme: ≥24dp content padding, ≥16dp between items

## Dependencies
- E2-S04 (Repositories) -- provides CourseRepository and ProgressRepository for data access.

## Implementation Summary

**Files Created/Modified:**
- `app/src/main/java/com/arameu/ui/course/CourseMapUiState.kt` — UI state: Loading/Ready with UnitWithProgress, LessonWithProgress (~25 lines)
- `app/src/main/java/com/arameu/ui/course/CourseMapViewModel.kt` — Combines units + progress into UI state with unlock logic (~75 lines)
- `app/src/main/java/com/arameu/ui/course/CourseMapScreen.kt` — LazyColumn with unit headers, lesson cards, auto-scroll (~170 lines)
- `app/src/test/java/com/arameu/ui/course/CourseMapViewModelTest.kt` — 5 tests for ViewModel state logic

**Key Decisions:**
- Unlock logic computed in ViewModel (derived, not stored) — first lesson always unlocked, rest require previous completion
- Current lesson identified as first uncompleted lesson across all units
- Card alpha: current=1.0, completed=0.7, unlocked=0.85, locked=0.3
- Current lesson card elevated with "Continua" prompt in terracotta

**Tests:** 5 new tests, all passing
**Date:** 2026-04-06
