package com.gaffy.apps.mvvm.presentation.feature.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gaffy.apps.mvvm.InstantExecutorExtension
import com.gaffy.apps.mvvm.MainCoroutineRule
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.interactors.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class FavoriteViewModelTest {

    private val getFavoriteExerciseInteractor: GetFavoriteExerciseInteractor = mockk()
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor = mockk()
    private lateinit var viewModel: FavoriteViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val exercise = ExerciseModel("", 1, "name", true)
    private val exercises: LiveData<List<ExerciseModel>> =
        liveData { listOf(exercise) }

    @Before
    fun setUp() {
        coEvery { getFavoriteExerciseInteractor.get() } returns exercises
        viewModel = FavoriteViewModel(
            getFavoriteExerciseInteractor,
            removeFromFavoriteInteractor
        )
    }

    @Test
    fun testInit() = runTest {
        //when
        viewModel.viewState.observeForever { }

        //then
        val expectedState = viewModel.viewState.value!!
        assertEquals(exercises, expectedState.exercises)
    }

    @Test
    fun testActionFavorite() = runTest {
        //when
        viewModel.actionFavorite(exercise)
        viewModel.viewState.observeForever { }

        coEvery { removeFromFavoriteInteractor.remove(exercise) } just runs

        //then
        coVerify { removeFromFavoriteInteractor.remove(exercise) }
    }
}