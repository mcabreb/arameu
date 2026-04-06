package com.arameu.data.repository

import com.arameu.data.dao.CourseDao
import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Lesson
import com.arameu.data.entity.Unit
import com.arameu.data.entity.Vocabulary
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {

    fun getUnits(): Flow<List<Unit>> = courseDao.getUnits()

    fun getLessons(unitId: Int): Flow<List<Lesson>> = courseDao.getLessonsByUnit(unitId)

    suspend fun getExercises(lessonId: Int): List<Exercise> =
        courseDao.getExercisesByLesson(lessonId)

    fun getVocabulary(unitId: Int): Flow<List<Vocabulary>> = courseDao.getVocabularyByUnit(unitId)
}
