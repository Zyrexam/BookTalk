package com.example.booktalk.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.booktalk.R
import com.example.booktalk.navigation.MainActions
import com.example.booktalk.ui.theme.typography

@Composable
fun TopBar(title: String, action: MainActions) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.text_back_button),
            modifier = Modifier.clickable(onClick = action.upPress)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}