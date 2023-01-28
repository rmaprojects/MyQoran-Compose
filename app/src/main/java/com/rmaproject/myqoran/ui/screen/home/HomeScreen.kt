package com.rmaproject.myqoran.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.components.MyQoranAppBar
import com.rmaproject.myqoran.ui.navigation.MyQoranSharedViewModel
import com.rmaproject.myqoran.ui.screen.home.components.*
import com.rmaproject.myqoran.ui.screen.home.states.JuzState
import com.rmaproject.myqoran.ui.screen.home.states.PageState
import com.rmaproject.myqoran.ui.screen.home.states.SurahState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navigateToReadQoran: (Int, Int?, Int?, Int?) -> Unit,
    navigateToSearch: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    sharedViewModel: MyQoranSharedViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp

    val surahState = viewModel.surahState.value
    val juzState = viewModel.juzState.value
    val pageState = viewModel.pageState.value

    LaunchedEffect(surahState) {
        sharedViewModel.setTotalAyah(surahState.surahList)
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            MyQoranAppBar(
                goToSearch = navigateToSearch,
                openDrawer = openDrawer,
                currentDestination = null
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        sheetGesturesEnabled = true,
        sheetPeekHeight = (((screenHeight * 25) / 100) + 128).dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            SheetContent(
                pagerState = pagerState,
                surahState = surahState,
                juzState = juzState,
                pageState = pageState,
                navigateToReadQoran = navigateToReadQoran,
                scope = scope,
                expandBottomSheet = {
                    scope.launch {
                        if (bottomSheetState.isCollapsed)
                            bottomSheetState.expand()
                        else bottomSheetState.collapse()
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier.padding(innerPadding),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            HeaderCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                cardName = "Last Read",
                icon = Icons.Default.History,
                cardDescription = stringResource(R.string.desc_last_read),
                onClick = { /*TODO*/ }
            )
            HeaderCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                cardName = "Bookmark",
                icon = Icons.Default.Bookmark,
                cardDescription = stringResource(R.string.desc_bookmark_card),
                onClick = { /*TODO*/ }
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SheetContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    surahState: SurahState,
    juzState: JuzState,
    pageState: PageState,
    scope: CoroutineScope,
    navigateToReadQoran: (Int, Int?, Int?, Int?) -> Unit,
    expandBottomSheet: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { expandBottomSheet() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(Icons.Default.Minimize, contentDescription = "Expand Bottomsheet")
            }
        }
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

const val ORDER_BY_SURAH = 0
const val ORDER_BY_JUZ = 1
const val ORDER_BY_PAGE = 2