package com.gaffy.apps.mvvm.presentation.compose.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.gaffy.apps.R
import com.gaffy.apps.mvvm.domain.model.MuscleModel
import com.gaffy.apps.mvvm.presentation.compose.theme.space8

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AutoCompleteText(
    @StringRes label: Int,
    value: String,
    showDropDown: Boolean,
    filterOpts: List<MuscleModel>,
    onValueChange: (String) -> Unit,
    onItemSelect: (MuscleModel) -> Unit = {},
    onDismissMenu: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ExposedDropdownMenuBox(expanded = showDropDown, onExpandedChange = { }) {
        TextField(
            value = TextFieldValue(value, selection = TextRange(value.length)),
            onValueChange = {
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
                    onClick = { onValueChange("") },
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
        ExposedDropdownMenu(expanded = showDropDown, onDismissRequest = onDismissMenu) {
            filterOpts.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        keyboardController?.hide()
                        onItemSelect(option)
                    }
                ) {
                    Text(
                        text = option.name,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AutoCompleteTextPreview() {
    val model = MuscleModel(1, "Biceps", false, "", "")
    AutoCompleteText(
        label = R.string.exercise_label,
        value = "Search Value",
        showDropDown = true,
        filterOpts = listOf(
            model,
            model.copy(name = "Triceps"),
            model.copy(name = "Chest")
        ),
        onValueChange = {},
        onItemSelect = { },
        onDismissMenu = {}
    )
}
