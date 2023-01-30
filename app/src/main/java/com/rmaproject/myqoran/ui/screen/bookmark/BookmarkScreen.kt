package com.rmaproject.myqoran.ui.screen.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmaproject.myqoran.components.MyQoranAppBar
import com.rmaproject.myqoran.components.TopBarActionItem
import com.rmaproject.myqoran.data.local.entities.Bookmark
import com.rmaproject.myqoran.ui.screen.bookmark.component.BookmarkCard
import com.rmaproject.myqoran.ui.screen.bookmark.state.BookmarkState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navigateUp: () -> Unit,
    navigateToRead: (
        indexType: Int, surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, scrollPosition: Int?
    ) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {

    val actionList = listOf(
        TopBarActionItem(text = "Delete All Bookmark",
            icon = Icons.Default.DeleteForever,
            onClick = { viewModel.deleteAllBookmarks() }
        )
    )

    Scaffold(modifier = modifier, topBar = {
        MyQoranAppBar(
            currentDestinationTitle = "Bookmarks",
            navigateUp = navigateUp,
            actions = actionList,
        )
    }) { innerPadding ->
        viewModel.bookmarkState.collectAsState(initial = BookmarkState.Empty).let { state ->
            val result = state.value
            BookmarkContent(
                state = result,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                navigateToRead = navigateToRead
            )
        }
    }
}

@Composable
fun BookmarkContent(
    state: BookmarkState<List<Bookmark>>,
    navigateToRead: (indexType: Int, surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, scrollPosition: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is BookmarkState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Belum ada bookmark yang ditambahkan",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
        }
        is BookmarkState.NotEmpty -> {
            val bookmarkList = state.data
            LazyColumn(
                modifier.fillMaxSize()
            ) {
                items(items = bookmarkList, key = { it.id!! }) { bookmark ->
                    BookmarkCard(
                        id = bookmark.id!!,
                        surahName = bookmark.surahName!!,
                        surahNumber = bookmark.surahNumber,
                        juzNumber = bookmark.juzNumber,
                        pageNumber = bookmark.pageNumber,
                        ayahNumber = bookmark.ayahNumber!!,
                        indexType = bookmark.indexType!!,
                        position = bookmark.positionScroll!!,
                        dateAdded = bookmark.timeAdded,
                        textQoran = bookmark.textQoran!!,
                        navigateToRead = navigateToRead
                    )
                }
            }
        }
    }
}