# E10-S02: Generate Unit 5 Audio

## Status
To Do

## Epic
E10 - Author Unit 5: Roots & Word Families

## Priority
High

## Estimate
S

## Description
[ARAMEU] Run the TTS audio generation pipeline for all Unit 5 vocabulary. Unit 5 introduces root families, and the learner will be hearing related words in close succession — pronunciation consistency between derived forms matters here more than anywhere else in the course.

## Acceptance Criteria
- [ ] TTS pipeline run for all ~30 Unit 5 vocabulary items
- [ ] All audio files generated without errors and saved to `assets/audio/unit5/`
- [ ] Volume and pacing consistent with Units 1-4 audio (spot-check 10 files)
- [ ] No clipping, distortion, or unexpected silence in any generated file

## Tasks
- **T1: Run audio generation** — Execute `tools/generate_audio.py --unit 5` against the completed Unit 5 content JSON (from E10-S01). Confirm all expected files appear in `app/src/main/assets/audio/unit5/`.
- **T2: Quality-check root family audio** — For each root family, listen to all derived forms in sequence (e.g., katav → ketav → katba → safra). Verify the shared consonants sound consistent and the derived forms are clearly distinct. Pay special attention to pharyngeal and guttural consonants (ayin, ḥet).
- **T3: Fix any issues** — Adjust TTS input for any mispronounced or poorly paced forms, regenerate, and document adjustments.

## Technical Notes
- Run with `--unit 5` flag for faster iteration
- Root consonants pronounced in isolation (used in root pattern exercises) may need special TTS input formatting — test these separately before batch-generating
- For guttural letters (aleph, ayin, ḥet, resh), verify the TTS produces an audible sound; some TTS engines silently drop glottal stops

## Dependencies
- E10-S01 (Write Unit 5 Content JSON) -- provides the vocabulary list that the audio pipeline processes.
