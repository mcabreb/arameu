# E4-S05: Build Introduction & Summary Screens

## Status
To Do

## Epic
E4 - Lesson Engine & Exercise Types

## Priority
High

## Estimate
S

## Description
[ARAMEU] Create the bookend screens for each lesson — the introduction that opens the encounter and the summary that gently closes it.

The introduction follows the ear-first principle: audio plays automatically before any text appears. The learner hears the ancient sound first. Then the script materialises. Then the transliteration. Then the meaning. A moment of discovery, not a flashcard.

The summary is a quiet reflection: "Has après:" — here is what you encountered today. No scores, no stats on this screen. Just the words.

## Acceptance Criteria
- [ ] Introduction: audio plays automatically when the screen appears
- [ ] Introduction: after audio plays (~1-2 seconds), Aramaic script fades in (large, centred)
- [ ] Introduction: after script appears (~1 second later), transliteration and Catalan meaning fade in below
- [ ] Introduction: replay audio button visible after initial playback
- [ ] Introduction: "Continua" button appears at the bottom to proceed
- [ ] Summary: displays "Has après:" header followed by list of new vocabulary items
- [ ] Summary: each item shows script + transliteration + meaning
- [ ] Summary: "Continua" button returns to course map
- [ ] Both screens use generous whitespace and manuscript styling

## Tasks
- **T1: Create IntroScreen composable** — In `ui/exercise/IntroScreen.kt`. Centre layout. On first composition, trigger `AudioManager.play(audioId)`. Use `LaunchedEffect` with sequential delays: 0ms → play audio, 1500ms → animate script in (fadeIn), 2500ms → animate transliteration + meaning in. Replay button (speaker icon) in corner. "Continua" button at bottom, appears after all elements visible.
- **T2: Create SummaryScreen composable** — In `ui/exercise/SummaryScreen.kt`. Column layout: "Has après:" header (warm serif, large), then a list of vocabulary items (each showing: script in large Noto Sans Hebrew, transliteration below, meaning below that). "Continua" button at bottom.

## Technical Notes
- The intro reveal sequence should use `AnimatedVisibility` with staggered `enterTransition = fadeIn(tween(600))`
- Audio playback: check if the exercise has a promptAudioId. If not (shouldn't happen in intro phase), skip audio and show text immediately
- Summary vocabulary: the ViewModel should collect all "intro" phase exercises from the lesson and extract their vocabulary data
- The summary is NOT scored — it's reflective, not evaluative
- "Continua" on summary triggers: save progress → navigate back to course map

## Dependencies
- E4-S01 (Lesson Flow Controller) -- provides the LessonScreen framework and ViewModel.
- E5-S03 (In-App Audio Playback) -- provides AudioManager for playing pronunciation audio.
