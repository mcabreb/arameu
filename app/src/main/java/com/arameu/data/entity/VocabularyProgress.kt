package com.arameu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabulary_progress")
data class VocabularyProgress(
    @PrimaryKey val vocabId: Int,
    val nextReviewAt: Long? = null,
    val interval: Int = 0,
    val easeFactor: Float = 2.5f,
    val correctStreak: Int = 0,
)
