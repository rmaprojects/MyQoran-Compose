package com.rmaproject.myqoran.ui.screen.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.components.MyQoranAlertDialog
import com.rmaproject.myqoran.components.MyQoranAppBar
import com.rmaproject.myqoran.components.TopBarActionItem
import com.rmaproject.myqoran.data.local.entities.Bookmark
import com.rmaproject.myqoran.ui.screen.bookmark.component.BookmarkCard
import com.rmaproject.myqoran.ui.screen.bookmark.state.BookmarkState
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(
    navigateUp: () -> Unit,
    navigateToRead: (
        indexType: Int, surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, scrollPosition: Int?
    ) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {

    var isDialogShown by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val actionList = listOf(
        TopBarActionItem(
            text = stringResource(R.string.txt_delete_all_bookmark),
            icon = Icons.Default.DeleteForever,
            onClick = {
                if (viewModel.bookmarkSize.value == 0) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            context.getString(R.string.txt_no_bookmark)
                        )
                    }
                    return@TopBarActionItem
                }
                isDialogShown = true
            }
        )
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranAppBar(
                currentDestinationTitle = "Bookmark",
                navigateUp = navigateUp,
                actions = actionList,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        viewModel.bookmarkState.collectAsState(initial = BookmarkState.Empty).let { state ->
            val result = state.value
            BookmarkContent(
                state = result,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                navigateToRead = navigateToRead,
                deleteBookmark = { bookmark -> viewModel.deleteBookmark(bookmark) }
            )
        }

        if (isDialogShown) {
            MyQoranAlertDialog(
                icon = Icons.Default.DeleteForever,
                title = stringResource(R.string.txt_question_delete_all_bookmark),
                description = stringResource(R.string.txt_warn_cannot_undo),
                confirmButtonText = "Ok",
                dismissButtonText = stringResource(id = R.string.txt_cancel),
                onDismissClick = { isDialogShown = false },
                onConfirmClick = { viewModel.deleteAllBookmarks(); isDialogShown = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkContent(
    state: BookmarkState<List<Bookmark>>,
    navigateToRead: (indexType: Int, surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, scrollPosition: Int?) -> Unit,
    deleteBookmark: (Bookmark) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is BookmarkState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.txt_no_bookmark),
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
                itemsIndexed(
                    items = bookmarkList,
                    key = { _, item -> item.id!! }
                ) { _, bookmark ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToStart) {
                                deleteBookmark(bookmark)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            MaterialTheme.colorScheme.background
                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            BookmarkCard(
                                modifier = Modifier.padding(8.dp),
                                id = bookmark.id!!,
                                surahName = bookmark.surahName!!,
                                surahNumber = bookmark.surahNumber,
                                juzNumber = bookmark.juzNumber,
                                pageNumber = bookmark.pageNumber,
                                ayahNumber = bookmark.ayahNumber!!,
                                indexType = bookmark.indexType!!,
                                position = bookmark.positionScroll!!,
                                dateAdded = bookmark.timeStamp,
                                textQoran = bookmark.textQoran!!,
                                navigateToRead = navigateToRead
                            )
                        }
                    )
                }
            }
        }
    }
}