package com.arameu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Lesson
import com.arameu.data.entity.Unit
import com.arameu.data.entity.Vocabulary
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM units ORDER BY `order`")
    fun getUnits(): Flow<List<Unit>>

    @Query("SELECT * FROM lessons WHERE unitId = :unitId ORDER BY `order`")
    fun getLessonsByUnit(unitId: Int): Flow<List<Lesson>>

    @Query("SELECT * FROM exercises WHERE lessonId = :lessonId ORDER BY phase, `order`")
    suspend fun getExercisesByLesson(lessonId: Int): List<Exercise>

    @Query("SELECT * FROM vocabulary WHERE unitId = :unitId ORDER BY id")
    fun getVocabularyByUnit(unitId: Int): Flow<List<Vocabulary>>

    @Query("SELECT * FROM vocabulary WHERE id = :id")
    suspend fun getVocabularyById(id: Int): Vocabulary?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(units: List<Unit>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<Lesson>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<Exercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(vocabulary: List<Vocabulary>)
}
