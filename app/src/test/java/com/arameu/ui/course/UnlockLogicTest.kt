package com.arameu.ui.course

import com.arameu.data.dao.CourseDao
import com.arameu.data.dao.ProgressDao
import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Lesson
import com.arameu.data.entity.LessonProgress
import com.arameu.data.entity.Unit
import com.arameu.data.entity.Vocabulary
import com.arameu.data.entity.VocabularyProgress
import com.arameu.data.repository.CourseRepository
import com.arameu.data.repository.ProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UnlockLogicTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel(
        units: List<Unit>,
        lessons: Map<Int, List<Lesson>>,
        progress: List<LessonProgress>,
    ): CourseMapViewModel {
        val lessonFlows = lessons.mapValues { MutableStateFlow(it.value) }.toMutableMap()
        val courseDao = object : CourseDao {
            override fun getUnits(): Flow<List<Unit>> = flowOf(units)
            override fun getLessonsByUnit(unitId: Int): Flow<List<Lesson>> =
                lessonFlows[unitId] ?: flowOf(emptyList())
            override suspend fun getExercisesByLesson(lessonId: Int): List<Exercise> = emptyList()
            override fun getVocabularyByUnit(unitId: Int): Flow<List<Vocabulary>> = flowOf(emptyList())
            override suspend fun getVocabularyById(id: Int): Vocabulary? = null
            override suspend fun insertUnits(units: List<Unit>) {}
            override suspend fun insertLessons(lessons: List<Lesson>) {}
            override suspend fun insertExercises(exercises: List<Exercise>) {}
            override suspend fun insertVocabulary(vocabulary: List<Vocabulary>) {}
        }
        val progressDao = object : ProgressDao {
            override fun getLessonProgress(lessonId: Int): Flow<LessonProgress?> = flowOf(null)
            override fun getAllLessonProgress(): Flow<List<LessonProgress>> = flowOf(progress)
            override suspend fun saveLessonProgress(progress: LessonProgress) {}
            override suspend fun getVocabularyProgress(vocabId: Int): VocabularyProgress? = null
            override suspend fun upsertVocabularyProgress(progress: VocabularyProgress) {}
            override suspend fun clearAllLessonProgress() {}
            override suspend fun clearAllVocabularyProgress() {}
        }
        return CourseMapViewModel(CourseRepository(courseDao), ProgressRepository(progressDao))
    }

    @Test
    fun `first lesson of first unit is unlocked by default`() = runTest {
        val vm = buildViewModel(
            units = listOf(Unit(1, "f", "U1", 1)),
            lessons = mapOf(1 to listOf(Lesson(1, 1, "L1", 1), Lesson(2, 1, "L2", 2))),
            progress = emptyList(),
        )
        advanceUntilIdle()
        val ready = vm.uiState.value as CourseMapUiState.Ready
        assertTrue(ready.units[0].lessons[0].isUnlocked)
    }

    @Test
    fun `second lesson locked when first not completed`() = runTest {
        val vm = buildViewModel(
            units = listOf(Unit(1, "f", "U1", 1)),
            lessons = mapOf(1 to listOf(Lesson(1, 1, "L1", 1), Lesson(2, 1, "L2", 2))),
            progress = emptyList(),
        )
        advanceUntilIdle()
        val ready = vm.uiState.value as CourseMapUiState.Ready
        assertFalse(ready.units[0].lessons[1].isUnlocked)
    }

    @Test
    fun `completing lesson unlocks next lesson in same unit`() = runTest {
        val vm = buildViewModel(
            units = listOf(Unit(1, "f", "U1", 1)),
            lessons = mapOf(1 to listOf(Lesson(1, 1, "L1", 1), Lesson(2, 1, "L2", 2))),
            progress = listOf(LessonProgress(1, true, 80, 1000L)),
        )
        advanceUntilIdle()
        val ready = vm.uiState.value as CourseMapUiState.Ready
        assertTrue(ready.units[0].lessons[1].isUnlocked)
    }

    @Test
    fun `completing all lessons in unit unlocks first lesson of next unit`() = runTest {
        val vm = buildViewModel(
            units = listOf(Unit(1, "f", "U1", 1), Unit(2, "f", "U2", 2)),
            lessons = mapOf(
                1 to listOf(Lesson(1, 1, "L1", 1)),
                2 to listOf(Lesson(2, 2, "L2", 1)),
            ),
            progress = listOf(LessonProgress(1, true, 90, 1000L)),
        )
        advanceUntilIdle()
        val ready = vm.uiState.value as CourseMapUiState.Ready
        assertTrue(ready.units[1].lessons[0].isUnlocked)
    }

    @Test
    fun `locked lessons are not tappable`() = runTest {
        val vm = buildViewModel(
            units = listOf(Unit(1, "f", "U1", 1)),
            lessons = mapOf(1 to listOf(Lesson(1, 1, "L1", 1), Lesson(2, 1, "L2", 2))),
            progress = emptyList(),
        )
        advanceUntilIdle()
        val ready = vm.uiState.value as CourseMapUiState.Ready
        assertFalse(ready.units[0].lessons[1].isUnlocked)
    }

    @Test
    fun `unlock state persists across restarts — derived from progress`() = runTest {
        // Simulates restart: same data → same unlock state
        val vm = buildViewModel(
            units = listOf(Unit(1, "f", "U1", 1)),
            lessons = mapOf(1 to listOf(Lesson(1, 1, "L1", 1), Lesson(2, 1, "L2", 2), Lesson(3, 1, "L3", 3))),
            progress = listOf(LessonProgress(1, true, 85, 1000L)),
        )
        advanceUntilIdle()
        val ready = vm.uiState.value as CourseMapUiState.Ready
        assertTrue(ready.units[0].lessons[0].isCompleted)
        assertTrue(ready.units[0].lessons[1].isUnlocked)
        assertFalse(ready.units[0].lessons[2].isUnlocked)
    }
}
