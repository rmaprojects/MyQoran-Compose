package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadTopBar(
    navigateUp: () -> Unit,
    openDrawer: () -> Unit,
    currentSurahOrAyahOrJuz: String?,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = currentSurahOrAyahOrJuz ?: "Read Qoran") },
        actions = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Open Selector")
            }
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back to List")
            }
        }
    )
}