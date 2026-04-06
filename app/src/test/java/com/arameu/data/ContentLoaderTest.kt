package com.arameu.data

import com.arameu.data.dto.ContentDto
import com.arameu.data.dto.ExerciseDto
import com.arameu.data.dto.LessonDto
import com.arameu.data.dto.UnitDto
import com.arameu.data.dto.VocabularyDto
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentLoaderTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `ContentDto deserializes from valid JSON`() {
        val input = """
        {
          "units": [{
            "id": 1, "level": "foundations", "titleCa": "Primeres lletres", "order": 1,
            "lessons": [{
              "id": 1, "titleCa": "Aleph i Beth", "order": 1,
              "exercises": [{
                "id": 1, "phase": "intro", "type": "multiple_choice", "order": 1,
                "promptText": "Quina lletra?", "promptAudioId": null,
                "promptScript": null, "correctAnswer": "aleph",
                "acceptedVariants": ["aleph", "Aleph"],
                "options": ["aleph", "beth", "gimel", "daleth"]
              }]
            }]
          }],
          "vocabulary": [{
            "id": 1, "transliteration": "aleph", "script": "\u0710",
            "meaningCa": "primera lletra", "audioId": "vocab_01",
            "unitId": 1, "grammarNote": null
          }]
        }
        """.trimIndent()
        val content = json.decodeFromString<ContentDto>(input)
        assertEquals(1, content.units.size)
        assertEquals(1, content.units[0].lessons.size)
        assertEquals(1, content.units[0].lessons[0].exercises.size)
        assertEquals(1, content.vocabulary.size)
    }

    @Test
    fun `UnitDto maps to entity correctly`() {
        val dto = UnitDto(
            id = 1, level = "foundations", titleCa = "Test", order = 1,
            lessons = emptyList()
        )
        val entity = dto.toEntity()
        assertEquals(1, entity.id)
        assertEquals("foundations", entity.level)
        assertEquals("Test", entity.titleCa)
        assertEquals(1, entity.order)
    }

    @Test
    fun `LessonDto maps to entity with unitId`() {
        val dto = LessonDto(id = 5, titleCa = "Lesson", order = 2, exercises = emptyList())
        val entity = dto.toEntity(unitId = 3)
        assertEquals(5, entity.id)
        assertEquals(3, entity.unitId)
        assertEquals("Lesson", entity.titleCa)
        assertEquals(2, entity.order)
    }

    @Test
    fun `ExerciseDto maps options list to JSON string`() {
        val dto = ExerciseDto(
            id = 1, phase = "intro", type = "multiple_choice", order = 1,
            promptText = "Q", promptAudioId = null, promptScript = null,
            correctAnswer = "a", acceptedVariants = listOf("a", "A"),
            options = listOf("a", "b", "c", "d")
        )
        val entity = dto.toEntity(lessonId = 1)
        assertNotNull(entity.options)
        assertTrue(entity.options!!.contains("\"a\""))
        assertTrue(entity.options!!.contains("\"d\""))
        assertNotNull(entity.acceptedVariants)
        assertTrue(entity.acceptedVariants!!.contains("\"A\""))
    }

    @Test
    fun `ExerciseDto with null options maps to null`() {
        val dto = ExerciseDto(
            id = 1, phase = "guided", type = "type_transliteration", order = 1,
            promptText = "Escriu", promptAudioId = null, promptScript = null,
            correctAnswer = "beth", acceptedVariants = listOf("beth"),
            options = null
        )
        val entity = dto.toEntity(lessonId = 1)
        assertEquals(null, entity.options)
    }

    @Test
    fun `VocabularyDto maps to entity`() {
        val dto = VocabularyDto(
            id = 1, transliteration = "aleph", script = "\u0710",
            meaningCa = "primera lletra", audioId = "v01",
            unitId = 1, grammarNote = null
        )
        val entity = dto.toEntity()
        assertEquals("aleph", entity.transliteration)
        assertEquals("\u0710", entity.script)
        assertEquals(1, entity.unitId)
    }

    @Test
    fun `malformed exercise in JSON is skipped gracefully`() {
        val input = """
        {
          "units": [{
            "id": 1, "level": "foundations", "titleCa": "Test", "order": 1,
            "lessons": [{
              "id": 1, "titleCa": "L1", "order": 1,
              "exercises": [{
                "id": 1, "phase": "intro", "type": "multiple_choice", "order": 1,
                "promptText": "Q", "correctAnswer": "a",
                "acceptedVariants": null, "options": null
              }]
            }]
          }],
          "vocabulary": []
        }
        """.trimIndent()
        val content = json.decodeFromString<ContentDto>(input)
        assertEquals(1, content.units[0].lessons[0].exercises.size)
    }
}
