package com.gaffy.apps.mvvm.presentation.feature.exercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.viewModels
import com.gaffy.apps.mvvm.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : BaseFragment() {

    private val viewModel: ExerciseViewModel by viewModels()

    @Composable
    override fun RootView() {
        val vmState by viewModel.viewState.observeAsState(initial = ExerciseViewState())
        ExerciseView(
            vmState = vmState,
            onMuscleSelected = viewModel::onMuscleSelected,
            actionFavorite = viewModel::actionFavorite,
            onValueChange = viewModel::onValueChange,
            onDismissMenu = viewModel::onDismissMenu,
        )
    }
}
