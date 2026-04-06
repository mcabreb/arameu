#!/usr/bin/env python3
"""Generate pronunciation audio for all vocabulary in content.json using edge-tts."""

import asyncio
import json
import logging
import os
import re
import sys
from pathlib import Path

logging.basicConfig(level=logging.INFO, format="%(levelname)s: %(message)s")
logger = logging.getLogger(__name__)

CONTENT_PATH = Path("app/src/main/assets/course/content.json")
AUDIO_BASE = Path("app/src/main/assets/audio")
VOICE = "he-IL-AvriNeural"


def slugify(text: str) -> str:
    """Convert text to filesystem-safe slug."""
    slug = text.lower().strip()
    slug = re.sub(r"['\u2019]", "", slug)  # remove apostrophes
    slug = re.sub(r"[^a-z0-9]+", "-", slug)
    slug = slug.strip("-")
    return slug


def load_vocabulary(content_path: Path) -> list[dict]:
    """Load vocabulary from content.json."""
    with open(content_path, "r", encoding="utf-8") as f:
        data = json.load(f)
    return data.get("vocabulary", [])


async def generate_audio(text: str, output_path: Path, voice: str = VOICE) -> bool:
    """Generate audio for a single text using edge-tts."""
    try:
        import edge_tts
        communicate = edge_tts.Communicate(text, voice)
        await communicate.save(str(output_path))
        return True
    except Exception as e:
        logger.error(f"Failed to generate audio for '{text}': {e}")
        return False


async def main(
    content_path: Path = CONTENT_PATH,
    force: bool = False,
    unit_filter: int | None = None,
) -> None:
    vocabulary = load_vocabulary(content_path)
    if unit_filter is not None:
        vocabulary = [v for v in vocabulary if v["unitId"] == unit_filter]

    logger.info(f"Processing {len(vocabulary)} vocabulary items")

    success = 0
    skipped = 0
    failed = 0

    for vocab in vocabulary:
        unit_id = vocab["unitId"]
        slug = slugify(vocab.get("transliteration", f"vocab-{vocab['id']}"))
        audio_id = vocab.get("audioId", slug)
        script = vocab.get("script", vocab.get("transliteration", ""))

        output_dir = AUDIO_BASE / f"unit{unit_id}"
        output_dir.mkdir(parents=True, exist_ok=True)
        output_path = output_dir / f"{slugify(audio_id)}.mp3"

        if output_path.exists() and not force:
            logger.info(f"  SKIP {output_path} (exists)")
            skipped += 1
            continue

        logger.info(f"  GEN  {output_path} ← '{script}'")
        if await generate_audio(script, output_path):
            success += 1
        else:
            failed += 1

    logger.info(f"Done: {success} generated, {skipped} skipped, {failed} failed")


if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(description="Generate pronunciation audio")
    parser.add_argument("--force", action="store_true", help="Regenerate existing files")
    parser.add_argument("--unit", type=int, help="Generate for a specific unit only")
    parser.add_argument("--content", type=Path, default=CONTENT_PATH, help="Path to content.json")
    args = parser.parse_args()

    asyncio.run(main(content_path=args.content, force=args.force, unit_filter=args.unit))
