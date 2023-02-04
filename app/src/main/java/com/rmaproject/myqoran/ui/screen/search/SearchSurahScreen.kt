package com.rmaproject.myqoran.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmaproject.myqoran.components.MyQoranAppBar
import com.rmaproject.myqoran.data.local.entities.SearchSurahResult
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH
import com.rmaproject.myqoran.ui.screen.home.components.SurahCardItem
import com.rmaproject.myqoran.ui.screen.search.components.SearchField
import com.rmaproject.myqoran.ui.screen.search.event.SearchEvent
import com.rmaproject.myqoran.ui.screen.search.event.SearchUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSurahScreen(
    navigateUp: () -> Unit,
    navigateToReadQoran: (Int, Int?, Int?, Int?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val textFieldState = viewModel.searchQuery.value

    LaunchedEffect(textFieldState) {
        viewModel.onEvent(SearchEvent.FindSurah)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranAppBar(
                currentDestinationTitle = "Search Surah",
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                query = textFieldState.text,
                onValueChange = { newValue ->
                    viewModel.onEvent(SearchEvent.EnterQuery(newValue))
                },
                displayHint = textFieldState.isHintVisible,
                onFocusState = { newFocusState ->
                    viewModel.onEvent(SearchEvent.QueryChangeFocus(newFocusState))
                },
            )
            viewModel.searchSurahState.collectAsState(initial = SearchUiState.EmptyQuery)
                .let { state ->
                    val event = state.value
                    SearchSurahContent(
                        navigateToReadQoran = navigateToReadQoran,
                        state = event
                    )
                }
        }
    }
}

@Composable
fun SearchSurahContent(
    navigateToReadQoran: (Int, Int?, Int?, Int?) -> Unit,
    state: SearchUiState<List<SearchSurahResult>>,
) {
    when (state) {
        is SearchUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Tidak ditemukan surat dengan nama \"${state.query}\"",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
        }
        is SearchUiState.EmptyQuery -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Kotak pencarian masih kosong, isi dulu",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
        }
        is SearchUiState.NotEmpty -> {
            LazyColumn {
                itemsIndexed(
                    items = state.data
                ) { index, surah ->
                    SurahCardItem(
                        modifier = Modifier.padding(8.dp),
                        ayahNumber = index + 1,
                        surahName = surah.surahNameEn!!,
                        surahNameId = surah.surahNameId!!,
                        surahNameAr = surah.surahNameAr!!,
                        navigateToReadQoran = {
                            navigateToReadQoran(
                                ORDER_BY_SURAH,
                                surah.surahNumber,
                                null,
                                null
                            )
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SearchSurahScreenPreview() {
    SearchSurahScreen(
        {},
        { _, _, _, _ -> }
    )
}