package com.arameu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_progress")
data class LessonProgress(
    @PrimaryKey val lessonId: Int,
    val completed: Boolean = false,
    val score: Int = 0,
    val completedAt: Long? = null,
)
