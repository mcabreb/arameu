package com.arameu.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arameu.data.entity.LessonProgress
import com.arameu.data.repository.CourseRepository
import com.arameu.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CourseMapViewModel(
    private val courseRepository: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseMapUiState>(CourseMapUiState.Loading)
    val uiState: StateFlow<CourseMapUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                courseRepository.getUnits(),
                progressRepository.getAllProgress(),
            ) { units, progressList ->
                val progressMap = progressList.associateBy { it.lessonId }
                val allLessons = mutableListOf<Pair<Int, Boolean>>() // lessonId to isCompleted

                val unitItems = units.sortedBy { it.order }.map { unit ->
                    val lessons = courseRepository.getLessons(unit.id).first()
                        .sortedBy { it.order }

                    val lessonItems = lessons.map { lesson ->
                        val progress = progressMap[lesson.id]
                        val isCompleted = progress?.completed == true
                        allLessons.add(lesson.id to isCompleted)
                        LessonWithProgress(
                            id = lesson.id,
                            titleCa = lesson.titleCa,
                            order = lesson.order,
                            isCompleted = isCompleted,
                            isUnlocked = false, // computed below
                            score = progress?.score,
                        )
                    }

                    UnitWithProgress(
                        id = unit.id,
                        titleCa = unit.titleCa,
                        order = unit.order,
                        lessons = lessonItems,
                    )
                }

                // Compute unlock states
                val unlockedUnits = unitItems.map { unit ->
                    unit.copy(
                        lessons = unit.lessons.mapIndexed { idx, lesson ->
                            val isFirst = unit == unitItems.first() && idx == 0
                            val prevCompleted = if (idx > 0) {
                                unit.lessons[idx - 1].isCompleted
                            } else {
                                val prevUnit = unitItems.getOrNull(unitItems.indexOf(unit) - 1)
                                prevUnit?.lessons?.lastOrNull()?.isCompleted ?: false
                            }
                            lesson.copy(isUnlocked = isFirst || prevCompleted || lesson.isCompleted)
                        }
                    )
                }

                val currentLessonId = allLessons.firstOrNull { !it.second }?.first

                CourseMapUiState.Ready(
                    units = unlockedUnits,
                    currentLessonId = currentLessonId,
                )
            }.collect { _uiState.value = it }
        }
    }
}
