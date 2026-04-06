package com.arameu.ui.course

sealed interface CourseMapUiState {
    data object Loading : CourseMapUiState

    data class Ready(
        val units: List<UnitWithProgress>,
        val currentLessonId: Int?,
    ) : CourseMapUiState
}

data class UnitWithProgress(
    val id: Int,
    val titleCa: String,
    val order: Int,
    val lessons: List<LessonWithProgress>,
)

data class LessonWithProgress(
    val id: Int,
    val titleCa: String,
    val order: Int,
    val isCompleted: Boolean,
    val isUnlocked: Boolean,
    val score: Int?,
)
