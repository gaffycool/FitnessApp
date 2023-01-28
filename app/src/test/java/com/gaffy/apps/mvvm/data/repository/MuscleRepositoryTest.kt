package com.gaffy.apps.mvvm.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gaffy.apps.mvvm.InstantExecutorExtension
import com.gaffy.apps.mvvm.MainCoroutineRule
import com.gaffy.apps.mvvm.data.dto.ExerciseDTO
import com.gaffy.apps.mvvm.data.dto.ExerciseResponseDTO
import com.gaffy.apps.mvvm.data.dto.MuscleDTO
import com.gaffy.apps.mvvm.data.dto.MuscleResponseDTO
import com.gaffy.apps.mvvm.data.room.ExerciseDao
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MuscleRepositoryTest {

    private val exerciseDao: ExerciseDao = mockk()
    private val apiService: com.gaffy.apps.mvvm.data.api.ApiService = mockk()
    private lateinit var repository: MuscleRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val exercise = ExerciseModel("", 1, "name", true)

    @Before
    fun setUp() {
        repository = MuscleRepository(
            exerciseDao, apiService
        )
    }

    /***
     * GIVEN apiService.fetchMuscle returns success response
     * WHEN i invoke fetchMuscle
     * THEN i expect list of MuscleModel to be returned
     */
    @Test
    fun testFetchMuscle() = runTest {
        //given
        val muscleResponseDTO = MuscleResponseDTO(
            count = 20, next = null, previous = null, results = listOf(
                MuscleDTO(
                    1, "name", "", false, "image", ""
                )
            )
        )
        val response = Response.success(muscleResponseDTO)
        coEvery { apiService.fetchMuscle() } returns response

        //when
        val result = repository.fetchMuscle()

        //then
        assertEquals(listOf(MuscleModel(1, "name", false, "image", "")), result)
    }

    /***
     * GIVEN apiService.fetchExercise return success response
     * AND exerciseDao.findFavorite return ExerciseModel with favorite true
     * WHEN i invoke fetchExercise
     * THEN i expect list of ExerciseModel to be returned with favorite true
     */
    @Test
    fun testFetchExercise() = runTest {
        //given
        val muscleId = 1
        val muscles = listOf(MuscleModel(1, "name", false, "/image", ""))
        val muscleResponseDTO = ExerciseResponseDTO(
            count = 20, next = null, previous = null, results = listOf(
                ExerciseDTO(
                    mockk(), 1, "", "description", mockk(), 2, 1, 1, 1, listOf(1),
                    mockk(), "name", "", mockk()
                )
            )
        )
        val response = Response.success(muscleResponseDTO)
        coEvery { apiService.fetchExercise(muscleId) } returns response
        coEvery { exerciseDao.findFavorite(1) } returns ExerciseModel(
            "", 1, "name", true, mockk()
        )

        //when
        val result = repository.fetchExercise(muscleId, muscles)

        //then
        assertEquals(
            listOf(
                ExerciseModel(
                    "description",
                    1,
                    "name",
                    true,
                    listOf("https://wger.de/image")
                )
            ), result
        )
    }

    /***
     * GIVEN exerciseDao.getFavoriteExercises return live data
     * WHEN i invoke getFavoriteExercises
     * THEN i expect same live data to be returned
     */
    @Test
    fun testGetFavoriteExercises() = runTest {
        //given
        val liveData: LiveData<List<ExerciseModel>> = mockk()
        coEvery { exerciseDao.getFavoriteExercises() } returns liveData

        //when
        val result = repository.getFavoriteExercises()

        //then
        assertEquals(liveData, result)
    }

    /***
     * GIVEN exerciseDao.insert with ExerciseModel
     * WHEN i invoke saveToFavorite
     * THEN i expect ExerciseModel to be saved
     */
    @Test
    fun testSaveToFavorite() = runTest {
        //given
        val exerciseModel: ExerciseModel = mockk()
        coEvery { exerciseDao.insert(exerciseModel) } just runs

        //when
        repository.saveToFavorite(exerciseModel)

        //then
        coVerify { exerciseDao.insert(exerciseModel) }
    }

    /***
     * GIVEN exerciseDao.delete with ExerciseModel
     * WHEN i invoke removeFromFavorite
     * THEN i expect ExerciseModel to be delete
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        //given
        val exerciseModel: ExerciseModel = mockk()
        coEvery { exerciseDao.delete(exerciseModel) } just runs

        //when
        repository.removeFromFavorite(exerciseModel)

        //then
        coVerify { exerciseDao.delete(exerciseModel) }
    }
}