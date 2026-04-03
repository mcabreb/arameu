# E2-S03: Create Seed Content Structure

## Status
To Do

## Epic
E2 - Data Layer & Content Pipeline

## Priority
High

## Estimate
S

## Description
[ARAMEU] Write a valid content.json skeleton with placeholder data for 3 units so the app can be developed and tested before real Aramaic content is authored. This unblocks UI development — exercise screens need data to render.

The placeholder content should be structurally correct (right number of lessons, exercises of each type, valid field values) even if the Aramaic content is lorem ipsum. Real content comes in E6.

## Acceptance Criteria
- [ ] content.json contains 3 units with 4-6 lessons each
- [ ] Each lesson has exercises of all MVP types: multiple_choice, matching, type_transliteration
- [ ] Each lesson has exercises in all phases: intro, guided, mixed
- [ ] Vocabulary entries exist for all referenced vocabulary IDs
- [ ] App loads seed content without errors or crashes
- [ ] Exercise options arrays have 4 items each (for multiple choice)
- [ ] Matching exercises have 4-5 pair items

## Tasks
- **T1: Write placeholder content.json** — Create `assets/course/content.json` with: 3 units (each with titleCa, level="foundations", order), ~15 lessons total, ~60 exercises across all types and phases. Use placeholder Aramaic (e.g., "aleph", "beth") and Catalan meanings (e.g., "primera lletra"). Include audioId references that will be populated later.
- **T2: Validate loading** — Run the app, verify Room is populated via Android Studio Database Inspector. Check all tables have expected row counts. Verify no JSON parse errors in logcat.

## Technical Notes
- Placeholder audio references (audioId) can point to non-existent files for now — AudioManager should handle missing files gracefully
- Use realistic exercise structures: multiple_choice needs `options` as JSON array of 4 strings, matching needs pairs, type_transliteration needs `acceptedVariants`
- The seed content will be REPLACED by real content in E6, but its structure must be identical to avoid data layer changes

## Dependencies
- E2-S02 (JSON Content Loader) -- provides the loader that reads and populates from content.json.
