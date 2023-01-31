package com.rmaproject.myqoran.ui.screen.read.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.R
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
                modifier = Modifier
                    .fillMaxWidth(),
                text = ayahText ?: "",
                textAlign = TextAlign.End,
                style = ReadQoranTextStyle
            )
            Spacer(Modifier.height(32.dp))
            SpannableText(
                text = ayahTranslate ?: "",
                onClick = {
                    Log.d("CLICK", it)
                },
            )
        }
    }
}

@Composable
fun ItemSurahCard(
    surahName: String,
    surahNameAr: String,
    totalAyah: Int,
    descendPlace: String,
    onPlayAllAyahClick: () -> Unit,
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
                        text = "$totalAyah Ayat",
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
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        shape = ButtonDefaults.elevatedShape,
                        colors = ButtonDefaults.elevatedButtonColors(),
                        elevation = ButtonDefaults.elevatedButtonElevation(),
                        onClick = onPlayAllAyahClick
                    ) {
                        Icon(Icons.Default.PlayCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Play All Ayah")
                    }
                }
            }
        }
    }
}

//@Composable
//fun ClickableTextTranslate(
//    ayahTranslate: String?,
//    onClick: ()-> Unit,
//    modifier: Modifier = Modifier
//) {
//    val pattern = Pattern.compile("""\d""", Pattern.CASE_INSENSITIVE)
//    val matcher = pattern.matcher(ayahTranslate ?: "")
//    val annotatedString = buildAnnotatedString {
//        while (matcher.find()) {
//            withStyle(
//                style = SpanStyle(color = MaterialTheme.colorScheme.primary)
//            ) {
//                append()
//            }
//        }
//    }
//    ClickableText(
//        modifier = modifier,
//        text = annotatedString,
//        style = MaterialTheme.typography.bodyLarge,
//        onClick = { offset ->
//
//        }
//    )
//}