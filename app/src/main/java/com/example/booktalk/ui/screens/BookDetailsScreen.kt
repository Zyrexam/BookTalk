package com.example.booktalk.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.booktalk.R
import com.example.booktalk.components.BookDetailsCard
import com.example.booktalk.components.TopBar
import com.example.booktalk.navigation.MainActions
import com.example.booktalk.ui.theme.typography
import com.example.booktalk.utils.DetailViewState
import com.example.booktalk.viewModel.MainViewModel

@Composable
fun BookDetailsScreen(viewModel: MainViewModel, actions: MainActions) {
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.text_bookDetails), action = actions)
    }) { paddingValues ->
        BookDetails(
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun BookDetails(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val result by viewModel.bookDetails.collectAsState()

    when (result) {
        DetailViewState.Loading -> Text(text = "Loading")
        is DetailViewState.Error -> Text(text = "Error found: ${(result as DetailViewState.Error).exception}")
        is DetailViewState.Success -> {
            val book = (result as DetailViewState.Success).data

            LazyColumn(modifier = modifier) {
                // Book Details Card
                item {
                    BookDetailsCard(
                        title = book.title,
                        authors = book.authors,
                        thumbnailUrl = book.thumbnailUrl,
                        categories = book.categories
                    )
                }

                // Description
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(id = R.string.text_bookDetails),
                        style = typography.titleMedium,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = book.longDescription,
                        style = typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(0.7F),
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    )
                }
            }
        }
        DetailViewState.Empty -> Text("No results found!")
    }
}