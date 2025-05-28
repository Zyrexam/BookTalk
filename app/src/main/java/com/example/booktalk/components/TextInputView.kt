package com.example.booktalk.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.booktalk.ui.theme.typography

@Composable
fun LabelView(title: String) {
    Text(
        text = title,
        style = typography.bodySmall,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.primary
    )
}

@ExperimentalComposeUiApi
@Composable
fun TextInputField(label: String, value: String, onValueChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { LabelView(title = label) },
        textStyle = typography.bodyLarge,
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
        })
    )
}

@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.primaryContainer,
    unfocusedTextColor = MaterialTheme.colorScheme.primaryContainer,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor = Color.LightGray,
    cursorColor = MaterialTheme.colorScheme.onSurface,
    disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
)