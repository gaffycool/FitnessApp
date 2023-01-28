package com.gaffy.apps.mvvm.presentation.feature.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.interactors.GetExerciseInteractor
import com.gaffy.apps.mvvm.domain.interactors.GetMuscleInteractor
import com.gaffy.apps.mvvm.domain.interactors.RemoveFromFavoriteInteractor
import com.gaffy.apps.mvvm.domain.interactors.SaveToFavoriteInteractor
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject internal constructor(
    private val getMuscleInteractor: GetMuscleInteractor,
    private val getExerciseInteractor: GetExerciseInteractor,
    private val saveToFavoriteInteractor: SaveToFavoriteInteractor,
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor,
) : ViewModel() {

    private val _viewState = MutableLiveData<ExerciseViewState>()
    val viewState: LiveData<ExerciseViewState> get() = _viewState

    private var vmState: ExerciseViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    init {
        vmState = ExerciseViewState()
        fetchMuscles()
    }

    fun fetchMuscles() {
        viewModelScope.launch {
            vmState = vmState.copy(muscles = getMuscleInteractor.get())
        }
    }

    fun actionFavorite(index: Int) {
        val exerciseListState = vmState.exerciseListState as ExerciseListState.Loaded
        val model = exerciseListState.exercises[index]
        viewModelScope.launch {
            vmState = if (model.favorite) {
                removeFromFavoriteInteractor.remove(model)
                vmState.copy(
                    exerciseListState = exerciseListState.copy(
                        exercises = exerciseListState.exercises.toMutableList().apply {
                            set(index, model.copy(favorite = false))
                        }.toList()
                    )
                )
            } else {
                val favoriteModel = model.copy(favorite = true)
                saveToFavoriteInteractor.save(favoriteModel)
                vmState.copy(
                    exerciseListState = exerciseListState.copy(
                        exercises = exerciseListState.exercises.toMutableList().apply {
                            set(index, favoriteModel)
                        }.toList()
                    )
                )
            }
        }
    }

    fun onValueChange(value: String) {
        vmState = vmState.copy(searchValue = value, showDropdownMenu = value.isNotEmpty())
    }

    fun onDismissMenu() {
        vmState = vmState.copy(showDropdownMenu = false)
    }

    fun onMuscleSelected(muscle: MuscleModel) {
        vmState = vmState.copy(
            selectedMuscleModel = muscle,
            exerciseListState = ExerciseListState.Loading,
            searchValue = muscle.name,
            showDropdownMenu = false
        )
        viewModelScope.launch {
            vmState = vmState.copy(
                exerciseListState = ExerciseListState.Loaded(
                    exercises = getExerciseInteractor.get(
                        GetExerciseInteractor.Params(muscle.id, vmState.muscles)
                    )
                )
            )
        }
    }
}

data class ExerciseViewState(
    val muscles: List<MuscleModel> = emptyList(),
    val searchValue: String = "",
    val showDropdownMenu: Boolean = false,
    val selectedMuscleModel: MuscleModel? = null,
    val exerciseListState: ExerciseListState = ExerciseListState.None
) {
    fun searchedMuscle(): List<MuscleModel> {
        return muscles.filter { it.name.contains(searchValue, ignoreCase = true) }
    }
}

sealed class ExerciseListState {
    object None : ExerciseListState()
    object Loading : ExerciseListState()
    data class Loaded(val exercises: List<ExerciseModel> = emptyList()) : ExerciseListState()
}
