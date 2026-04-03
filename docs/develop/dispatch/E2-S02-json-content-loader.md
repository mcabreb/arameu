# E2-S02: Build JSON Content Loader

## Status
To Do

## Epic
E2 - Data Layer & Content Pipeline

## Priority
Critical

## Estimate
S

## Description
[ARAMEU] Load course content from a bundled JSON file into the Room database on first app launch. This is the bridge between content authoring (JSON files) and the app's data layer (Room).

Content is authored as JSON and bundled in the APK's assets. On first launch, the loader reads the JSON, parses it, and populates Room. Subsequent launches skip this step. This separation allows content authoring to proceed independently from app development.

## Acceptance Criteria
- [ ] App reads `assets/course/content.json` on first launch
- [ ] All units, lessons, exercises, and vocabulary from JSON are inserted into Room
- [ ] Subsequent app launches skip the population step (no duplicate data)
- [ ] Content loads in under 2 seconds on target device
- [ ] Malformed JSON fields are handled gracefully (skip item, log warning)
- [ ] Population runs in a background coroutine, not on the main thread

## Tasks
- **T1: Define content.json schema** — Document the JSON structure: top-level `units` array (each with nested `lessons` → `exercises`) and `vocabulary` array. Match field names to Room entity fields. Write a sample minimal JSON for testing.
- **T2: Write DatabaseCallback** — Create a RoomDatabase.Callback that overrides `onCreate`. In `onCreate`, read content.json from AssetManager, parse with kotlinx.serialization, insert all records via DAOs. Run inside a coroutine scope.
- **T3: Add first-launch guard** — Use SharedPreferences boolean `content_loaded`. Check before population. Set to true after successful insert. This handles the edge case where Room's `onCreate` only fires on database creation — if the DB exists but content failed, the flag prevents re-population.

## Technical Notes
- Use `context.assets.open("course/content.json").bufferedReader().use { it.readText() }` to read the asset
- kotlinx.serialization: define `@Serializable` data classes mirroring the JSON structure (separate from Room entities to decouple)
- Map JSON DTOs to Room entities in the loader — keep Room entities clean
- Consider batch inserts for performance: `@Insert` with List<Entity> rather than individual inserts

## Dependencies
- E2-S01 (Room Database & Entities) -- provides the database schema and DAOs for inserting content.
