# E3-S04: Save & Display Progress

## Status
To Do

## Epic
E3 - Course Navigation & Progress

## Priority
High

## Estimate
S

## Description
[ARAMEU] Persist lesson scores and show completion state on the course map. When Nuria finishes a lesson, the app remembers — her quiet trail of completed lessons grows.

Progress display is understated, matching the "respectful progression" design principle. No celebration fanfare. A gentle check mark, a softened card, the next lesson waiting.

## Acceptance Criteria
- [ ] Lesson score (0-100%) is saved to Room on lesson completion
- [ ] Completion timestamp is recorded
- [ ] Course map shows a quiet completion indicator (subtle check mark) per completed lesson
- [ ] Progress data survives app restart, app kill, and device reboot
- [ ] Score is displayed only if the user looks for it (not prominent — keep the focus on the next lesson)

## Tasks
- **T1: Save progress on lesson complete** — In LessonViewModel: when the last exercise is answered, call `progressRepository.saveLessonResult(lessonId, score)`. Score = (correctAnswers / totalExercises * 100).
- **T2: Display progress on course map** — In CourseMapScreen: completed lessons show a small muted check mark (aged ink colour). No percentage displayed by default — keep it clean. Optional: tap completed lesson to see score in a small tooltip.

## Technical Notes
- Room's Flow-based queries in ProgressDao automatically notify the CourseMapViewModel when progress changes — no manual refresh needed
- The "quiet" design means: no confetti, no sound effect, no "Great job!" popup. Just the lesson card softens to completed state and the next lesson becomes the focal point.
- Consider saving progress atomically — if the app crashes during save, the lesson should not be marked complete

## Dependencies
- E3-S01 (Course Map Screen) -- provides the UI where progress is displayed.
