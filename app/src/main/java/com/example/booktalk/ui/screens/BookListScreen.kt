package com.example.booktalk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.booktalk.R
import com.example.booktalk.components.ItemBookList
import com.example.booktalk.components.TextInputField
import com.example.booktalk.model.BookItem
import com.example.booktalk.navigation.MainActions
import com.example.booktalk.utils.ViewState
import com.example.booktalk.viewModel.MainViewModel

@ExperimentalComposeUiApi
@Composable
fun BookListScreen(viewModel: MainViewModel, actions: MainActions) {
    val result by viewModel.books.collectAsState()

    when (result) {
        ViewState.Loading -> Text(text = "Loading")
        is ViewState.Error -> Text(text = "Error found: ${(result as ViewState.Error).exception}")
        is ViewState.Success -> {
            BookList((result as ViewState.Success).data, actions)
        }
        ViewState.Empty -> Text("No results found!")
    }
}

@ExperimentalComposeUiApi
@Composable
fun BookList(bookList: List<BookItem>, actions: MainActions) {
    val search = rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 24.dp
        ),
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {
        // Title
        item {
            Text(
                text = "Explore thousands of \nbooks in go",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primaryContainer,
                maxLines = 2,
                modifier = Modifier.padding(start = 16.dp, end = 24.dp, bottom = 24.dp)
            )
        }

        // Search
        item {
            TextInputField(
                label = stringResource(R.string.text_search),
                value = search.value,
                onValueChanged = {
                    search.value = it
                }
            )
        }

        // Search results title
        item {
            Text(
                text = "Famous books",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        // Filtered books list
        val filteredBooks = bookList.filter {
            it.title.contains(search.value, ignoreCase = true)
        }

        items(
            items = filteredBooks,
            key = { it.isbn }
        ) { book ->
            ItemBookList(
                title = book.title,
                author = book.authors.joinToString(", "),
                thumbnailUrl = book.thumbnailUrl,
                categories = book.categories,
                onItemClick = {
                    actions.gotoBookDetails.invoke(book.isbn)
                }
            )
        }
    }
}