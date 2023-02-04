package com.rmaproject.myqoran.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.components.MyQoranHomeAppBar
import com.rmaproject.myqoran.data.kotpref.LastReadPreferences
import com.rmaproject.myqoran.ui.navigation.MyQoranSharedViewModel
import com.rmaproject.myqoran.ui.screen.home.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToReadQoran: (Int, Int?, Int?, Int?) -> Unit,
    navigateLastRead: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToBookmark: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    sharedViewModel: MyQoranSharedViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val surahState = viewModel.surahState.value
    val juzState = viewModel.juzState.value
    val pageState = viewModel.pageState.value

    LaunchedEffect(surahState) {
        sharedViewModel.setTotalAyah(surahState.surahList)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranHomeAppBar(
                goToSearch = navigateToSearch,
                openDrawer = openDrawer,
                currentDestination = null
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HeaderCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        cardName = stringResource(R.string.txt_last_read),
                        icon = Icons.Default.History,
                        cardDescription =
                        if (LastReadPreferences.surahName == null) stringResource(R.string.txt_desc_no_last_read)
                        else stringResource(R.string.txt_desc_continue_read, "\n${LastReadPreferences.surahName}: ${LastReadPreferences.ayahNumber}"),
                        onClick = navigateLastRead
                    )
                    HeaderCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        cardName = "Bookmark",
                        icon = Icons.Default.Bookmark,
                        cardDescription = stringResource(R.string.desc_bookmark_card),
                        onClick = navigateToBookmark
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f)
            ) {
                TabRow(
                    modifier = Modifier.height(48.dp),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    backgroundColor = MaterialTheme.colorScheme.surface
                ) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.onSurface
                        ) {
                            Text(text = tabItem.title)
                        }
                    }
                }
                HorizontalPager(
                    count = tabItems.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                ) { currentPage ->
                    when (currentPage) {
                        ORDER_BY_SURAH -> {
                            LazyColumn(
                                modifier = Modifier.offset(currentPageOffset.dp),
                            ) {
                                items(
                                    surahState.surahList,
                                    key = { it.id!! }
                                ) { surah ->
                                    SurahCardItem(
                                        ayahNumber = surah.surahNumber!!,
                                        surahName = surah.surahNameEN!!,
                                        surahNameId = surah.surahNameID!!,
                                        surahNameAr = surah.surahNameArabic!!,
                                        navigateToReadQoran = {
                                            navigateToReadQoran(
                                                ORDER_BY_SURAH,
                                                surah.surahNumber, null, null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        ORDER_BY_JUZ -> {
                            LazyColumn(
                                modifier = Modifier.offset(currentPageOffset.dp)
                            ) {
                                items(juzState.juzList) { juz ->
                                    JuzCardItem(
                                        juzNumber = juz.juzNumber!!,
                                        navigateToReadQoran = {
                                            navigateToReadQoran(
                                                ORDER_BY_JUZ,
                                                null,
                                                juz.juzNumber,
                                                null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        ORDER_BY_PAGE -> {
                            LazyColumn(
                                modifier = Modifier.offset(currentPageOffset.dp)
                            ) {
                                items(pageState.qoranByPageList) { qoranByPage ->
                                    PageCardItem(
                                        pageNumber = qoranByPage.pageNumber!!,
                                        navigateToReadQoran = {
                                            navigateToReadQoran(
                                                ORDER_BY_PAGE,
                                                null,
                                                null,
                                                qoranByPage.pageNumber
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

const val ORDER_BY_SURAH = 0
const val ORDER_BY_JUZ = 1
const val ORDER_BY_PAGE = 2