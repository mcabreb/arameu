# E5-S02: Build Audio Generation Pipeline

## Status
To Do

## Epic
E5 - Audio System

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Create a script that batch-generates pronunciation audio files for all vocabulary in the course. The script reads the content JSON, extracts vocabulary, and generates MP3 files using the selected TTS tool.

This pipeline is run during development, not at runtime. The generated audio files are bundled in the APK as assets. The pipeline must be reproducible — any developer can regenerate all audio from the content JSON.

## Acceptance Criteria
- [ ] Script reads vocabulary from assets/course/content.json
- [ ] Generates one MP3 file per vocabulary item
- [ ] Files named by convention: {transliteration_slug}.mp3 (lowercase, hyphens for spaces)
- [ ] Output directory structure: assets/audio/{unitN}/
- [ ] Script handles existing files (skip if already generated, or --force to regenerate)
- [ ] All Unit 1-3 vocabulary has generated audio after running
- [ ] Script logs progress and any failures

## Tasks
- **T1: Write Python generation script** — `tools/generate_audio.py`. Reads content.json, extracts vocabulary array. For each item: construct TTS input from the `script` field (Aramaic with nikkud), generate MP3 using selected TTS tool, save to `app/src/main/assets/audio/unit{unitId}/{slug}.mp3`. Handle errors per-item (log and continue, don't abort batch).
- **T2: Add slug generation** — Convert transliteration to filesystem-safe slug: lowercase, replace spaces with hyphens, remove special characters. E.g., "malka" → "malka.mp3", "bar enash" → "bar-enash.mp3".
- **T3: Batch generate for Units 1-3** — Run the script against the final content.json (from E6). Verify all files generated. Spot-check 10 files for quality.

## Technical Notes
- If edge-tts is selected: use the `edge_tts` Python library with `asyncio` for batch generation
- Add a `--unit` flag to generate audio for a specific unit only (faster iteration)
- Consider a manifest file (audio_manifest.json) that maps vocabId → audio filename for validation
- MP3 quality: 128kbps mono is sufficient for speech — keeps APK size manageable (~50KB per file, ~500 files = ~25MB)
- Script should be idempotent: running twice produces the same output

## Dependencies
- E5-S01 (Research & Select TTS Tool) -- provides the selected TTS tool and configuration.
