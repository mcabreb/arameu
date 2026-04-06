package com.arameu.data.repository

import com.arameu.data.dao.ProgressDao
import com.arameu.data.entity.LessonProgress
import com.arameu.data.entity.VocabularyProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProgressRepositoryTest {

    private lateinit var repo: ProgressRepository
    private lateinit var dao: FakeProgressDao

    @Before
    fun setUp() {
        dao = FakeProgressDao()
        repo = ProgressRepository(dao)
    }

    @Test
    fun `getLessonProgress returns progress by lessonId`() = runTest {
        dao.lessonProgressMap[1] = LessonProgress(1, true, 85, 1000L)
        val result = repo.getLessonProgress(1).first()
        assertNotNull(result)
        assertEquals(85, result!!.score)
    }

    @Test
    fun `getLessonProgress returns null for unknown lesson`() = runTest {
        val result = repo.getLessonProgress(999).first()
        assertNull(result)
    }

    @Test
    fun `saveLessonResult creates progress with completed=true`() = runTest {
        repo.saveLessonResult(1, 90)
        val saved = dao.savedLessonProgress
        assertNotNull(saved)
        assertEquals(1, saved!!.lessonId)
        assertTrue(saved.completed)
        assertEquals(90, saved.score)
        assertNotNull(saved.completedAt)
    }

    @Test
    fun `getAllProgress returns all lesson progress`() = runTest {
        dao.allProgress = listOf(
            LessonProgress(1, true, 80, 1000L),
            LessonProgress(2, true, 95, 2000L),
        )
        val result = repo.getAllProgress().first()
        assertEquals(2, result.size)
    }

    private class FakeProgressDao : ProgressDao {
        val lessonProgressMap = mutableMapOf<Int, LessonProgress>()
        var allProgress: List<LessonProgress> = emptyList()
        var savedLessonProgress: LessonProgress? = null

        override fun getLessonProgress(lessonId: Int): Flow<LessonProgress?> =
            flowOf(lessonProgressMap[lessonId])
        override fun getAllLessonProgress(): Flow<List<LessonProgress>> =
            flowOf(allProgress)
        override suspend fun saveLessonProgress(progress: LessonProgress) {
            savedLessonProgress = progress
        }
        override suspend fun getVocabularyProgress(vocabId: Int): VocabularyProgress? = null
        override suspend fun upsertVocabularyProgress(progress: VocabularyProgress) {}
        override suspend fun clearAllLessonProgress() {}
        override suspend fun clearAllVocabularyProgress() {}
    }
}
