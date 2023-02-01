package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_JUZ
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_PAGE
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadQoranDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    indexType: Int,
    indexList: List<Qoran>,
    onIndexClick: (Int) -> Unit,
    navigateToSearchAyah: () -> Unit,
    currentPage: Int,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(modifier = modifier, drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                DrawerHeader(
                    currentIndex = indexType,
                    indexList = indexList,
                    onIndexClick = onIndexClick,
                    currentPage = currentPage,
                    navigateToSearchAyah = navigateToSearchAyah
                )
            }
        }, drawerState = drawerState) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    currentIndex: Int,
    indexList: List<Qoran>,
    onIndexClick: (Int) -> Unit,
    navigateToSearchAyah: () -> Unit,
    currentPage: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp),
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = navigateToSearchAyah,
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search Ayah")
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = when (currentIndex) {
                    ORDER_BY_SURAH -> "Pilih Surat"
                    ORDER_BY_JUZ -> "Pilih Juz"
                    ORDER_BY_PAGE -> "Pilih Halaman"
                    else -> throw Exception("Unknown Index")
                },
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(indexList) { qoran ->
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onIndexClick(qoran.surahNumber!!) },
                    enabled = currentPage != qoran.surahNumber,
                    colors = ButtonDefaults.textButtonColors(
                        disabledContainerColor = if (currentPage == qoran.surahNumber) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent,
                        disabledContentColor = if (currentPage == qoran.surahNumber) MaterialTheme.colorScheme.primary
                        else Color.Transparent
                    )
                ) {
                    Text(
                        text = when (currentIndex) {
                            ORDER_BY_SURAH -> qoran.surahNameEn!!
                            ORDER_BY_JUZ -> "Juz ${qoran.juzNumber}"
                            ORDER_BY_PAGE -> "Halaman ${qoran.page}"
                            else -> throw Exception("Unknown Order")
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevHead() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        DrawerHeader(
            currentIndex = 1,
            indexList = emptyList(),
            onIndexClick = {},
            navigateToSearchAyah = { /*TODO*/ },
            currentPage = 1
        )
    }
}