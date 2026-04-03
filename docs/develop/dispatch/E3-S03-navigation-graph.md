# E3-S03: Implement Navigation Graph

## Status
To Do

## Epic
E3 - Course Navigation & Progress

## Priority
High

## Estimate
S

## Description
[ARAMEU] Set up single-activity Compose Navigation so all screens connect properly. The app has a simple navigation structure: welcome (first launch) → course map → lesson → back to course map.

Navigation should feel seamless and book-like. No jarring transitions, no bottom bars, no tab navigation. Just forward and back, like turning pages.

## Acceptance Criteria
- [ ] Single-activity architecture with Compose NavHost
- [ ] Routes defined: /welcome, /course, /lesson/{lessonId}
- [ ] First launch navigates to /welcome, subsequent launches to /course
- [ ] Tapping a lesson on course map navigates to /lesson/{id}
- [ ] Back button from lesson returns to course map
- [ ] No duplicate screens accumulate on the back stack
- [ ] Transitions are smooth and calm (fade or slide, not jarring)

## Tasks
- **T1: Create ArameuNavGraph** — In `navigation/ArameuNavGraph.kt`. Define composable routes: `welcome`, `course`, `lesson/{lessonId}`. Use `NavType.IntType` for lessonId argument.
- **T2: Wire MainActivity** — Set `ArameuNavGraph` as the content of `setContent {}`. Determine start destination: check SharedPreferences `first_launch` → if true, start at `welcome`; else start at `course`.
- **T3: Configure transitions** — Use `AnimatedNavHost` with `fadeIn`/`fadeOut` transitions. Keep transitions subtle (300ms). The feeling should be calm, like turning a page.

## Technical Notes
- Use `navController.navigate(route) { popUpTo("course") { inclusive = false } }` to prevent lesson screens from stacking
- For the welcome → course transition, use `popUpTo("welcome") { inclusive = true }` so back from course doesn't return to welcome
- Consider `WindowInsets` for edge-to-edge display matching the manuscript aesthetic

## Dependencies
- E1-S02 (Scaffold Android Project) -- provides the project structure and Compose Navigation dependency.
