package com.arameu.data

import com.arameu.data.dto.ContentDto
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class SeedContentTest {

    private lateinit var content: ContentDto
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        val raw = File("src/main/assets/course/content.json").readText()
        content = json.decodeFromString<ContentDto>(raw)
    }

    @Test
    fun `seed content has 6 units`() {
        assertEquals(6, content.units.size)
    }

    @Test
    fun `each unit has 4 to 7 lessons`() {
        for (unit in content.units) {
            assertTrue(
                "Unit ${unit.id} (${unit.titleCa}) has ${unit.lessons.size} lessons, expected 4-7",
                unit.lessons.size in 4..7
            )
        }
    }

    @Test
    fun `total lessons is 35`() {
        val total = content.units.sumOf { it.lessons.size }
        assertEquals("Total lessons", 35, total)
    }

    @Test
    fun `each lesson has at least 2 exercise types`() {
        for (unit in content.units) {
            for (lesson in unit.lessons) {
                val types = lesson.exercises.map { it.type }.toSet()
                assertTrue(
                    "Lesson ${lesson.id} (${lesson.titleCa}) has only ${types.size} type(s): $types",
                    types.size >= 2
                )
            }
        }
    }

    @Test
    fun `all MVP exercise types appear across the content`() {
        val mvpTypes = setOf("multiple_choice", "matching", "type_transliteration")
        val allTypes = content.units.flatMap { u -> u.lessons.flatMap { l -> l.exercises.map { it.type } } }.toSet()
        for (t in mvpTypes) {
            assertTrue("Exercise type $t not found in content", allTypes.contains(t))
        }
    }

    @Test
    fun `each lesson has exercises in all phases`() {
        val phases = setOf("intro", "guided", "mixed")
        for (unit in content.units) {
            for (lesson in unit.lessons) {
                val lessonPhases = lesson.exercises.map { it.phase }.toSet()
                // Review lessons may only have mixed phase
                if (lesson.titleCa.startsWith("Repàs") || lesson.titleCa.contains("complet") || lesson.titleCa.contains("paraules") || lesson.titleCa.contains("Review") || lesson.titleCa.contains("Daniel")) {
                    assertTrue(
                        "Review lesson ${lesson.id} should have mixed phase",
                        lessonPhases.contains("mixed") || lessonPhases.containsAll(phases)
                    )
                } else {
                    for (p in phases) {
                        assertTrue(
                            "Lesson ${lesson.id} (${lesson.titleCa}) missing phase $p, has: $lessonPhases",
                            lessonPhases.contains(p)
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `vocabulary entries exist for all units`() {
        val vocabUnitIds = content.vocabulary.map { it.unitId }.toSet()
        for (unit in content.units) {
            assertTrue(
                "No vocabulary for unit ${unit.id}",
                vocabUnitIds.contains(unit.id)
            )
        }
    }

    @Test
    fun `multiple choice exercises have 4 options`() {
        for (unit in content.units) {
            for (lesson in unit.lessons) {
                for (ex in lesson.exercises.filter { it.type == "multiple_choice" }) {
                    assertEquals(
                        "Exercise ${ex.id} should have 4 options, has ${ex.options?.size}",
                        4,
                        ex.options?.size
                    )
                }
            }
        }
    }

    @Test
    fun `matching exercises have 3 to 6 pair items`() {
        for (unit in content.units) {
            for (lesson in unit.lessons) {
                for (ex in lesson.exercises.filter { it.type == "matching" }) {
                    val size = ex.options?.size ?: 0
                    assertTrue(
                        "Exercise ${ex.id} matching should have 3-6 pairs, has $size",
                        size in 3..6
                    )
                }
            }
        }
    }

    @Test
    fun `all exercise IDs are unique`() {
        val allIds = content.units.flatMap { u -> u.lessons.flatMap { l -> l.exercises.map { it.id } } }
        assertEquals("Duplicate exercise IDs found", allIds.size, allIds.toSet().size)
    }

    @Test
    fun `all vocabulary IDs are unique`() {
        val ids = content.vocabulary.map { it.id }
        assertEquals("Duplicate vocabulary IDs found", ids.size, ids.toSet().size)
    }
}
