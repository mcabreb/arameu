# E3-S01: Build Course Map Screen

## Status
To Do

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
- [ ] Screen displays units as a vertical scroll with the current unit centered
- [ ] Each unit shows a Catalan title and its lessons
- [ ] The next uncompleted lesson is visually prominent — the primary call to action
- [ ] Completed lessons appear as a gentle trail (muted, with a quiet check mark)
- [ ] Locked future lessons are barely visible (very muted, no interaction)
- [ ] Tapping the current lesson navigates to the lesson screen
- [ ] Scroll position defaults to the current lesson on app launch
- [ ] Visual style matches the manuscript theme: terracotta, parchment, generous whitespace

## Tasks
- **T1: Create CourseMapViewModel** — In `ui/course/CourseMapViewModel.kt`. Combines `CourseRepository.getUnits()` with `ProgressRepository.getAllProgress()` into `StateFlow<CourseMapUiState>`. UiState contains list of UnitWithProgress (unit + lessons + completion state per lesson). Computes currentLessonId: first lesson where completed=false.
- **T2: Create CourseMapScreen composable** — In `ui/course/CourseMapScreen.kt`. LazyColumn rendering unit sections. Current lesson rendered as a large warm card (terracotta accent, parchment surface). Completed lessons as small muted items. Locked lessons as faint text. Auto-scroll to current lesson on first composition using `LazyListState.animateScrollToItem`.
- **T3: Design unit and lesson cards** — Unit header: Catalan title in warm serif, generous top margin, like a chapter heading. Current lesson card: rounded, elevated, warm colours, lesson title + "Continua" prompt. Completed lesson: flat, muted ink colour, small check mark. Locked lesson: barely visible, no interaction.

## Technical Notes
- Use `LazyColumn` with `rememberLazyListState()` for scroll control
- Auto-scroll: use `LaunchedEffect(currentLessonIndex)` to scroll to the active item
- The "book" metaphor means vertical scroll, not horizontal swipe or grid
- Keep the screen minimal — no top bar navigation clutter, just the course flow
- Match padding to theme: ≥24dp content padding, ≥16dp between items

## Dependencies
- E2-S04 (Repositories) -- provides CourseRepository and ProgressRepository for data access.
