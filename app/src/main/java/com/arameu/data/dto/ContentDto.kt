package com.arameu.data.dto

import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Lesson
import com.arameu.data.entity.Unit
import com.arameu.data.entity.Vocabulary
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ContentDto(
    val units: List<UnitDto>,
    val vocabulary: List<VocabularyDto>,
)

@Serializable
data class UnitDto(
    val id: Int,
    val level: String,
    val titleCa: String,
    val order: Int,
    val lessons: List<LessonDto>,
) {
    fun toEntity(): Unit = Unit(
        id = id,
        level = level,
        titleCa = titleCa,
        order = order,
    )
}

@Serializable
data class LessonDto(
    val id: Int,
    val titleCa: String,
    val order: Int,
    val exercises: List<ExerciseDto>,
) {
    fun toEntity(unitId: Int): Lesson = Lesson(
        id = id,
        unitId = unitId,
        titleCa = titleCa,
        order = order,
    )
}

@Serializable
data class ExerciseDto(
    val id: Int,
    val phase: String,
    val type: String,
    val order: Int,
    val promptText: String,
    val promptAudioId: String? = null,
    val promptScript: String? = null,
    val correctAnswer: String,
    val acceptedVariants: List<String>? = null,
    val options: List<String>? = null,
) {
    fun toEntity(lessonId: Int): Exercise = Exercise(
        id = id,
        lessonId = lessonId,
        phase = phase,
        type = type,
        order = order,
        promptText = promptText,
        promptAudioId = promptAudioId,
        promptScript = promptScript,
        correctAnswer = correctAnswer,
        acceptedVariants = acceptedVariants?.let { Json.encodeToString(it) },
        options = options?.let { Json.encodeToString(it) },
    )
}

@Serializable
data class VocabularyDto(
    val id: Int,
    val transliteration: String,
    val script: String,
    val meaningCa: String,
    val audioId: String? = null,
    val unitId: Int,
    val grammarNote: String? = null,
) {
    fun toEntity(): Vocabulary = Vocabulary(
        id = id,
        transliteration = transliteration,
        script = script,
        meaningCa = meaningCa,
        audioId = audioId,
        unitId = unitId,
        grammarNote = grammarNote,
    )
}
