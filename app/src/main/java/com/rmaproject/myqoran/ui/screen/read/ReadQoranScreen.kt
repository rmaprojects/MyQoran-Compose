package com.rmaproject.myqoran.ui.screen.read

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_JUZ
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_PAGE
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH
import com.rmaproject.myqoran.ui.screen.read.components.ItemReadAyah
import com.rmaproject.myqoran.ui.screen.read.components.ReadQoranDrawer
import com.rmaproject.myqoran.ui.screen.read.components.ReadTopBar
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
fun ReadQoranScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReadQoranViewModel = hiltViewModel()
) {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val qoranAyahList = viewModel.qoranState.value.listAyah

    val pageTotal = when (viewModel.indexType) {
        ORDER_BY_SURAH -> SURAH_TOTAL
        ORDER_BY_JUZ -> JUZ_TOTAL
        ORDER_BY_PAGE -> PAGE_TOTAL
        else -> throw Exception("Unknown Type")
    }

    LaunchedEffect(Unit) {
        scope.launch {
            when (viewModel.indexType) {
                ORDER_BY_SURAH -> pagerState.scrollToPage(viewModel.surahNumber)
                ORDER_BY_JUZ -> pagerState.scrollToPage(viewModel.juzNumber)
                ORDER_BY_PAGE -> pagerState.scrollToPage(viewModel.pageNumber)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.changePage(page)
        }
    }

    viewModel.observeAbleAyah.observe(lifecycleOwner) { ayahList ->
        viewModel.getNewAyah(ayahList)
    }

    ReadQoranDrawer(
        modifier = modifier,
        drawerState = drawerState
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Scaffold(
                topBar = {
                    ReadTopBar(
                        navigateUp = navigateUp,
                        openDrawer = { scope.launch { drawerState.open() } },
                        currentSurahOrAyahOrJuz = ""
                    )
                },
            ) { innerPadding ->
                Column(
                    Modifier.padding(innerPadding)
                ) {
                    HorizontalPager(
                        count = pageTotal,
                        state = pagerState,
                        userScrollEnabled = false
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(12.dp)
                        ) {
                            items(
                                qoranAyahList
                            ) { qoran ->
                                ItemReadAyah(
                                    ayahText = qoran.ayahText,
                                    ayahTranslate = qoran.translation_id
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private const val SURAH_TOTAL = 114
private const val PAGE_TOTAL = 604
private const val JUZ_TOTAL = 30


@Preview
@Composable
fun ReadQoranScreenPreview() {
    ReadQoranScreen({})
}