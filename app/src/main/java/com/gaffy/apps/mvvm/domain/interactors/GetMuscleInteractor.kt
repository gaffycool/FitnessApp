package com.gaffy.apps.mvvm.domain.interactors

import com.gaffy.apps.mvvm.data.repository.MuscleRepository
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interactor to fetch list of muscles
 *
 */
@Singleton
class GetMuscleInteractor @Inject constructor(
    private val muscleRepository: MuscleRepository
) {
    suspend fun get(): List<MuscleModel> = muscleRepository.fetchMuscle()
}
