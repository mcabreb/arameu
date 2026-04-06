package com.arameu.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "lessons",
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
data class Lesson(
    @PrimaryKey val id: Int,
    val unitId: Int,
    val titleCa: String,
    val order: Int,
)
