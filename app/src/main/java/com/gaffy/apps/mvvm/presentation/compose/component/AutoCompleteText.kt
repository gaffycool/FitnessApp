package com.gaffy.apps.mvvm.presentation.compose.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.gaffy.apps.R
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import com.gaffy.apps.mvvm.presentation.compose.theme.space8

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoCompleteText(
    @StringRes label: Int,
    showDropDown: Boolean,
    filterOpts: List<MuscleModel>,
    onValueChange: (String) -> Unit,
    onItemSelect: (MuscleModel) -> Unit = {},
    onDismissMenu: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Column {
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onValueChange(it.text)
            },
            placeholder = { Text(stringResource(id = label)) },
            shape = RoundedCornerShape(space8),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(
                    onClick = { textState.value = textState.value.copy(text = "") },
                    modifier = Modifier.clearAndSetSemantics { }) {
                    Icon(
                        Icons.Filled.Clear,
                        "Clear",
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(space8)
                )
        )
        DropdownMenu(
            expanded = showDropDown,
            onDismissRequest = onDismissMenu,
            modifier = Modifier
                .heightIn(max = 200.dp),
            properties = PopupProperties(focusable = false, usePlatformDefaultWidth = false)
        ) {
            filterOpts.forEach { option ->
                DropdownMenuItem(onClick = {
                    keyboardController?.hide()
                    onItemSelect(option)
                    textState.value = textState.value.copy(
                        text = option.name,
                        selection = TextRange(option.name.length)
                    )
                }) {
                    Text(
                        text = option.name,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AutoCompleteTextPreview() {
    val model = MuscleModel(1, "Biceps", false, "", "")
    AutoCompleteText(label = R.string.exercise_label,
        showDropDown = true,
        filterOpts = listOf(
            model, model.copy(name = "Triceps"), model.copy(name = "Chest")
        ),
        onValueChange = {},
        onItemSelect = { },
        onDismissMenu = {})
}
