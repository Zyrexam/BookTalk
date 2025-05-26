package com.example.booktalk.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktalk.model.BookItem
import com.example.booktalk.utils.DetailViewState
import com.example.booktalk.utils.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainViewModel : ViewModel() {

    // Internal mutable state
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailViewState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)

    // Public immutable state
    val books: StateFlow<ViewState> = _viewState
    val bookDetails: StateFlow<DetailViewState> = _detailViewState

    private val jsonFormatter = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    private suspend fun loadJsonFromAssets(context: Context): List<BookItem> {
        val json = context.assets.open("books.json").bufferedReader().use { it.readText() }
        return jsonFormatter.decodeFromString(json)
    }

    fun getAllBooks(context: Context) = viewModelScope.launch {
        try {
            val bookList = loadJsonFromAssets(context)
            _viewState.value = ViewState.Success(bookList)
        } catch (e: Exception) {
            _viewState.value = ViewState.Error(e)
        }
    }

    fun getBookByID(context: Context, isbnNO: String) = viewModelScope.launch {
        try {
            val bookList = loadJsonFromAssets(context)
            val book = bookList.firstOrNull { it.isbn == isbnNO }
            if (book != null) {
                _detailViewState.value = DetailViewState.Success(book)
            } else {
                _detailViewState.value = DetailViewState.Error(Exception("Book not found"))
            }
        } catch (e: Exception) {
            _detailViewState.value = DetailViewState.Error(e)
        }
    }
}
