package com.gaffy.apps.mvvm.presentation.compose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.gaffy.apps.R
import com.gaffy.apps.mvvm.data.room.ExerciseModel
import com.gaffy.apps.mvvm.presentation.compose.theme.space2
import com.gaffy.apps.mvvm.presentation.compose.theme.space4
import com.gaffy.apps.mvvm.presentation.compose.theme.space6
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ExerciseItemView(
    model: ExerciseModel,
    actionFavorite: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = Modifier.clickable { expanded = !expanded }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(space4)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = model.name,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.primary
                )
                IconButton(
                    onClick = actionFavorite,
                    modifier = Modifier.size(space6)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (model.favorite) R.drawable.ic_favorite
                            else R.drawable.ic_favorite_border
                        ),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(space4))
            if (!expanded) {
                MarkdownText(
                    markdown = if (model.description.length <= READ_MORE_CHAT_LIMIT) model.description
                    else model.description.substring(0, READ_MORE_CHAT_LIMIT) + "...",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    onClick = { expanded = !expanded }
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    MarkdownText(
                        markdown = model.description,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        onClick = { expanded = !expanded }
                    )
                    Spacer(modifier = Modifier.height(space4))
                    FlowRow(
                        mainAxisSize = SizeMode.Wrap,
                        mainAxisSpacing = space2,
                        crossAxisSpacing = space2,
                        mainAxisAlignment = MainAxisAlignment.Center
                    ) {
                        model.images.forEach {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        color = MaterialTheme.colors.background,
                                        shape = RoundedCornerShape(space6)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExerciseItemPreview() {
    ExerciseItemView(
        model = ExerciseModel(
            description = "The biceps curl is a highly recognizable weight-training exercise that works the muscles of the upper arm and, to a lesser extent, those of the lower arm.1\n" +
                    " It's an excellent exercise for seeing results in strength and definition.",
            id = 1,
            name = "Biceps Curls",
        ),
        actionFavorite = {}
    )
}

const val READ_MORE_CHAT_LIMIT = 100