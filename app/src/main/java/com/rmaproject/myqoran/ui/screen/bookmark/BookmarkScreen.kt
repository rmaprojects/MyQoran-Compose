package com.rmaproject.myqoran.ui.screen.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}


@Preview
@Composable
fun BookmarkScreenPreview() {
    BookmarkScreen()
}