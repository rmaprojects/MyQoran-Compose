package com.rmaproject.myqoran.ui.screen.bookmark

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaproject.myqoran.data.local.entities.Bookmark
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.ui.screen.bookmark.state.BookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: QoranRepository
) : ViewModel() {

    private val _bookmarkState: MutableStateFlow<BookmarkState<List<Bookmark>>> = MutableStateFlow(
        BookmarkState.Empty
    )
    val bookmarkState: StateFlow<BookmarkState<List<Bookmark>>> = _bookmarkState.asStateFlow()
    private val _bookmarkSize = MutableStateFlow(0)
    val bookmarkSize = _bookmarkSize.asStateFlow()


    init {
        getAllBookmarks()
    }

    private fun getAllBookmarks() {
        viewModelScope.launch {
            repository.getBookmarks().collect { bookmarkList ->
                _bookmarkSize.value = bookmarkList.size
                if (bookmarkList.isEmpty()) _bookmarkState.emit(BookmarkState.Empty)
                else _bookmarkState.emit(BookmarkState.NotEmpty(bookmarkList))
            }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch {
            repository.deleteAllBookmark()
        }
    }

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }
}