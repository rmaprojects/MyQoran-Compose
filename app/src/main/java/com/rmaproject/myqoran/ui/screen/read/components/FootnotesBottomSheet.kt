package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FootNotesBottomSheet(
    footNotesContent: String,
    hideBottomSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                onClick = { hideBottomSheet() }
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close Footnote")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = footNotesContent,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}