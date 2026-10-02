package com.sadturtleman.kuit.presentation.bookmarks

import com.sadturtleman.kuit.domain.Book

data class BookmarksUiState(
    val books: List<Book> = emptyList()
)

sealed interface BookmarksIntent {
    data class ToggleBookmark(val id: Int) : BookmarksIntent
}
