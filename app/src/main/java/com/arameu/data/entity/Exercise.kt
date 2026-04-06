package com.arameu.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("lessonId")],
)
data class Exercise(
    @PrimaryKey val id: Int,
    val lessonId: Int,
    val phase: String,
    val type: String,
    val order: Int,
    val promptText: String,
    val promptAudioId: String?,
    val promptScript: String?,
    val correctAnswer: String,
    val acceptedVariants: String?,
    val options: String?,
)
