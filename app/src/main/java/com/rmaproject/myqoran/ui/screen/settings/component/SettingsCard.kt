package com.rmaproject.myqoran.ui.screen.settings.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsSwitchCard(
    cardIcon: ImageVector,
    cardFor: String,
    description: String,
    isSwitchClicked: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp),
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                cardIcon,
                contentDescription = description,
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f)
            )
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    cardFor,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Switch(
                modifier = Modifier.weight(1f),
                checked = isSwitchClicked,
                onCheckedChange = { onSwitchClick(it) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsClickCard(
    cardIcon: ImageVector,
    cardFor: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp),
        onClick = { onClick() },
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                cardIcon,
                contentDescription = description,
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f)
            )
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    cardFor,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun SettingsSliderCard(
    sliderValue: Float,
    onSlide: (Float) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(158.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.TextFields,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ukuran teks baca qur'an",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Slider(
                modifier = Modifier.padding(8.dp),
                value = sliderValue,
                onValueChange = onSlide,
                valueRange = 24F..40F
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "بِسْمِ اللهِ الرَّحْمَنِ الرَّحِيْمِ",
                    style = TextStyle(
                        fontSize = sliderValue.sp
                    )
                )
            }
        }
    }
}