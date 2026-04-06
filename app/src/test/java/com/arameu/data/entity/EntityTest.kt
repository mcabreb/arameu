package com.arameu.data.entity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

class EntityTest {

    @Test
    fun `Unit entity has required fields`() {
        val unit = Unit(id = 1, level = "foundations", titleCa = "Primeres lletres", order = 1)
        assertEquals(1, unit.id)
        assertEquals("foundations", unit.level)
        assertEquals("Primeres lletres", unit.titleCa)
        assertEquals(1, unit.order)
    }

    @Test
    fun `Lesson entity has unitId foreign key`() {
        val lesson = Lesson(id = 1, unitId = 1, titleCa = "Aleph i Beth", order = 1)
        assertEquals(1, lesson.unitId)
    }

    @Test
    fun `Exercise entity supports all required fields`() {
        val exercise = Exercise(
            id = 1,
            lessonId = 1,
            phase = "intro",
            type = "multiple_choice",
            order = 1,
            promptText = "Quina lletra es?",
            promptAudioId = "audio_01",
            promptScript = "\u0710",
            correctAnswer = "aleph",
            acceptedVariants = "[\"aleph\",\"Aleph\"]",
            options = "[\"aleph\",\"beth\",\"gimel\",\"daleth\"]"
        )
        assertEquals("intro", exercise.phase)
        assertEquals("multiple_choice", exercise.type)
        assertEquals("[\"aleph\",\"Aleph\"]", exercise.acceptedVariants)
        assertEquals("[\"aleph\",\"beth\",\"gimel\",\"daleth\"]", exercise.options)
    }

    @Test
    fun `Exercise options and acceptedVariants are JSON-encoded strings`() {
        val exercise = Exercise(
            id = 1, lessonId = 1, phase = "guided", type = "type_transliteration",
            order = 1, promptText = "Escriu", promptAudioId = null,
            promptScript = null, correctAnswer = "beth",
            acceptedVariants = "[\"beth\",\"bet\"]", options = null
        )
        assertEquals("[\"beth\",\"bet\"]", exercise.acceptedVariants)
        assertNull(exercise.options)
    }

    @Test
    fun `Vocabulary entity has required fields`() {
        val vocab = Vocabulary(
            id = 1, transliteration = "aleph", script = "\u0710",
            meaningCa = "primera lletra", audioId = "vocab_01",
            unitId = 1, grammarNote = null
        )
        assertEquals("aleph", vocab.transliteration)
        assertEquals("\u0710", vocab.script)
        assertEquals("primera lletra", vocab.meaningCa)
        assertNull(vocab.grammarNote)
    }

    @Test
    fun `LessonProgress entity tracks completion`() {
        val progress = LessonProgress(
            lessonId = 1, completed = true, score = 85,
            completedAt = 1712345678000L
        )
        assertEquals(1, progress.lessonId)
        assertEquals(true, progress.completed)
        assertEquals(85, progress.score)
    }

    @Test
    fun `VocabularyProgress entity tracks spaced repetition fields`() {
        val progress = VocabularyProgress(
            vocabId = 1, nextReviewAt = 1712345678000L,
            interval = 1, easeFactor = 2.5f, correctStreak = 3
        )
        assertEquals(1, progress.vocabId)
        assertEquals(2.5f, progress.easeFactor)
        assertEquals(3, progress.correctStreak)
    }

    @Test
    fun `LessonProgress defaults to not completed`() {
        val progress = LessonProgress(lessonId = 1)
        assertFalse(progress.completed)
        assertEquals(0, progress.score)
        assertNull(progress.completedAt)
    }

    @Test
    fun `VocabularyProgress defaults to initial SRS values`() {
        val progress = VocabularyProgress(vocabId = 1)
        assertNull(progress.nextReviewAt)
        assertEquals(0, progress.interval)
        assertEquals(2.5f, progress.easeFactor)
        assertEquals(0, progress.correctStreak)
    }
}
