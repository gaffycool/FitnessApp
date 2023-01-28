package com.gaffy.apps.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.gaffy.apps.mvvm.data.api.ApiService
import com.gaffy.apps.mvvm.data.room.ExerciseDao
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 */
@Singleton
class MuscleRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val apiService: com.gaffy.apps.mvvm.data.api.ApiService,
) {
    suspend fun fetchMuscle(): List<MuscleModel> {
        val response = apiService.fetchMuscle()
        return when (response.code()) {
            HttpURLConnection.HTTP_OK -> response.body()!!.results.map { it.toDomain() }
            else -> throw IllegalStateException("Failed to load muscle, Please try again")
        }
    }

    suspend fun fetchExercise(muscleId: Int, muscles: List<MuscleModel>): List<ExerciseModel> {
        val response = apiService.fetchExercise(muscleId)
        return when (response.code()) {
            HttpURLConnection.HTTP_OK -> response.body()!!.results.map {
                it.toDomain(
                    isFavorite = findFavorite(it.id),
                    muscleList = muscles
                )
            }
            else -> throw IllegalStateException("Failed to load exercise, Please try again")
        }
    }

    fun getFavoriteExercises(): LiveData<List<ExerciseModel>> =
        exerciseDao.getFavoriteExercises()

    suspend fun saveToFavorite(exerciseModel: ExerciseModel) = exerciseDao.insert(exerciseModel)

    suspend fun removeFromFavorite(exerciseModel: ExerciseModel) =
        exerciseDao.delete(exerciseModel)

    private suspend fun findFavorite(id: Int): Boolean = exerciseDao.findFavorite(id) != null
}
