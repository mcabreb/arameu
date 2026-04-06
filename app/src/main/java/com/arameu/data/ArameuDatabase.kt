package com.arameu.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arameu.data.dao.CourseDao
import com.arameu.data.dao.ProgressDao
import com.arameu.data.entity.Exercise
import com.arameu.data.entity.Lesson
import com.arameu.data.entity.LessonProgress
import com.arameu.data.entity.Unit
import com.arameu.data.entity.Vocabulary
import com.arameu.data.entity.VocabularyProgress

@Database(
    entities = [
        Unit::class,
        Lesson::class,
        Exercise::class,
        Vocabulary::class,
        LessonProgress::class,
        VocabularyProgress::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ArameuDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile
        private var INSTANCE: ArameuDatabase? = null

        fun getInstance(context: Context): ArameuDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ArameuDatabase::class.java,
                    "arameu.db",
                ).build().also { INSTANCE = it }
            }
        }

        fun getInMemoryInstance(context: Context): ArameuDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                ArameuDatabase::class.java,
            ).allowMainThreadQueries().build()
        }
    }
}
