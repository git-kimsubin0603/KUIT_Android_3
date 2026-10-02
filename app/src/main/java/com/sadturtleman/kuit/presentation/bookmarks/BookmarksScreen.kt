package com.sadturtleman.kuit.presentation.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.presentation.home.BookRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    state: BookmarksUiState,
    onIntent: (BookmarksIntent) -> Unit,
    onBookClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("북마크") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text("←", style = MaterialTheme.typography.headlineSmall)
                }
            }
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.books, key = { it.id }) { book ->
                BookRow(
                    book,
                    { onBookClick(book.id) },
                    { onIntent(BookmarksIntent.ToggleBookmark(book.id)) }
                )
            }
        }
    }
}
