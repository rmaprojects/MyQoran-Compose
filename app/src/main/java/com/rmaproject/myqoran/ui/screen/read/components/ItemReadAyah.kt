package com.rmaproject.myqoran.ui.screen.read.components

import android.text.Spannable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.utils.GlobalState
import com.rmaproject.myqoran.utils.toAnnotatedString

@Composable
fun ItemReadAyah(
    ayahText: Spannable,
    ayahTranslate: String?,
    modifier: Modifier = Modifier,
    isRead: Boolean = true,
    footNote: String = "",
    onTranslateClick: (String) -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = ayahText.toAnnotatedString(MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.End,
                style = TextStyle(
                    fontSize = GlobalState.ayahTextSize.sp,
                    fontFamily = FontFamily(Font(R.font.usmani_font))
                )
            )
            Spacer(Modifier.height(32.dp))
            if (!GlobalState.isFocusRead) {
                if (isRead) {
                    SpannableText(
                        text = ayahTranslate ?: "",
                        onClick = {
                            onTranslateClick(footNote)
                        },
                    )
                }
                if (!isRead) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = ayahTranslate ?: "",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun ItemSurahCard(
    surahName: String,
    surahNameAr: String,
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
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
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
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = descendPlace,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(R.string.txt_many_ayah, "$totalAyah"),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = surahNameAr,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = FontFamily(Font(R.font.usmani_font))
                        )
                    )
                }
            }
        }
    }
}