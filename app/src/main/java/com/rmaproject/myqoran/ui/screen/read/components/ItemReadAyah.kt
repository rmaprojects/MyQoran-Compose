package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.ui.theme.ReadQoranTextStyle

@Composable
fun ItemReadAyah(
    ayahText: String?,
    ayahTranslate: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = ayahText ?: "",
                textAlign = TextAlign.End,
                style = ReadQoranTextStyle
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = ayahTranslate ?: "",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ItemReadAyahPreview() {
    ItemReadAyah(
        "بِسْمِ ٱللَّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
        "Dengan nama Alloh yang Maha Pengasih lagi Maha Penyayang"
    )
}

@Composable
fun ItemSurahCard(
    surahName: String,
    totalAyah: Int,
    descendPlace: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(256.dp),
        shape = CardDefaults.outlinedShape,
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.outlinedCardColors(),
        elevation = CardDefaults.outlinedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = surahName,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "$totalAyah Ayat",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = descendPlace,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Preview
@Composable
fun ItemSurahCardPreview() {
    ItemSurahCard(
        "Al Fatihah",
        7,
        "Meccan"
    )
}