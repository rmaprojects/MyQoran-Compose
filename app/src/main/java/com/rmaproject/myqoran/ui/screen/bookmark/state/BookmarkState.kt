package com.rmaproject.myqoran.ui.screen.bookmark.state

sealed class BookmarkState<out T: Any?> {
    data class NotEmpty<out T: Any?>(val data: T): BookmarkState<T>()
    object Empty: BookmarkState<Nothing>()
}