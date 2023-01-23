package com.rmaproject.myqoran.ui.screen.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.R

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
                    text = "$juzNumber",
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
                    text = stringResource(R.string.txt_juz, juzNumber),
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

@Preview
@Composable
fun CardItemPreview() {
    SurahCardItem(
        1,
        "Al Fatihah",
        "Pembukaan",
        "الفاتحة",
        {}
    )
}

@Preview
@Composable
fun JuzCardItemPreview() {
    JuzCardItem(
        1,
        {}
    )
}

@Preview
@Composable
fun PageCardItemPreview() {
    PageCardItem(1, {})
}