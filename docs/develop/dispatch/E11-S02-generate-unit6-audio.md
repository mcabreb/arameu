# E11-S02: Generate Unit 6 Audio

## Status
To Do

## Epic
E11 - Author Unit 6: Sentences & Daniel

## Priority
High

## Estimate
S

## Description
[ARAMEU] Run the TTS audio generation pipeline for all Unit 6 vocabulary, including the most important audio in the entire course: Daniel 2:4 spoken aloud.

The verse audio in lesson 35 will be the emotional high point of the app. It needs to sound right. Take extra care with the full-verse recording — pacing, breath, stress. If the TTS output does not feel right for the verse, try multiple TTS configurations and select the best result.

## Acceptance Criteria
- [ ] TTS pipeline run for all ~25 Unit 6 vocabulary items
- [ ] All audio files generated without errors and saved to `assets/audio/unit6/`
- [ ] Volume and pacing consistent with Units 1-5 audio (spot-check 10 files)
- [ ] No clipping, distortion, or unexpected silence in any generated file

## Tasks
- **T1: Run audio generation** — Execute `tools/generate_audio.py --unit 6` against the completed Unit 6 content JSON (from E11-S01). Confirm all expected files appear in `app/src/main/assets/audio/unit6/`.
- **T2: Quality-check sentence audio** — Listen to the sentence-level audio (lessons 30-34 full sentences). Verify natural sentence prosody — not word-by-word robotic delivery. Adjust TTS input (punctuation, pauses) if needed.
- **T3: Special review for Daniel 2:4** — Listen to the full-verse audio at least three times. Verify stress on key syllables, natural phrasing at clause boundaries, and that the pace allows the learner to follow along. If the output is acceptable but not ideal, try one alternative TTS configuration and compare.

## Technical Notes
- For Daniel 2:4 full-verse audio: generate a single audio file for the complete verse (`daniel-2-4.mp3`) in addition to the individual word files
- If the verse TTS pacing feels rushed, insert SSML pause markers (`<break time="500ms"/>`) at clause boundaries — check if the selected TTS tool supports SSML
- Run with `--unit 6` flag for faster iteration

## Dependencies
- E11-S01 (Write Unit 6 Content JSON) -- provides the vocabulary list and the Daniel 2:4 word list that the audio pipeline processes.
