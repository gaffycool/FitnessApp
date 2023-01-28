package com.gaffy.apps.mvvm.domain.interactors

import com.gaffy.apps.mvvm.data.repository.MuscleRepository
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interactor to remove muscle from favorite list
 *
 */
@Singleton
class RemoveFromFavoriteInteractor @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    suspend fun remove(exerciseModel: ExerciseModel) =
        muscleRepository.removeFromFavorite(exerciseModel)
}
