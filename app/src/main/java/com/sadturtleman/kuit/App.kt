package com.sadturtleman.kuit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sadturtleman.kuit.data.FakeBookRepository
import com.sadturtleman.kuit.domain.GetBookUseCase
import com.sadturtleman.kuit.domain.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.ToggleBookmarkUseCase
import com.sadturtleman.kuit.presentation.bookmarks.BookmarksScreen
import com.sadturtleman.kuit.presentation.bookmarks.BookmarksViewModel
import com.sadturtleman.kuit.presentation.detail.DetailScreen
import com.sadturtleman.kuit.presentation.detail.DetailViewModel
import com.sadturtleman.kuit.presentation.home.HomeScreen
import com.sadturtleman.kuit.presentation.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val id: Int) : NavKey

@Serializable
data object Bookmarks : NavKey

private val repository = FakeBookRepository()

@Composable
fun App() {
    val backStack = rememberNavBackStack(Home)

    val homeVm: HomeViewModel = viewModel {
        HomeViewModel(GetBookUseCase(repository), ToggleBookmarkUseCase(repository))
    }
    val detailVm: DetailViewModel = viewModel {
        DetailViewModel(GetBookUseCase(repository), ToggleBookmarkUseCase(repository))
    }
    val bookmarksVm: BookmarksViewModel = viewModel {
        BookmarksViewModel(GetBookmarkedBooksUseCase(repository), ToggleBookmarkUseCase(repository))
    }

    fun goBack() {
        homeVm.load()
        bookmarksVm.load()
        backStack.removeLastOrNull()
    }

    NavDisplay(
        backStack = backStack,
        onBack = { goBack() },
        entryProvider = entryProvider {
            entry<Home> {
                val state by homeVm.state.collectAsStateWithLifecycle()

                HomeScreen(
                    state = state,
                    onIntent = homeVm::onIntent,
                    onBookClick = { id ->
                        detailVm.load(id)
                        backStack.add(Detail(id))
                    },
                    onBookmarksClick = {
                        bookmarksVm.load()
                        backStack.add(Bookmarks)
                    }
                )
            }

            entry<Detail> {
                val state by detailVm.state.collectAsStateWithLifecycle()

                DetailScreen(state = state, onIntent = detailVm::onIntent, onBack = { goBack() })
            }

            entry<Bookmarks> {
                val state by bookmarksVm.state.collectAsStateWithLifecycle()

                BookmarksScreen(
                    state = state,
                    onIntent = bookmarksVm::onIntent,
                    onBookClick = { id ->
                        detailVm.load(id)
                        backStack.add(Detail(id))
                        Log.d("BackStack", backStack.toList().toString())
                    },
                    onBack = { goBack() }
                )
            }
        }
    )
}
