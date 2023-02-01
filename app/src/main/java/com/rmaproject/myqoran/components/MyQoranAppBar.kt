package com.rmaproject.myqoran.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyQoranAppBar(
    currentDestinationTitle: String,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    actions: List<TopBarActionItem> = emptyList(),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = currentDestinationTitle) },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            if (actions.isNotEmpty())
                actions.map { item ->
                    IconButton(onClick = item.onClick) {
                        Icon(item.icon, contentDescription = item.text)
                    }
                }
        }
    )
}


@Preview
@Composable
fun MyQoranAppBarPreview() {
    val list = listOf(
        TopBarActionItem(
            text = "Delete All",
            icon = Icons.Default.DeleteForever
        ) {}
    )
    MyQoranAppBar(
        currentDestinationTitle = "",
        {},
        actions = list
    )
}

data class TopBarActionItem(
    val text: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)