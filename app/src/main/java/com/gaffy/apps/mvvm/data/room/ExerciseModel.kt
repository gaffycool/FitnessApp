package com.gaffy.apps.mvvm.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class ExerciseModel(
    val description: String,
    @PrimaryKey val id: Int,
    val name: String,
    val favorite: Boolean = false,
    val images: List<String> = emptyList()
)