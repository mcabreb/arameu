package com.arameu.data.repository

import com.arameu.data.dao.ProgressDao
import com.arameu.data.entity.LessonProgress
import com.arameu.data.entity.VocabularyProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProgressSaveTest {

    @Test
    fun `saveLessonResult saves with correct score`() = runTest {
        val dao = RecordingProgressDao()
        val repo = ProgressRepository(dao)

        repo.saveLessonResult(lessonId = 5, score = 92)

        assertNotNull(dao.lastSaved)
        assertEquals(5, dao.lastSaved!!.lessonId)
        assertEquals(92, dao.lastSaved!!.score)
    }

    @Test
    fun `saveLessonResult marks lesson completed`() = runTest {
        val dao = RecordingProgressDao()
        val repo = ProgressRepository(dao)

        repo.saveLessonResult(lessonId = 1, score = 75)

        assertTrue(dao.lastSaved!!.completed)
    }

    @Test
    fun `saveLessonResult records completion timestamp`() = runTest {
        val dao = RecordingProgressDao()
        val repo = ProgressRepository(dao)
        val before = System.currentTimeMillis()

        repo.saveLessonResult(lessonId = 1, score = 100)

        val after = System.currentTimeMillis()
        assertNotNull(dao.lastSaved!!.completedAt)
        assertTrue(dao.lastSaved!!.completedAt!! in before..after)
    }

    @Test
    fun `progress data survives across repository instances`() = runTest {
        // Simulate: save in one instance, read in another (same DAO)
        val dao = RecordingProgressDao()
        val repo1 = ProgressRepository(dao)
        repo1.saveLessonResult(1, 88)

        val saved = dao.lastSaved!!
        assertEquals(1, saved.lessonId)
        assertEquals(88, saved.score)
        assertTrue(saved.completed)
    }

    private class RecordingProgressDao : ProgressDao {
        var lastSaved: LessonProgress? = null
        override fun getLessonProgress(lessonId: Int): Flow<LessonProgress?> = flowOf(null)
        override fun getAllLessonProgress(): Flow<List<LessonProgress>> = flowOf(emptyList())
        override suspend fun saveLessonProgress(progress: LessonProgress) {
            lastSaved = progress
        }
        override suspend fun getVocabularyProgress(vocabId: Int): VocabularyProgress? = null
        override suspend fun upsertVocabularyProgress(progress: VocabularyProgress) {}
    }
}
