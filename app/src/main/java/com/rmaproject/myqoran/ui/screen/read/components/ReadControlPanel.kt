package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ReadControlPanel(
    ayahNumber: Int,
    onPlayAyahClick: () -> Unit,
    onCopyAyahClick: () -> Unit,
    onShareAyahClick: () -> Unit,
    onBookmarkAyahClick: () -> Unit,
    modifier: Modifier = Modifier,
    isOnSearch: Boolean = false,
    surahName: String = ""
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = "Rotation"
    )

    ElevatedCard(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            horizontalArrangement =
            if (isExpanded) Arrangement.SpaceAround
            else Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isExpanded) {
                IconButton(onClick = { onShareAyahClick(); isExpanded = false }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = null
                    )
                }
                IconButton(onClick = { onCopyAyahClick(); isExpanded = false }) {
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = null
                    )
                }
                if (!isOnSearch) {
                    IconButton(onClick = { onPlayAyahClick(); isExpanded = false }) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { onBookmarkAyahClick(); isExpanded = false }) {
                        Icon(
                            Icons.Default.Bookmark,
                            contentDescription = null
                        )
                    }
                }
            }
            if (isOnSearch && !isExpanded) {
                Text(
                    text = "$surahName: $ayahNumber",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            if (!isExpanded && !isOnSearch) {
                Box(
                    modifier = Modifier
                        .width(56.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Circle,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "$ayahNumber",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.rotate(rotationState)
            ) {
                Icon(
                    Icons.Default.ArrowBackIos,
                    contentDescription = null
                )
            }
        }
    }
}