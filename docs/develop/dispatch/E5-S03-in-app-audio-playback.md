# E5-S03: Implement In-App Audio Playback

## Status
To Do

## Epic
E5 - Audio System

## Priority
High

## Estimate
S

## Description
[ARAMEU] Create the AudioManager that plays bundled pronunciation audio within the app. This is the voice of the app — every lesson introduction, every listening exercise, and every learn-and-repeat moment depends on it.

Audio must be fast (< 500ms latency from tap to sound), reliable (no crashes on missing files), and respect the device's audio settings (volume, speaker/headphones).

## Acceptance Criteria
- [ ] AudioManager plays audio from assets by vocabulary/phrase ID
- [ ] Playback latency < 500ms from trigger to first audible sound
- [ ] Works correctly on device speaker and headphones
- [ ] Follows system media volume (not alarm or notification volume)
- [ ] Handles missing audio files gracefully (silent fail, log warning)
- [ ] Supports sequential playback (play one file, then another after completion)
- [ ] Can be triggered from any screen (intro, exercises, glossary)

## Tasks
- **T1: Create AudioManager class** — In `audio/AudioManager.kt`. Wraps Media3 ExoPlayer instance. Constructor takes application Context. Initialises ExoPlayer with default audio attributes (USAGE_MEDIA, CONTENT_TYPE_SPEECH). Exposes `fun play(audioId: String)` that resolves path `assets/audio/{derived-path}/{audioId}.mp3` and plays.
- **T2: Implement asset URI resolution** — Convert audioId to asset URI: `asset:///audio/{unitN}/{slug}.mp3`. ExoPlayer supports asset URIs via `MediaItem.fromUri()`. Handle the unitN derivation (either encode in audioId or look up from vocabulary).
- **T3: Handle lifecycle and cleanup** — Release ExoPlayer in `fun release()`. Call from Application or relevant lifecycle owner. Handle audio focus properly. Stop current playback when a new `play()` call arrives (don't overlap).

## Technical Notes
- Media3 ExoPlayer handles MP3 from assets natively — no custom data source needed
- Use `AudioAttributes.Builder().setUsage(USAGE_MEDIA).setContentType(CONTENT_TYPE_SPEECH)` for correct volume stream
- For sequential playback (e.g., playing a phrase word by word): use ExoPlayer's `addMediaItem` queue or callback-based chaining
- ExoPlayer is heavyweight — create one instance and reuse, don't create per-playback
- Error handling: if asset file doesn't exist, ExoPlayer will throw — catch and log, don't crash

## Dependencies
- E1-S02 (Scaffold Android Project) -- provides the project with Media3 dependency configured.
