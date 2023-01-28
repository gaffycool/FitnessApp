package com.gaffy.apps.mvvm.domain.model

data class MuscleModel(
    val id: Int,
    val name: String,
    val isFront: Boolean,
    val imageUrlMain: String,
    val imageUrlSecondary: String,
)