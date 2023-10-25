package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.data.kotpref.SettingsPreferences
import com.rmaproject.myqoran.ui.screen.read.ReadQoranViewModel
import snow.player.PlayMode

@Composable
fun PlayerControlPanelBottomBar(
    onSkipPrevClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onChangePlayerMode: () -> Unit,
    onStopClick: () -> Unit,
    playType: ReadQoranViewModel.PlayType,
    playMode: PlayMode,
    isPlayerPlaying: Boolean,
    modifier: Modifier = Modifier,
    currentPlaying: String = "",
    qori: String = SettingsPreferences.currentQoriOption.qoriName,
) {
    BottomAppBar(
        modifier = modifier.height(128.dp),
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                text = currentPlaying,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                text = qori,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                if (playType == ReadQoranViewModel.PlayType.PLAY_ALL) {
                    IconButton(onClick = onSkipPrevClick) {
                        Icon(
                            Icons.Default.SkipPrevious,
                            contentDescription = "Prev Ayah"
                        )
                    }
                }
                IconButton(onClick = onPlayPauseClick) {
                    Icon(
                        if (!isPlayerPlaying) Icons.Default.Pause
                        else Icons.Default.PlayArrow,
                        contentDescription = "Play Pause Ayah"
                    )
                }
                if (playType == ReadQoranViewModel.PlayType.PLAY_ALL) {
                    IconButton(onClick = onSkipNextClick) {
                        Icon(
                            Icons.Default.SkipNext,
                            contentDescription = "Next Ayah"
                        )
                    }
                }
                IconButton(onClick = onStopClick) {
                    Icon(
                        Icons.Default.Stop,
                        contentDescription = "Stop Player"
                    )
                }
                if (playType == ReadQoranViewModel.PlayType.PLAY_SINGLE) {
                    IconButton(onClick = onChangePlayerMode) {
                        Icon(
                            imageVector = if (playMode == PlayMode.PLAYLIST_LOOP) Icons.Default.Repeat
                            else Icons.Default.RepeatOne,
                            contentDescription = "Loop Player"
                        )
                    }
                }
            }
        }
    }
}