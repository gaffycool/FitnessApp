package com.gaffy.apps.mvvm.presentation.feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaffy.apps.R
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.presentation.compose.component.ExerciseItemView
import com.gaffy.apps.mvvm.presentation.compose.theme.AppTheme
import com.gaffy.apps.mvvm.presentation.compose.theme.space4
import com.gaffy.apps.mvvm.presentation.compose.theme.space8

@Composable
fun FavoriteView(
    vmState: FavoriteViewState,
    actionFavorite: (ExerciseModel) -> Unit,
) {
    AppTheme {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(horizontal = space4, vertical = space8)
        ) {
            val exercises by vmState.exercises.observeAsState(initial = emptyList())
            if (exercises.isEmpty()) {
                FavoriteEmptyView()
            } else
                LazyColumn {
                    items(exercises) {
                        ExerciseItemView(
                            model = it,
                            actionFavorite = { actionFavorite(it) }
                        )
                        Spacer(modifier = Modifier.height(space4))
                    }
                }
        }
    }
}

@Composable
fun FavoriteEmptyView() = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_favorite_border),
        contentDescription = stringResource(id = R.string.favorite),
        tint = MaterialTheme.colors.primary,
        modifier = Modifier.size(space8)
    )
    Spacer(modifier = Modifier.height(space8))
    Text(
        text = stringResource(R.string.favorite_empty_title),
        style = MaterialTheme.typography.h3
    )
}