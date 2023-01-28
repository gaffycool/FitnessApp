package com.gaffy.apps.mvvm.presentation.feature.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaffy.apps.R
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import com.gaffy.apps.mvvm.presentation.compose.component.AutoCompleteText
import com.gaffy.apps.mvvm.presentation.compose.component.ExerciseItemView
import com.gaffy.apps.mvvm.presentation.compose.component.LoadingCardView
import com.gaffy.apps.mvvm.presentation.compose.theme.AppTheme
import com.gaffy.apps.mvvm.presentation.compose.theme.space4
import com.gaffy.apps.mvvm.presentation.compose.theme.space8

@Composable
fun ExerciseView(
    vmState: ExerciseViewState,
    onMuscleSelected: (MuscleModel) -> Unit,
    actionFavorite: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    onDismissMenu: () -> Unit = {}
) {
    AppTheme {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(horizontal = space4, vertical = space8)
        ) {
            AutoCompleteText(
                label = R.string.exercise_label,
                showDropDown = vmState.showDropdownMenu,
                filterOpts = vmState.searchedMuscle(),
                onValueChange = onValueChange,
                onItemSelect = onMuscleSelected,
                onDismissMenu = onDismissMenu
            )
            Spacer(modifier = Modifier.height(space4))
            when (val state = vmState.exerciseListState) {
                is ExerciseListState.Loaded -> LazyColumn {
                    items(count = state.exercises.count()) {
                        ExerciseItemView(
                            model = state.exercises[it],
                            actionFavorite = { actionFavorite(it) }
                        )
                        Spacer(modifier = Modifier.height(space4))
                    }
                }
                ExerciseListState.Loading -> LoadingCardView()
                ExerciseListState.None -> {
                    //do nothing
                }
            }
        }
    }
}