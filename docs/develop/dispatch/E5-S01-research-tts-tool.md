# E5-S01: Research & Select Free TTS Tool

## Status
To Do

## Epic
E5 - Audio System

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Identify and validate a free text-to-speech tool that can produce acceptable Biblical Aramaic pronunciation. Audio is the foundation of the learning experience — Nuria is an ear-first learner, and every lesson begins with sound.

Biblical Aramaic uses the same phonological system as Biblical Hebrew. Free TTS tools with Hebrew voice support are the most viable path. The selected tool must produce clear, natural pronunciation suitable for language learning — not robotic or unintelligible.

## Acceptance Criteria
- [ ] At least 2 TTS tools tested with Hebrew/Aramaic input text
- [ ] Test words include: אָלֶף (aleph), מַלְכָּא (malka), שְׁלָם (shlam), כְּתַב (ktav), דָּנִיֵּאל (daniel)
- [ ] Best tool selected with documented rationale (quality, ease of use, licensing)
- [ ] 5 sample MP3 files generated and reviewed for pronunciation quality
- [ ] Selected tool is free for the project's scale (~200-500 audio files)
- [ ] Setup instructions documented for reproducibility

## Tasks
- **T1: Test edge-tts** — Install via `pip install edge-tts`. Test with Hebrew voice (he-IL-AvriNeural or he-IL-HilaNeural). Generate samples: `edge-tts --voice he-IL-AvriNeural --text "אָלֶף" --write-media aleph.mp3`. Evaluate clarity, naturalness, nikkud handling.
- **T2: Test Piper TTS** — Download Piper (open-source, local). Check if a Hebrew model exists. If so, test same words. If not, note as unavailable.
- **T3: Test Google Cloud TTS free tier** — Check if Google Cloud TTS has a Hebrew voice on the free tier (WaveNet or Standard). If accessible without billing, test same words. Note: may require a Google Cloud account.
- **T4: Compare and document** — Create a brief comparison: tool name, voice quality (1-5), nikkud handling, ease of batch generation, licensing. Select the winner. Save sample files for reference.

## Technical Notes
- edge-tts is the most likely winner — Microsoft's TTS is high quality, free, no API key needed, supports Hebrew
- Hebrew voices handle Aramaic text well because the phonology is nearly identical
- Nikkud (vowel pointing) is critical — the TTS needs to respect vowel marks for correct pronunciation
- Male voice (AvriNeural) may sound more "academic" for Biblical Aramaic; test both male and female
- Output format: MP3, 44.1kHz, mono — keeps file size small for APK bundling

## Dependencies
- None (this is research that can start immediately, in parallel with E1).
