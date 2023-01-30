package com.rmaproject.myqoran.ui.screen.bookmark.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.ui.theme.BookmarkArabicStyle
import com.rmaproject.myqoran.utils.DateConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkCard(
    id: Int,
    indexType: Int,
    surahName: String,
    surahNumber: Int?,
    ayahNumber: Int,
    juzNumber: Int?,
    pageNumber: Int?,
    position: Int,
    dateAdded: Long,
    textQoran: String,
    navigateToRead: (
        indexType: Int,
        surahNumber: Int?,
        juzNumber: Int?,
        pageNumber: Int?,
        scrollPosition: Int?
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier,
        onClick = {
            navigateToRead(
                indexType,
                surahNumber,
                juzNumber,
                pageNumber,
                position
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            )  {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .width(64.dp)
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.Circle,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "$id",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(4f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        text = surahName,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        text = "Ayat $ayahNumber",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight()
                        .weight(2f)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Date Added:\n${DateConverter.convertMillisToActualDate(dateAdded)}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Text(
                modifier = Modifier.fillMaxSize(),
                text = textQoran,
                style = BookmarkArabicStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}