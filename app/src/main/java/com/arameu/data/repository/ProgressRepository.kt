package com.arameu.data.repository

import com.arameu.data.dao.ProgressDao
import com.arameu.data.entity.LessonProgress
import kotlinx.coroutines.flow.Flow

class ProgressRepository(private val progressDao: ProgressDao) {

    fun getLessonProgress(lessonId: Int): Flow<LessonProgress?> =
        progressDao.getLessonProgress(lessonId)

    fun getAllProgress(): Flow<List<LessonProgress>> =
        progressDao.getAllLessonProgress()

    suspend fun resetAllProgress() {
        progressDao.clearAllLessonProgress()
        progressDao.clearAllVocabularyProgress()
    }

    suspend fun saveLessonResult(lessonId: Int, score: Int) {
        progressDao.saveLessonProgress(
            LessonProgress(
                lessonId = lessonId,
                completed = true,
                score = score,
                completedAt = System.currentTimeMillis(),
            )
        )
    }
}
