package com.rmaproject.myqoran.ui.screen.read

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.ui.navigation.MyQoranSharedViewModel
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_JUZ
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_PAGE
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH
import com.rmaproject.myqoran.ui.screen.read.components.*
import com.rmaproject.myqoran.ui.screen.read.events.PlayAyahEvent
import com.rmaproject.myqoran.ui.screen.read.events.ReadQoranEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class, ExperimentalMaterialApi::class
)
@Composable
fun ReadQoranScreen(
    navigateUp: () -> Unit,
    sharedViewModel: MyQoranSharedViewModel,
    modifier: Modifier = Modifier,
    viewModel: ReadQoranViewModel = hiltViewModel()
) {

    var pageTotal = SURAH_TOTAL + 1

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val pagerState = rememberPagerState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val totalAyahs = remember {
        sharedViewModel.getTotalAyah()
    }

    val qoranAyahList = viewModel.qoranState.value.listAyah
    val currentPlayingAyah = viewModel.currentPlayedAyah.value
    val playType = viewModel.playerType

    LaunchedEffect(Unit) {
        scope.launch {
            when (viewModel.indexType) {
                ORDER_BY_SURAH -> {
                    pageTotal = SURAH_TOTAL + 1
                    pagerState.scrollToPage(viewModel.surahNumber)
                }
                ORDER_BY_JUZ -> {
                    pageTotal = JUZ_TOTAL + 1
                    pagerState.scrollToPage(viewModel.juzNumber)
                }
                ORDER_BY_PAGE -> {
                    pageTotal = PAGE_TOTAL + 1
                    pagerState.scrollToPage(viewModel.pageNumber)

                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.onEvent(ReadQoranEvent.ChangePage(page))
        }
    }

    viewModel.observeAbleAyah.observe(lifecycleOwner) { ayahList ->
        viewModel.onEvent(ReadQoranEvent.GetNewAyah(ayahList))
    }

    viewModel.observeAbleCurrentReading.observe(lifecycleOwner) { qoranList ->
        scope.launch {
            delay(200)
            val currentReading =
                when (viewModel.indexType) {
                    ORDER_BY_SURAH -> qoranList[pagerState.currentPage - 1].surahNameEn
                    ORDER_BY_JUZ -> context.resources.getString(
                        R.string.txt_juz,
                        qoranList[pagerState.currentPage - 1].juzNumber.toString()
                    )
                    ORDER_BY_PAGE -> context.resources.getString(
                        R.string.txt_page,
                        qoranList[pagerState.currentPage - 1].page.toString()
                    )
                    else -> throw Exception("Unknown Type")
                }
            viewModel.onEvent(ReadQoranEvent.SetCurrentReading(currentReading ?: ""))
        }
    }

    ReadQoranDrawer(
        modifier = modifier,
        drawerState = drawerState,
        indexType = viewModel.indexType,
        indexList = viewModel.indexList,
        currentPage = pagerState.currentPage,
        onIndexClick = { page ->
            scope.launch { pagerState.scrollToPage(page); drawerState.close() }
        }
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ModalBottomSheetLayout(
                sheetState = bottomSheetState,
                sheetShape = MaterialTheme.shapes.large,
                sheetBackgroundColor = MaterialTheme.colorScheme.surface,
                sheetContent = {
                    FootNotesBottomSheet(
                        footNotesContent = "Hello World!",
                        hideBottomSheet = { scope.launch { bottomSheetState.hide() } }
                    )
                }
            ) {
                Scaffold(
                    topBar = {
                        ReadTopBar(
                            navigateUp = navigateUp,
                            openDrawer = { scope.launch { drawerState.open() } },
                            currentSurahOrAyahOrJuz = viewModel.currentReadingState.value
                        )
                    },
                    bottomBar = {
                        if (
                            playType.value == ReadQoranViewModel.PlayType.PLAY_SINGLE ||
                            playType.value == ReadQoranViewModel.PlayType.PLAY_ALL
                        ) {
                            PlayerControlPanelBottomBar(
                                currentPlaying = currentPlayingAyah,
                                playType = playType.value,
                                onSkipNextClick = { viewModel.onPlayAyahEvent(PlayAyahEvent.SkipNext) },
                                onPlayPauseClick = { viewModel.onPlayAyahEvent(PlayAyahEvent.PlayPauseAyah) },
                                onSkipPrevClick = { viewModel.onPlayAyahEvent(PlayAyahEvent.SkipPrevious) },
                                onStopClick = { viewModel.onPlayAyahEvent(PlayAyahEvent.StopAyah) }
                            )
                        }
                    }
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
                                    qoranAyahList.size
                                ) { index ->
                                    val qoran = qoranAyahList[index]
                                    if (qoran.ayahNumber == 1) {
                                        ItemSurahCard(
                                            modifier = Modifier.padding(vertical = 12.dp),
                                            surahName = qoran.surahNameEn!!,
                                            surahNameAr = qoran.surahNameAr!!,
                                            totalAyah = totalAyahs?.get(index)!!,
                                            descendPlace = qoran.surahDescendPlace!!
                                        )
                                    }
                                    ReadControlPanel(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        onPlayAyahClick = {
                                            viewModel.onEvent(
                                                ReadQoranEvent.PlayAyah(
                                                    ayahNumber = qoran.ayahNumber!!,
                                                    surahNumber = qoran.surahNumber!!,
                                                    surahName = qoran.surahNameEn!!
                                                )
                                            )
                                        },
                                        onCopyAyahClick = {
                                            viewModel.onEvent(
                                                ReadQoranEvent.CopyAyah(
                                                    context,
                                                    surahName = qoran.surahNameEn ?: "",
                                                    ayahText = qoran.ayahText ?: "",
                                                    translation = qoran.translation_id ?: ""
                                                )
                                            )
                                        },
                                        onShareAyahClick = {
                                            viewModel.onEvent(
                                                ReadQoranEvent.ShareAyah(
                                                    context,
                                                    surahName = qoran.surahNameEn ?: "",
                                                    ayahText = qoran.ayahText ?: "",
                                                    translation = qoran.translation_id ?: ""
                                                )
                                            )
                                        },
                                        onBookmarkAyahClick = {
                                            viewModel.onEvent(
                                                ReadQoranEvent.SaveBookmark(
                                                    surahName = qoran.surahNameEn!!,
                                                    surahNumber = qoran.surahNumber,
                                                    ayahNumber = qoran.ayahNumber!!,
                                                    juzNumber = qoran.juzNumber,
                                                    pageNumber = qoran.page,
                                                    position = index,
                                                    qoranTextAr = qoran.ayahText!!,
                                                    indexType = viewModel.indexType
                                                )
                                            )
                                        }
                                    )
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
}

private const val SURAH_TOTAL = 114
private const val PAGE_TOTAL = 604
private const val JUZ_TOTAL = 30

@Preview
@Composable
fun ReadQoranScreenPreview() {
    ReadQoranScreen({}, MyQoranSharedViewModel())
}