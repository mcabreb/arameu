package com.arameu.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vocabulary",
    foreignKeys = [
        ForeignKey(
            entity = Unit::class,
            parentColumns = ["id"],
            childColumns = ["unitId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("unitId")],
)
data class Vocabulary(
    @PrimaryKey val id: Int,
    val transliteration: String,
    val script: String,
    val meaningCa: String,
    val audioId: String?,
    val unitId: Int,
    val grammarNote: String?,
)
