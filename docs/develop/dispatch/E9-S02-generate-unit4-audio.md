# E9-S02: Generate Unit 4 Audio

## Status
To Do

## Epic
E9 - Author Unit 4: First Words & Greetings

## Priority
High

## Estimate
S

## Description
[ARAMEU] Run the TTS audio generation pipeline for all Unit 4 vocabulary and verify the output. This is a short but necessary step: the content is only alive when the learner can hear it.

Unit 4 introduces greetings, numbers, and real words the learner will want to say aloud. The audio quality here matters more than in the alphabet units — these are words Nuria will carry with her.

## Acceptance Criteria
- [ ] TTS pipeline run for all ~35 Unit 4 vocabulary items
- [ ] All audio files generated without errors and saved to `assets/audio/unit4/`
- [ ] Volume and pacing consistent with Units 1-3 audio (spot-check 10 files)
- [ ] No clipping, distortion, or unexpected silence in any generated file

## Tasks
- **T1: Run audio generation** — Execute `tools/generate_audio.py --unit 4` against the completed Unit 4 content JSON (from E9-S01). Confirm all expected files appear in `app/src/main/assets/audio/unit4/`.
- **T2: Quality-check output** — Listen to at least 10 files, including: both forms of a number (ḥad, asra), the greeting (shalom), and root-derived forms (katav, ketav). Verify natural pacing and correct stress.
- **T3: Fix any issues** — If a word is mispronounced or has bad audio, adjust the TTS input (add phonetic hints, adjust script with nikkud, or use a variant form) and regenerate. Document any adjustments made to the TTS input.

## Technical Notes
- Run with `--unit 4` flag to generate only Unit 4 audio (faster iteration, avoids re-generating Units 1-3)
- Check the audio manifest after generation: all 35 vocabulary IDs should appear with a valid filename
- Volume normalisation: if any file is noticeably quieter or louder than the Units 1-3 baseline, apply a post-processing step (e.g., `ffmpeg -i input.mp3 -af loudnorm output.mp3`)

## Dependencies
- E9-S01 (Write Unit 4 Content JSON) -- provides the vocabulary list that the audio pipeline processes.
