package com.rmaproject.myqoran.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.VolumeUp
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
import com.rmaproject.myqoran.data.kotpref.SettingsPreferences.QoriOptions
import com.rmaproject.myqoran.ui.navigation.Screen
import com.rmaproject.myqoran.ui.screen.settings.component.SettingsClickCard
import com.rmaproject.myqoran.ui.screen.settings.component.SettingsSliderCard
import com.rmaproject.myqoran.ui.screen.settings.component.SettingsSwitchCard
import com.rmaproject.myqoran.utils.GlobalState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
    goToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDialogForLanguageShown by remember {
        mutableStateOf(false)
    }

    var isDialogForChangeQoriShown by remember {
        mutableStateOf(false)
    }

    var languageValue by remember {
        mutableStateOf(SettingsPreferences.currentLanguage)
    }

    var qoriValue by remember {
        mutableStateOf(SettingsPreferences.currentQoriOption)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranHomeAppBar(
                openDrawer = openDrawer,
                currentDestination = Screen.Settings.route,
                goToSearch = goToSearch
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(scrollState),
        ) {
            SettingsSwitchCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.DarkMode,
                cardFor = "Dark Mode",
                description = "Change your theme",
                isSwitchClicked = GlobalState.isDarkMode,
                onSwitchClick = { isChecked ->
                    GlobalState.isDarkMode = isChecked
                    SettingsPreferences.isDarkMode = isChecked
                }
            )
            SettingsClickCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.Language,
                cardFor = "Change Language",
                description = "Current language: ${if (languageValue == 0) "Indonesia" else "English"}",
                onClick = {
                    isDialogForLanguageShown = true
                }
            )
            SettingsSliderCard(
                modifier = Modifier.padding(8.dp),
                sliderValue = GlobalState.ayahTextSize,
                onSlide = { newValue ->
                    GlobalState.ayahTextSize = newValue
                    SettingsPreferences.ayahTextSize = newValue
                },
            )
            SettingsClickCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.VolumeUp,
                cardFor = "Pilih Qori",
                description = "Qori sekarang: ${qoriValue.qoriName}",
                onClick = {
                    isDialogForChangeQoriShown = true
                },
            )
        }

        if (isDialogForLanguageShown) {
            val actionList = listOf(
                ActionItem(
                    text = "Indonesia",
                    onClick = {
                        isDialogForLanguageShown = false
                        SettingsPreferences.currentLanguage = SettingsPreferences.INDONESIAN
                        languageValue = SettingsPreferences.INDONESIAN
                    }
                ),
                ActionItem(
                    text = "English",
                    onClick = {
                        isDialogForLanguageShown = false
                        SettingsPreferences.currentLanguage = SettingsPreferences.ENGLISH
                        languageValue = SettingsPreferences.ENGLISH
                    }
                )
            )
            MyQoranAlertDialog(
                icon = Icons.Default.Language,
                title = "Change Language",
                actionItemList = actionList,
                dismissButtonText = "Cancel",
                onDismissClick = { isDialogForLanguageShown = false },
                onConfirmClick = { isDialogForLanguageShown = false }
            )
        }

        if (isDialogForChangeQoriShown) {
            val actionList = listOf(
                ActionItem(
                    text = QoriOptions.ABD_SUDAIS.qoriName,
                    onClick = {
                        isDialogForChangeQoriShown = false
                        SettingsPreferences.currentQoriOption = QoriOptions.ABD_SUDAIS
                        qoriValue = QoriOptions.ABD_SUDAIS
                    }
                ),
                ActionItem(
                    text = QoriOptions.ALAFASY.qoriName,
                    onClick = {
                        isDialogForChangeQoriShown = false
                        SettingsPreferences.currentQoriOption = QoriOptions.ALAFASY
                        qoriValue = QoriOptions.ALAFASY
                    }
                ),
                ActionItem(
                    text = QoriOptions.HUDHAIFY.qoriName,
                    onClick = {
                        isDialogForChangeQoriShown = false
                        SettingsPreferences.currentQoriOption = QoriOptions.HUDHAIFY
                        qoriValue = QoriOptions.HUDHAIFY
                    }
                ),
                ActionItem(
                    text = QoriOptions.MUHD_AYYOUB.qoriName,
                    onClick = {
                        isDialogForChangeQoriShown = false
                        SettingsPreferences.currentQoriOption = QoriOptions.MUHD_AYYOUB
                        qoriValue = QoriOptions.MUHD_AYYOUB
                    }
                ),
            )
            MyQoranAlertDialog(
                icon = null,
                title = "Pilih Qori",
                actionItemList = actionList,
                dismissButtonText = "Cancel",
                onDismissClick = { isDialogForChangeQoriShown = false },
                onConfirmClick = { isDialogForChangeQoriShown = false }
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen({}, {})
}