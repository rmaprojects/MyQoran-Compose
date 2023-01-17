package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ItemReadAyah(
    ayahText: String,
    ayahTranslate: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = ayahText,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = ayahTranslate,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall
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