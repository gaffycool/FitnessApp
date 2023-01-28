package com.gaffy.apps.mvvm.data.dto

import com.gaffy.apps.mvvm.data.api.ApiService
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExerciseResponseDTO(
    val count: Int, val next: Any?, val previous: Any?, val results: List<ExerciseDTO>
)

@JsonClass(generateAdapter = true)
data class ExerciseDTO(
    val author_history: List<String>,
    val category: Int,
    val creation_date: String,
    val description: String,
    val equipment: List<Int>,
    val exercise_base: Int,
    val id: Int,
    val language: Int,
    val license: Int,
    val muscles: List<Int>,
    val muscles_secondary: List<Int>,
    val name: String,
    val uuid: String,
    val variations: List<Int>
) {
    fun toDomain(isFavorite: Boolean, muscleList: List<MuscleModel>): ExerciseModel = ExerciseModel(
        description = description,
        id = id,
        name = name,
        favorite = isFavorite,
        images = muscles.map { id ->
            com.gaffy.apps.mvvm.data.api.ApiService.BASE_URL + muscleList.first { it.id == id }.imageUrlMain
        })
}