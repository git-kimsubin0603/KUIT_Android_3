package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.Book
import com.sadturtleman.kuit.domain.BookRepository

class FakeBookRepository : BookRepository {
    private var dtoList = listOf(
        BookDto(1, "Kotlin in Action", "Dmitry Jemerov", 560, 2017, "코틀린 언어의 핵심 문법과 활용법을 다루는 책.", is_bookmarked = true),
        BookDto(2, "Clean Architecture", "Robert C. Martin", 432, 2017, "소프트웨어 구조와 설계 원칙을 다루는 책. 의존성 규칙, 경계, 계층 분리에 대해 설명한다."),
        BookDto(3, "Jetpack Compose 완벽 가이드", "KUIT", 300, 2024, "Compose로 선언형 UI를 만드는 방법을 다룬다.", is_bookmarked = true),
        BookDto(4, "Refactoring UI", "Adam Wathan", 252, 2018, "개발자를 위한 UI 디자인 팁 모음."),
        BookDto(5, "Effective Kotlin", "Marcin Moskała", 400, 2019, "코틀린을 더 잘 쓰기 위한 모범 사례.")
    )

    override suspend fun getBooks(): List<Book> {
        return dtoList.map { it.toBook() }
    }

    override suspend fun getBook(id: Int): Book? {
        return dtoList.firstOrNull { it.book_id == id }?.toBook()
    }

    override suspend fun toggleBookmark(id: Int) {
        dtoList = dtoList.map {
            if (it.book_id == id) it.copy(is_bookmarked = !it.is_bookmarked) else it
        }
    }
}
