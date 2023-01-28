package com.gaffy.apps.mvvm.presentation.feature.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gaffy.apps.mvvm.InstantExecutorExtension
import com.gaffy.apps.mvvm.MainCoroutineRule
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.interactors.GetExerciseInteractor
import com.gaffy.apps.mvvm.domain.interactors.GetMuscleInteractor
import com.gaffy.apps.mvvm.domain.interactors.RemoveFromFavoriteInteractor
import com.gaffy.apps.mvvm.domain.interactors.SaveToFavoriteInteractor
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ExerciseViewModelTest {

    private val getMuscleInteractor: GetMuscleInteractor = mockk()
    private val getExerciseInteractor: GetExerciseInteractor = mockk()
    private val saveToFavoriteInteractor: SaveToFavoriteInteractor = mockk()
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor = mockk()

    private lateinit var viewModel: ExerciseViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var exercise = ExerciseModel("", 1, "name", true)

    @Before
    fun setUp() {
        viewModel = ExerciseViewModel(
            getMuscleInteractor,
            getExerciseInteractor,
            saveToFavoriteInteractor,
            removeFromFavoriteInteractor
        )
    }

    /***
     * GIVEN getMuscleInteractor return list of MuscleModel
     * WHEN i invoke fetchMuscle
     * THEN i expect viewstate should updated with same list of MuscleModel
     */
    @Test
    fun testFetchMuscles() = runTest {
        //given
        val muscles: List<MuscleModel> = listOf(mockk())
        coEvery { getMuscleInteractor.get() } returns muscles

        //when
        viewModel.fetchMuscles()
        viewModel.viewState.observeForever { }

        //then
        assertEquals(muscles, viewModel.viewState.value?.muscles)
    }

    /***
     * GIVEN getExerciseInteractor return list of ExerciseModel
     * WHEN i invoke onMuscleSelected
     * THEN i expect viewstate should updated with ExerciseListState.Loaded
     */
    @Test
    fun testMuscleSelected() = runTest {
        //given
        val muscle: MuscleModel = mockk {
            every { name } returns "Biceps"
            every { id } returns 1
        }
        testFetchMuscles()

        val vmState = viewModel.viewState.value!!
        val exercises: List<ExerciseModel> = listOf(exercise)
        val params = GetExerciseInteractor.Params(muscle.id, vmState.muscles)
        coEvery {
            getExerciseInteractor.get(params)
        } returns exercises

        //when
        viewModel.onMuscleSelected(muscle)

        //then
        val expectedState = viewModel.viewState.value!!
        assertTrue(expectedState.exerciseListState is ExerciseListState.Loaded)
        assertEquals(
            exercises,
            (expectedState.exerciseListState as ExerciseListState.Loaded).exercises
        )
    }

    /***
     * GIVEN search value
     * WHEN i invoke onValueChange
     * THEN i expect viewstate should updated with searchValue and showDropdownMenu true
     */
    @Test
    fun testValueChange() = runTest {
        //given
        val value = "a"

        //when
        viewModel.onValueChange(value)
        viewModel.viewState.observeForever { }

        //then
        val expectedState = viewModel.viewState.value!!
        assertEquals(value, expectedState.searchValue)
        assertTrue(expectedState.showDropdownMenu)
    }

    /***
     * GIVEN testValueChange()
     * WHEN i invoke onDismissMenu
     * THEN i expect viewstate should updated with showDropdownMenu false
     */
    @Test
    fun testDismissMenu() = runTest {
        //given
        testValueChange()

        //when
        viewModel.onDismissMenu()
        viewModel.viewState.observeForever { }

        //then
        val expectedState = viewModel.viewState.value!!
        assertFalse(expectedState.showDropdownMenu)
    }

    /***
     * GIVEN testFetchMuscles() and testMuscleSelected()
     * AND removeFromFavoriteInteractor with favorite true
     * WHEN i invoke actionFavorite
     * THEN i expect viewstate should updated with favorite value
     */
    @Test
    fun testActionRemoveFromFavorite() = runTest {
        //given
        testFetchMuscles()
        testMuscleSelected()

        exercise = exercise.copy(favorite = true)
        coEvery { removeFromFavoriteInteractor.remove(exercise) } just runs

        //when
        viewModel.actionFavorite(0)
        viewModel.viewState.observeForever { }

        //then
        val expectedState = viewModel.viewState.value!!
        assertFalse(
            (expectedState.exerciseListState as ExerciseListState.Loaded).exercises[0].favorite
        )
    }

    /***
     * GIVEN testFetchMuscles() and testMuscleSelected()
     * AND saveToFavoriteInteractor with favorite false
     * WHEN i invoke actionFavorite
     * THEN i expect viewstate should updated with favorite value
     */
    @Test
    fun testActionSaveToFavorite() = runTest {
        //given
        testFetchMuscles()
        testMuscleSelected()

        exercise = exercise.copy(favorite = false)
        coEvery { saveToFavoriteInteractor.save(exercise) } just runs

        //when
        viewModel.actionFavorite(0)
        viewModel.viewState.observeForever { }

        //then
        val expectedState = viewModel.viewState.value!!
        assertTrue(
            (expectedState.exerciseListState as ExerciseListState.Loaded).exercises[0].favorite
        )
    }
}