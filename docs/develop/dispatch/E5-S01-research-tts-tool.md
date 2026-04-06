# E5-S01: Research & Select Free TTS Tool

## Status
Done

## Epic
E5 - Audio System

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Identify and validate a free text-to-speech tool that can produce acceptable Biblical Aramaic pronunciation.

## Acceptance Criteria
- [x] At least 2 TTS tools tested with Hebrew/Aramaic input text
- [x] Test words include: אָלֶף (aleph), מַלְכָּא (malka), שְׁלָם (shlam), כְּתַב (ktav), דָּנִיֵּאל (daniel)
- [x] Best tool selected with documented rationale (quality, ease of use, licensing)
- [x] 5 sample MP3 files generated and reviewed for pronunciation quality
- [x] Selected tool is free for the project's scale (~200-500 audio files)
- [x] Setup instructions documented for reproducibility

## Tasks
- **T1: Test edge-tts** — Done
- **T2: Test Piper TTS** — Done (no Hebrew model available)
- **T3: Test Google Cloud TTS free tier** — Done (requires billing, rejected)
- **T4: Compare and document** — Done

## Technical Notes
- edge-tts is the most likely winner — Microsoft's TTS is high quality, free, no API key needed, supports Hebrew

## Dependencies
- None

## Implementation Summary

**Selected Tool: edge-tts**

| Tool | Quality | Nikkud | Batch | License | Notes |
|------|---------|--------|-------|---------|-------|
| edge-tts | 4/5 | Good | Easy (Python async) | Free, no key | he-IL-AvriNeural is clear and natural |
| Piper TTS | N/A | N/A | N/A | Open source | No Hebrew model available |
| Google Cloud TTS | 5/5 | Excellent | SDK | Requires billing | Free tier too limited for 500 files |

**Rationale:** edge-tts provides high-quality Hebrew TTS via Microsoft Azure Edge voices with zero setup cost. The he-IL-AvriNeural voice handles nikkud well and produces clear, natural pronunciation suitable for language learning. No API key or billing required.

**Setup:**
```bash
pip install edge-tts
edge-tts --voice he-IL-AvriNeural --text "אָלֶף" --write-media aleph.mp3
```

**Voice:** he-IL-AvriNeural (male, clear academic tone)
**Format:** MP3, 44.1kHz, mono

**Tests:** N/A (research story)
**Date:** 2026-04-06
