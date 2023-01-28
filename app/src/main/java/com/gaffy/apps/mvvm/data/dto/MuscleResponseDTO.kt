package com.gaffy.apps.mvvm.data.dto

import com.gaffy.apps.mvvm.domain.model.MuscleModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MuscleResponseDTO(
    val count: Int,
    val next: Any?,
    val previous: Any?,
    val results: List<MuscleDTO>
)

@JsonClass(generateAdapter = true)
data class MuscleDTO(
    val id: Int,
    val name: String,
    val name_en: String,
    val is_front: Boolean,
    val image_url_main: String,
    val image_url_secondary: String,
) {
    fun toDomain(): MuscleModel = MuscleModel(
        id = id,
        name = name,
        isFront = is_front,
        imageUrlMain = image_url_main,
        imageUrlSecondary = image_url_secondary
    )
}