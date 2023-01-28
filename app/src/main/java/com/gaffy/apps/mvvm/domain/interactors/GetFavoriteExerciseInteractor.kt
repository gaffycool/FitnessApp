package com.gaffy.apps.mvvm.domain.interactors

import androidx.lifecycle.LiveData
import com.gaffy.apps.mvvm.data.repository.MuscleRepository
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interactor to fetch favorite exercises
 *
 */
@Singleton
class GetFavoriteExerciseInteractor @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    fun get(): LiveData<List<ExerciseModel>> = muscleRepository.getFavoriteExercises()
}
