package com.rmaproject.myqoran.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.rmaproject.myqoran.components.MyQoranAppBar
import com.rmaproject.myqoran.ui.screen.home.components.JuzCardItem
import com.rmaproject.myqoran.ui.screen.home.components.PageCardItem
import com.rmaproject.myqoran.ui.screen.home.components.SurahCardItem
import com.rmaproject.myqoran.ui.screen.home.components.tabItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToReadQoran: (Int, Int?, Int?, Int?) -> Unit,
    navigateToSearch: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val surahState = viewModel.surahState.value
    val juzState = viewModel.juzState.value
    val pageState = viewModel.pageState.value

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranAppBar(
                goToSearch = { navigateToSearch() },
                openDrawer = openDrawer,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            TabRow(
                modifier = Modifier.height(36.dp),
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
                    .fillMaxSize()
            ) { currentPage ->
                when (currentPage) {
                    ORDER_BY_SURAH -> {
                        LazyColumn(
                            modifier = Modifier.offset(currentPageOffset.dp)
                        ) {
                            items(surahState.surahList) { surah ->
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

const val ORDER_BY_SURAH = 0
const val ORDER_BY_JUZ = 1
const val ORDER_BY_PAGE = 2