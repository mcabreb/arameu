package com.arameu.data.repository

import com.arameu.data.dao.CourseDao
import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Lesson
import com.arameu.data.entity.Unit
import com.arameu.data.entity.Vocabulary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CourseRepositoryTest {

    private lateinit var repo: CourseRepository
    private lateinit var dao: FakeCourseDao

    @Before
    fun setUp() {
        dao = FakeCourseDao()
        repo = CourseRepository(dao)
    }

    @Test
    fun `getUnits returns all units as Flow`() = runTest {
        dao.units = listOf(
            Unit(1, "foundations", "Unit 1", 1),
            Unit(2, "foundations", "Unit 2", 2),
        )
        val result = repo.getUnits().first()
        assertEquals(2, result.size)
        assertEquals("Unit 1", result[0].titleCa)
    }

    @Test
    fun `getLessons returns lessons for unit as Flow`() = runTest {
        dao.lessonsByUnit[1] = listOf(
            Lesson(1, 1, "Lesson A", 1),
            Lesson(2, 1, "Lesson B", 2),
        )
        val result = repo.getLessons(1).first()
        assertEquals(2, result.size)
    }

    @Test
    fun `getExercises returns exercises ordered by phase then order`() = runTest {
        dao.exercisesByLesson[1] = listOf(
            Exercise(1, 1, "intro", "multiple_choice", 1, "Q1", null, null, "a", null, "[\"a\",\"b\",\"c\",\"d\"]"),
            Exercise(2, 1, "guided", "matching", 1, "Q2", null, null, "b", null, "[\"x|y\",\"a|b\"]"),
        )
        val result = repo.getExercises(1)
        assertEquals(2, result.size)
        assertEquals("intro", result[0].phase)
    }

    @Test
    fun `getExercises parses options JSON to list`() = runTest {
        dao.exercisesByLesson[1] = listOf(
            Exercise(1, 1, "intro", "multiple_choice", 1, "Q", null, null, "a", null, "[\"a\",\"b\",\"c\",\"d\"]"),
        )
        val result = repo.getExercises(1)
        val parsed = result[0]
        assertEquals("[\"a\",\"b\",\"c\",\"d\"]", parsed.options)
    }

    @Test
    fun `getVocabulary returns vocabulary for unit`() = runTest {
        dao.vocabByUnit[1] = listOf(
            Vocabulary(1, "aleph", "ܐ", "primera lletra", "a_01", 1, null),
        )
        val result = repo.getVocabulary(1).first()
        assertEquals(1, result.size)
        assertEquals("aleph", result[0].transliteration)
    }

    @Test
    fun `getUnits returns empty list when no data`() = runTest {
        val result = repo.getUnits().first()
        assertTrue(result.isEmpty())
    }

    private class FakeCourseDao : CourseDao {
        var units: List<Unit> = emptyList()
        val lessonsByUnit = mutableMapOf<Int, List<Lesson>>()
        val exercisesByLesson = mutableMapOf<Int, List<Exercise>>()
        val vocabByUnit = mutableMapOf<Int, List<Vocabulary>>()

        override fun getUnits(): Flow<List<Unit>> = flowOf(units)
        override fun getLessonsByUnit(unitId: Int): Flow<List<Lesson>> =
            flowOf(lessonsByUnit[unitId] ?: emptyList())
        override suspend fun getExercisesByLesson(lessonId: Int): List<Exercise> =
            exercisesByLesson[lessonId] ?: emptyList()
        override fun getVocabularyByUnit(unitId: Int): Flow<List<Vocabulary>> =
            flowOf(vocabByUnit[unitId] ?: emptyList())
        override suspend fun getVocabularyById(id: Int): Vocabulary? = null
        override suspend fun insertUnits(units: List<Unit>) {}
        override suspend fun insertLessons(lessons: List<Lesson>) {}
        override suspend fun insertExercises(exercises: List<Exercise>) {}
        override suspend fun insertVocabulary(vocabulary: List<Vocabulary>) {}
    }
}
