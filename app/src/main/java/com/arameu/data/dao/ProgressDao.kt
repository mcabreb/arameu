package com.arameu.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.arameu.data.entity.LessonProgress
import com.arameu.data.entity.VocabularyProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId")
    fun getLessonProgress(lessonId: Int): Flow<LessonProgress?>

    @Query("SELECT * FROM lesson_progress")
    fun getAllLessonProgress(): Flow<List<LessonProgress>>

    @Upsert
    suspend fun saveLessonProgress(progress: LessonProgress)

    @Query("SELECT * FROM vocabulary_progress WHERE vocabId = :vocabId")
    suspend fun getVocabularyProgress(vocabId: Int): VocabularyProgress?

    @Upsert
    suspend fun upsertVocabularyProgress(progress: VocabularyProgress)

    @Query("DELETE FROM lesson_progress")
    suspend fun clearAllLessonProgress()

    @Query("DELETE FROM vocabulary_progress")
    suspend fun clearAllVocabularyProgress()
}
