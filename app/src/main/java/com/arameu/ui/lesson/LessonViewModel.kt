package com.arameu.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arameu.data.entity.Exercise
import com.arameu.data.repository.CourseRepository
import com.arameu.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LessonViewModel(
    private val lessonId: Int,
    private val courseRepository: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LessonUiState>(LessonUiState.Loading)
    val uiState: StateFlow<LessonUiState> = _uiState

    private var exercises: List<Exercise> = emptyList()
    private var currentIndex = 0
    private var correctCount = 0

    init {
        viewModelScope.launch {
            exercises = courseRepository.getExercises(lessonId)
                .sortedWith(compareBy(::phaseOrder, Exercise::order))

            if (exercises.isNotEmpty()) {
                _uiState.value = LessonUiState.Active(
                    currentExercise = exercises[0],
                    exerciseIndex = 0,
                    totalExercises = exercises.size,
                    correctCount = 0,
                    phase = exercises[0].phase,
                )
            }
        }
    }

    fun onAnswer(isCorrect: Boolean) {
        if (isCorrect) correctCount++
        currentIndex++

        if (currentIndex >= exercises.size) {
            val score = if (exercises.isNotEmpty()) {
                (correctCount.toFloat() / exercises.size * 100).roundToInt()
            } else 0

            viewModelScope.launch {
                progressRepository.saveLessonResult(lessonId, score)
            }

            _uiState.value = LessonUiState.Complete(
                lessonId = lessonId,
                score = score,
                correctCount = correctCount,
                totalExercises = exercises.size,
                introExercises = exercises.filter { it.phase == "intro" },
            )
        } else {
            _uiState.value = LessonUiState.Active(
                currentExercise = exercises[currentIndex],
                exerciseIndex = currentIndex,
                totalExercises = exercises.size,
                correctCount = correctCount,
                phase = exercises[currentIndex].phase,
            )
        }
    }

    private fun phaseOrder(exercise: Exercise): Int = when (exercise.phase) {
        "intro" -> 0
        "guided" -> 1
        "mixed" -> 2
        else -> 3
    }
}
