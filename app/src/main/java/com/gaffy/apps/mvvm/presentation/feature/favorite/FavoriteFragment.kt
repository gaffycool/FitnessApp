package com.gaffy.apps.mvvm.presentation.feature.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.viewModels
import com.gaffy.apps.mvvm.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    @Composable
    override fun RootView() {
        val vmState by viewModel.viewState.observeAsState(initial = FavoriteViewState())
        FavoriteView(
            vmState = vmState,
            actionFavorite = viewModel::actionFavorite
        )
    }
}
