package com.rmaproject.myqoran.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.components.ActionItem
import com.rmaproject.myqoran.components.MyQoranAlertDialog
import com.rmaproject.myqoran.components.MyQoranHomeAppBar
import com.rmaproject.myqoran.data.kotpref.SettingsPreferences
import com.rmaproject.myqoran.ui.navigation.Screen
import com.rmaproject.myqoran.ui.screen.settings.component.SettingsClickCard
import com.rmaproject.myqoran.ui.screen.settings.component.SettingsSwitchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isDarkModeState by remember {
        mutableStateOf(SettingsPreferences.isDarkMode)
    }

    var isDialogShown by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranHomeAppBar(
                openDrawer = openDrawer,
                currentDestination = Screen.Settings.route,
                goToSearch = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SettingsSwitchCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.DarkMode,
                cardFor = "Dark Mode",
                description = "Change your theme",
                isSwitchClicked = isDarkModeState,
                onSwitchClick = { isChecked ->
                    isDarkModeState = isChecked
                    SettingsPreferences.isDarkMode = isChecked
                }
            )
            SettingsClickCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.Language,
                cardFor = "Change Language",
                description = "Current language: $translation",
                onClick = {
                    isDialogShown = true
                }
            )
        }

        if (isDialogShown) {
            val actionList = listOf(
                ActionItem(
                    text = "Indonesia",
                    onClick = {
                        isDialogShown = false
                        SettingsPreferences.currentLanguage = SettingsPreferences.INDONESIAN
                    }
                ),
                ActionItem(
                    text = "English",
                    onClick = {
                        isDialogShown = false
                        SettingsPreferences.currentLanguage = SettingsPreferences.ENGLISH
                    }
                )
            )
            MyQoranAlertDialog(
                icon = Icons.Default.Language,
                title = "Change Language",
                actionItemList = actionList,
                confirmButtonText = "Ok",
                dismissButtonText = "Cancel",
                onDismissClick = { isDialogShown = false },
                onConfirmClick = { isDialogShown = false }
            )
        }
    }
}

val translation = if (SettingsPreferences.currentLanguage == 0) "Indonesia" else "English"

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen({})
}