package com.sadturtleman.kuit.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarksUiState())
    val state = _state.asStateFlow()

    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.ToggleBookmark -> viewModelScope.launch {
                toggleBookmarkUseCase(intent.id)
                fetch()
            }
        }
    }

    init {
        load()
    }

    fun load() = viewModelScope.launch { fetch() }

    private suspend fun fetch() {
        runCatching { getBookmarkedBooksUseCase() }.onSuccess { books ->
            _state.update { it.copy(books = books) }
        }
    }
}
