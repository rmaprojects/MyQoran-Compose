package com.rmaproject.myqoran.ui.screen.read

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReadQoranScreen(
    modifier: Modifier = Modifier,
    viewModel: ReadQoranViewModel = hiltViewModel()
) {
    Scaffold { innerPadding ->
        Column(
            Modifier.padding(innerPadding)
        ) {
            HorizontalPager(pageCount = 254) {

            }
        }
    }
}


@Preview
@Composable
fun ReadQoranScreenPreview() {
    ReadQoranScreen()
}