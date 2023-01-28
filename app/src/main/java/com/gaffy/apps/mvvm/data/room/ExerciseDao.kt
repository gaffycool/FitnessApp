package com.gaffy.apps.mvvm.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the ExerciseModel class.
 */
@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise ORDER BY name")
    fun getFavoriteExercises(): LiveData<List<ExerciseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseModel: ExerciseModel)

    @Delete
    suspend fun delete(vararg songs: ExerciseModel)

    @Query("SELECT * FROM exercise WHERE id = :id LIMIT 1")
    suspend fun findFavorite(id: Int): ExerciseModel?
}
