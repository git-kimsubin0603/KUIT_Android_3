package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.Book

data class BookDto(
    val book_id: Int,
    val book_title: String,
    val author_name: String,
    val page_count: Int,
    val published_year: Int,
    val summary: String,
    val is_bookmarked: Boolean = false
)

fun BookDto.toBook() = Book(
    id = book_id,
    title = book_title,
    author = author_name,
    pages = page_count,
    year = published_year,
    description = summary,
    isBookmarked = is_bookmarked
)
