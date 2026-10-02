package com.sadturtleman.kuit.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.GetBookUseCase
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getBookUseCase: GetBookUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    private var bookId = 0

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.ToggleBookmark -> viewModelScope.launch {
                toggleBookmarkUseCase(bookId)
                fetch()
            }
        }
    }

    fun load(id: Int) {
        bookId = id
        viewModelScope.launch { fetch() }
    }

    private suspend fun fetch() {
        runCatching { getBookUseCase(bookId) }.onSuccess { book ->
            _state.update {
                it.copy(
                    isLoading = false,
                    book = book,
                    error = if (book == null) "책을 찾을 수 없어요" else null
                )
            }
        }.onFailure {
            _state.update { it.copy(isLoading = false, error = "에러") }
        }
    }
}
