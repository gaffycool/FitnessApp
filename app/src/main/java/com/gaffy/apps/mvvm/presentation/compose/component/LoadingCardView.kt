package com.gaffy.apps.mvvm.presentation.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.gaffy.apps.mvvm.presentation.compose.theme.Blue100
import com.gaffy.apps.mvvm.presentation.compose.theme.space2
import com.gaffy.apps.mvvm.presentation.compose.theme.space4
import com.gaffy.apps.mvvm.presentation.compose.theme.space6

@Preview(showBackground = true)
@Composable
fun LoadingCardView() = Card {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4)
    ) {
        LoadingRectangleView(0.5f, space6)
        Spacer(modifier = Modifier.height(space4))
        LoadingRectangleView()
        Spacer(modifier = Modifier.height(space2))
        LoadingRectangleView()
    }
}


@Composable
fun LoadingRectangleView(width: Float = 1f, height: Dp = space4) = Spacer(
    modifier = Modifier
        .fillMaxWidth(fraction = width)
        .height(height)
        .background(
            color = Blue100,
            shape = RoundedCornerShape(space6)
        )
)