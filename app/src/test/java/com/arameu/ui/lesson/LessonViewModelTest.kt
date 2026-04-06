package com.arameu.ui.lesson

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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LessonViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var exercises: List<Exercise>
    private var savedProgress: LessonProgress? = null

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        savedProgress = null
        exercises = listOf(
            Exercise(1, 1, "intro", "multiple_choice", 1, "Q1", null, null, "a", "[\"a\"]", "[\"a\",\"b\",\"c\",\"d\"]"),
            Exercise(2, 1, "guided", "multiple_choice", 1, "Q2", null, null, "b", null, "[\"a\",\"b\",\"c\",\"d\"]"),
            Exercise(3, 1, "mixed", "type_transliteration", 1, "Q3", null, null, "c", "[\"c\",\"C\"]", null),
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(lessonId: Int = 1): LessonViewModel {
        val courseDao = object : CourseDao {
            override fun getUnits(): Flow<List<Unit>> = flowOf(emptyList())
            override fun getLessonsByUnit(unitId: Int): Flow<List<Lesson>> = flowOf(emptyList())
            override suspend fun getExercisesByLesson(lessonId: Int): List<Exercise> = exercises
            override fun getVocabularyByUnit(unitId: Int): Flow<List<Vocabulary>> = flowOf(emptyList())
            override suspend fun getVocabularyById(id: Int): Vocabulary? = null
            override suspend fun insertUnits(units: List<Unit>) {}
            override suspend fun insertLessons(lessons: List<Lesson>) {}
            override suspend fun insertExercises(exercises: List<Exercise>) {}
            override suspend fun insertVocabulary(vocabulary: List<Vocabulary>) {}
        }
        val progressDao = object : ProgressDao {
            override fun getLessonProgress(lessonId: Int): Flow<LessonProgress?> = flowOf(null)
            override fun getAllLessonProgress(): Flow<List<LessonProgress>> = flowOf(emptyList())
            override suspend fun saveLessonProgress(progress: LessonProgress) { savedProgress = progress }
            override suspend fun getVocabularyProgress(vocabId: Int): VocabularyProgress? = null
            override suspend fun upsertVocabularyProgress(progress: VocabularyProgress) {}
            override suspend fun clearAllLessonProgress() {}
            override suspend fun clearAllVocabularyProgress() {}
        }
        return LessonViewModel(
            lessonId = lessonId,
            courseRepository = CourseRepository(courseDao),
            progressRepository = ProgressRepository(progressDao),
        )
    }

    @Test
    fun `loads exercises ordered by phase`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue(state is LessonUiState.Active)
        val active = state as LessonUiState.Active
        assertEquals("intro", active.currentExercise.phase)
    }

    @Test
    fun `tracks correct answers for scored exercises only`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onAnswer(isCorrect = true) // intro — not scored
        advanceUntilIdle()
        val active = vm.uiState.value as LessonUiState.Active
        assertEquals(0, active.correctCount) // intro doesn't count
        vm.onAnswer(isCorrect = true) // guided — scored
        advanceUntilIdle()
        val active2 = vm.uiState.value as LessonUiState.Active
        assertEquals(1, active2.correctCount)
    }

    @Test
    fun `advances to next exercise on answer`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onAnswer(isCorrect = true)
        advanceUntilIdle()
        val active = vm.uiState.value as LessonUiState.Active
        assertEquals("guided", active.currentExercise.phase)
    }

    @Test
    fun `calculates score excluding intro exercises`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onAnswer(true)  // intro — not scored
        advanceUntilIdle()
        vm.onAnswer(true)  // guided — scored, correct
        advanceUntilIdle()
        vm.onAnswer(false) // mixed — scored, incorrect
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue("Expected Complete, got $state", state is LessonUiState.Complete)
        val complete = state as LessonUiState.Complete
        assertEquals(50, complete.score) // 1/2 scored exercises correct
    }

    @Test
    fun `saves progress on completion`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onAnswer(true)
        advanceUntilIdle()
        vm.onAnswer(true)
        advanceUntilIdle()
        vm.onAnswer(true)
        advanceUntilIdle()
        assertNotNull(savedProgress)
        assertEquals(1, savedProgress!!.lessonId)
        assertTrue(savedProgress!!.completed)
        assertEquals(100, savedProgress!!.score) // 2/2 scored exercises (intro excluded)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val vm = createViewModel()
        assertTrue(vm.uiState.value is LessonUiState.Loading)
    }
}
