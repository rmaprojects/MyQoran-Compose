package com.rmaproject.myqoran.ui.screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.ui.theme.MyQoranComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahCardItem(
    ayahNumber: Int,
    surahName: String,
    surahNameId: String,
    surahNameAr: String,
    navigateToReadQoran: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(4.dp),
        onClick = navigateToReadQoran
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .width(32.dp)
                    .align(Alignment.CenterVertically)
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
                    text = "$ayahNumber",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
            ) {
                Text(
                    text = surahName,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = surahNameId,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = surahNameAr,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily(Font(R.font.usmani_font))
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuzCardItem(
    juzNumber: Int,
    surahList: List<String?>,
    surahNumberList: List<Int?>,
    navigateToReadQoran: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isSurahListShowed by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = {
            if (surahNumberList.isNotEmpty()) {
                navigateToReadQoran(surahNumberList.first()!!)
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(32.dp)
                            .weight(1F)
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .size(48.dp),
                            imageVector = Icons.Default.Circle,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = "$juzNumber",
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(3F)
                    ) {
                        Text(
                            text = stringResource(R.string.txt_juz, juzNumber),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        isSurahListShowed = !isSurahListShowed
                    }
                ) {
                    Icon(
                        imageVector = if (!isSurahListShowed) Icons.Default.KeyboardArrowDown
                        else Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                    )
                }
            }
            AnimatedVisibility(
                visible = isSurahListShowed,
            ) {
                JuzCardMiniItem(
                    surahList = surahList,
                    surahNumberList = surahNumberList,
                    onItemClick = { surahNumber ->
                        navigateToReadQoran(surahNumber)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageCardItem(
    pageNumber: Int,
    navigateToReadQoran: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(4.dp),
        onClick = navigateToReadQoran
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .width(32.dp)
                    .align(Alignment.CenterVertically)
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
                    text = "$pageNumber",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Column(
                modifier = Modifier
                    .weight(5f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
            ) {
                Text(
                    text = stringResource(R.string.txt_page, pageNumber),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuzCardMiniItem(
    surahList: List<String?>,
    surahNumberList: List<Int?>,
    onItemClick: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (surahList.isNotEmpty() && surahNumberList.isNotEmpty()) {
            for (index in surahList.indices) {
                Card(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        onItemClick(surahNumberList[index])
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Box {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .width(48.dp)
                                    .height(32.dp),
                                imageVector = Icons.Default.Circle,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                text = "${surahNumberList[index]}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${surahList[index]}",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun JuzCardMiniItemPreview() {
    MyQoranComposeTheme(useDarkTheme = false) {
        Surface {
            Spacer(modifier = Modifier.height(120.dp))
            JuzCardMiniItem(
                listOf(
                    "Mamang",
                    "Miming",
                    "Memeng"
                ),
                listOf(
                    1,
                    2,
                    3
                ),
                {},
                modifier = Modifier
                    .padding(12.dp),
            )
        }
    }
}