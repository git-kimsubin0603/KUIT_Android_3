package com.sadturtleman.kuit.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(state: DetailUiState, onIntent: (DetailIntent) -> Unit, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("책 상세") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text("←", style = MaterialTheme.typography.headlineSmall)
                }
            }
        )
    }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val book = state.book
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                book == null -> Text(state.error ?: "에러", Modifier.align(Alignment.Center))
                else -> Column(Modifier.padding(16.dp)) {
                    Text(book.title, style = MaterialTheme.typography.headlineSmall)
                    Text(book.author)
                    Text("${book.pages}p · ${book.year}")
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { onIntent(DetailIntent.ToggleBookmark) }) {
                        Text(if (book.isBookmarked) "★ 북마크 해제" else "☆ 북마크에 추가")
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("소개", style = MaterialTheme.typography.titleMedium)
                    Text(book.description)
                }
            }
        }
    }
}
