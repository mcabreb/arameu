package com.arameu.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.arameu.data.dao.CourseDao
import com.arameu.data.dto.ContentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ContentLoader(
    private val context: Context,
    private val courseDao: CourseDao,
    private val prefs: SharedPreferences,
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loadIfNeeded() {
        val loadedVersion = prefs.getInt(PREF_CONTENT_VERSION, 0)
        if (loadedVersion >= CURRENT_CONTENT_VERSION) return

        withContext(Dispatchers.IO) {
            try {
                val raw = context.assets
                    .open(ASSET_PATH)
                    .bufferedReader()
                    .use { it.readText() }

                val content = json.decodeFromString<ContentDto>(raw)
                populate(content)
                prefs.edit().putInt(PREF_CONTENT_VERSION, CURRENT_CONTENT_VERSION).apply()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load content: ${e.message}", e)
            }
        }
    }

    private suspend fun populate(content: ContentDto) {
        val units = content.units.map { it.toEntity() }
        courseDao.insertUnits(units)

        for (unitDto in content.units) {
            val lessons = unitDto.lessons.map { it.toEntity(unitId = unitDto.id) }
            courseDao.insertLessons(lessons)

            for (lessonDto in unitDto.lessons) {
                val exercises = lessonDto.exercises.mapNotNull { exerciseDto ->
                    try {
                        exerciseDto.toEntity(lessonId = lessonDto.id)
                    } catch (e: Exception) {
                        Log.w(TAG, "Skipping malformed exercise ${exerciseDto.id}: ${e.message}")
                        null
                    }
                }
                courseDao.insertExercises(exercises)
            }
        }

        val vocabulary = content.vocabulary.mapNotNull { vocabDto ->
            try {
                vocabDto.toEntity()
            } catch (e: Exception) {
                Log.w(TAG, "Skipping malformed vocabulary ${vocabDto.id}: ${e.message}")
                null
            }
        }
        courseDao.insertVocabulary(vocabulary)
    }

    companion object {
        private const val TAG = "ContentLoader"
        private const val ASSET_PATH = "course/content.json"
        private const val PREF_CONTENT_VERSION = "content_version"
        // Bump this number whenever content.json changes
        private const val CURRENT_CONTENT_VERSION = 4
    }
}
