package com.arameu.ui.lesson

import com.arameu.data.entity.Exercise

sealed interface LessonUiState {
    data object Loading : LessonUiState

    data class Active(
        val currentExercise: Exercise,
        val exerciseIndex: Int,
        val totalExercises: Int,
        val correctCount: Int,
        val phase: String,
    ) : LessonUiState

    data class Complete(
        val lessonId: Int,
        val score: Int,
        val correctCount: Int,
        val totalExercises: Int,
        val introExercises: List<Exercise>,
    ) : LessonUiState
}
