package com.gaffy.apps.mvvm.domain.interactors

import com.gaffy.apps.mvvm.data.repository.MuscleRepository
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interactor to save muscle into favorite list
 *
 */
@Singleton
class SaveToFavoriteInteractor @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    suspend fun save(exerciseModel: ExerciseModel) = muscleRepository.saveToFavorite(exerciseModel)
}
