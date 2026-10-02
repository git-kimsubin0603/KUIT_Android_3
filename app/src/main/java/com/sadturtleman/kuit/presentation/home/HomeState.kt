package com.sadturtleman.kuit.presentation.home

import com.sadturtleman.kuit.domain.Book

data class HomeState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
) {
    val bookmarkCount: Int get() = books.count { it.isBookmarked }
}

sealed interface HomeIntent {
    data object Refresh : HomeIntent
    data class ToggleBookmark(val id: Int) : HomeIntent
}
