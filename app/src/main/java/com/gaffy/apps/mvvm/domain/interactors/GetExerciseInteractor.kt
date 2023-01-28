package com.gaffy.apps.mvvm.domain.interactors

import com.gaffy.apps.mvvm.data.repository.MuscleRepository
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interactor to fetch exercises for particular muscle
 *
 */
@Singleton
class GetExerciseInteractor @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    suspend fun get(params: Params): List<ExerciseModel> = muscleRepository.fetchExercise(
        params.muscleId,
        params.muscles
    )

    data class Params(val muscleId: Int, val muscles: List<MuscleModel>)
}

