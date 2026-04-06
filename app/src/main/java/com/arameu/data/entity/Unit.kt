package com.arameu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "units")
data class Unit(
    @PrimaryKey val id: Int,
    val level: String,
    val titleCa: String,
    val order: Int,
)
