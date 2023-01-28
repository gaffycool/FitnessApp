package com.gaffy.apps.mvvm.presentation.feature.favorite

import androidx.lifecycle.*
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.domain.interactors.GetFavoriteExerciseInteractor
import com.gaffy.apps.mvvm.domain.interactors.RemoveFromFavoriteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for [FavoriteFragment].
 */
@HiltViewModel
class FavoriteViewModel @Inject internal constructor(
    getFavoriteExerciseInteractor: GetFavoriteExerciseInteractor,
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor,
) : ViewModel() {

    private val _viewState = MutableLiveData<FavoriteViewState>()
    val viewState: LiveData<FavoriteViewState> = _viewState

    init {
        _viewState.value = FavoriteViewState(getFavoriteExerciseInteractor.get())
    }

    fun actionFavorite(exerciseModel: ExerciseModel) {
        viewModelScope.launch {
            removeFromFavoriteInteractor.remove(exerciseModel)
        }
    }
}

data class FavoriteViewState(
    val exercises: LiveData<List<ExerciseModel>> = liveData { }
)