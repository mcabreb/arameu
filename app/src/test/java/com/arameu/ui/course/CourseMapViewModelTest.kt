package com.arameu.ui.course

import com.arameu.data.entity.Lesson
import com.arameu.data.entity.LessonProgress
import com.arameu.data.entity.Unit
import com.arameu.data.repository.CourseRepository
import com.arameu.data.repository.ProgressRepository
import com.arameu.data.dao.CourseDao
import com.arameu.data.dao.ProgressDao
import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Vocabulary
import com.arameu.data.entity.VocabularyProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CourseMapViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var unitsFlow: MutableStateFlow<List<Unit>>
    private lateinit var progressFlow: MutableStateFlow<List<LessonProgress>>
    private lateinit var lessonFlows: MutableMap<Int, MutableStateFlow<List<Lesson>>>
    private lateinit var viewModel: CourseMapViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        unitsFlow = MutableStateFlow(emptyList())
        progressFlow = MutableStateFlow(emptyList())
        lessonFlows = mutableMapOf()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): CourseMapViewModel {
        val courseDao = object : CourseDao {
            override fun getUnits(): Flow<List<Unit>> = unitsFlow
            override fun getLessonsByUnit(unitId: Int): Flow<List<Lesson>> =
                lessonFlows.getOrPut(unitId) { MutableStateFlow(emptyList()) }
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
            override fun getAllLessonProgress(): Flow<List<LessonProgress>> = progressFlow
            override suspend fun saveLessonProgress(progress: LessonProgress) {}
            override suspend fun getVocabularyProgress(vocabId: Int): VocabularyProgress? = null
            override suspend fun upsertVocabularyProgress(progress: VocabularyProgress) {}
            override suspend fun clearAllLessonProgress() {}
            override suspend fun clearAllVocabularyProgress() {}
        }
        return CourseMapViewModel(
            courseRepository = CourseRepository(courseDao),
            progressRepository = ProgressRepository(progressDao),
        )
    }

    @Test
    fun `initial state is Loading`() = runTest {
        viewModel = createViewModel()
        assertTrue(viewModel.uiState.value is CourseMapUiState.Loading)
    }

    @Test
    fun `emits units with lessons when data available`() = runTest {
        unitsFlow.value = listOf(
            Unit(1, "foundations", "Unit 1", 1),
        )
        lessonFlows[1] = MutableStateFlow(listOf(
            Lesson(1, 1, "Lesson 1", 1),
            Lesson(2, 1, "Lesson 2", 2),
        ))
        viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue("Expected Ready, got $state", state is CourseMapUiState.Ready)
        val ready = state as CourseMapUiState.Ready
        assertEquals(1, ready.units.size)
        assertEquals(2, ready.units[0].lessons.size)
    }

    @Test
    fun `first lesson is current when no progress`() = runTest {
        unitsFlow.value = listOf(Unit(1, "foundations", "Unit 1", 1))
        lessonFlows[1] = MutableStateFlow(listOf(
            Lesson(1, 1, "Lesson 1", 1),
            Lesson(2, 1, "Lesson 2", 2),
        ))
        progressFlow.value = emptyList()
        viewModel = createViewModel()
        advanceUntilIdle()

        val ready = viewModel.uiState.value as CourseMapUiState.Ready
        assertEquals(1, ready.currentLessonId)
    }

    @Test
    fun `second lesson is current when first is completed`() = runTest {
        unitsFlow.value = listOf(Unit(1, "foundations", "Unit 1", 1))
        lessonFlows[1] = MutableStateFlow(listOf(
            Lesson(1, 1, "Lesson 1", 1),
            Lesson(2, 1, "Lesson 2", 2),
        ))
        progressFlow.value = listOf(
            LessonProgress(1, true, 90, 1000L),
        )
        viewModel = createViewModel()
        advanceUntilIdle()

        val ready = viewModel.uiState.value as CourseMapUiState.Ready
        assertEquals(2, ready.currentLessonId)
    }

    @Test
    fun `completed lesson has isCompleted=true`() = runTest {
        unitsFlow.value = listOf(Unit(1, "foundations", "Unit 1", 1))
        lessonFlows[1] = MutableStateFlow(listOf(
            Lesson(1, 1, "Lesson 1", 1),
        ))
        progressFlow.value = listOf(
            LessonProgress(1, true, 85, 1000L),
        )
        viewModel = createViewModel()
        advanceUntilIdle()

        val ready = viewModel.uiState.value as CourseMapUiState.Ready
        assertTrue(ready.units[0].lessons[0].isCompleted)
        assertEquals(85, ready.units[0].lessons[0].score)
    }

    @Test
    fun `all lessons completed results in null currentLessonId`() = runTest {
        unitsFlow.value = listOf(Unit(1, "foundations", "Unit 1", 1))
        lessonFlows[1] = MutableStateFlow(listOf(
            Lesson(1, 1, "Lesson 1", 1),
        ))
        progressFlow.value = listOf(
            LessonProgress(1, true, 100, 1000L),
        )
        viewModel = createViewModel()
        advanceUntilIdle()

        val ready = viewModel.uiState.value as CourseMapUiState.Ready
        assertNull(ready.currentLessonId)
    }
}
