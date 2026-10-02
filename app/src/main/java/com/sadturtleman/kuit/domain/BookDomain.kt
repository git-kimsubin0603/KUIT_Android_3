package com.sadturtleman.kuit.domain

interface BookRepository {
    suspend fun getBooks(): List<Book>
    suspend fun getBook(id: Int): Book?
    suspend fun toggleBookmark(id: Int)
}

class GetBookUseCase(private val repo: BookRepository) {
    suspend operator fun invoke(): List<Book> = repo.getBooks()
    suspend operator fun invoke(id: Int): Book? = repo.getBook(id)
}

class ToggleBookmarkUseCase(private val repo: BookRepository) {
    suspend operator fun invoke(id: Int) = repo.toggleBookmark(id)
}

class GetBookmarkedBooksUseCase(private val repo: BookRepository) {
    suspend operator fun invoke(): List<Book> = repo.getBooks().filter { it.isBookmarked }
}
