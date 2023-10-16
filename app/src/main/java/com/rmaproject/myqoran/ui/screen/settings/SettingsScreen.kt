package com.rmaproject.myqoran.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.R
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

@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDialogForLanguageShown by remember {
        mutableStateOf(false)
    }

    var isDialogForChangeQoriShown by remember {
        mutableStateOf(false)
    }

    var languageValue by remember {
        mutableIntStateOf(SettingsPreferences.currentLanguage)
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
                currentDestination = Screen.Settings.route
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
        ) {
            SettingsSwitchCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.DarkMode,
                cardFor = stringResource(R.string.txt_dark_mode),
                description = stringResource(R.string.txt_change_your_theme),
                isSwitchClicked = GlobalState.isDarkMode,
                onSwitchClick = { isChecked ->
                    GlobalState.isDarkMode = isChecked
                    SettingsPreferences.isDarkMode = isChecked
                }
            )
            SettingsSwitchCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.MenuBook,
                cardFor = stringResource(R.string.txt_focus_read),
                description = stringResource(R.string.txt_desc_focus_read),
                isSwitchClicked = GlobalState.isFocusRead,
                onSwitchClick = { isChecked ->
                    GlobalState.isFocusRead = isChecked
                    SettingsPreferences.isFocusReadActive = isChecked
                }
            )
            SettingsClickCard(
                modifier = Modifier.padding(8.dp),
                cardIcon = Icons.Default.Language,
                cardFor = stringResource(R.string.txt_change_language),
                description = stringResource(
                    R.string.txt_current_lang,
                    if (languageValue == SettingsPreferences.INDONESIAN) "Indonesia" else "English"
                ),
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
                cardFor = stringResource(R.string.txt_choose_reciter),
                description = stringResource(R.string.txt_current_reciter, qoriValue.qoriName),
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
                title = stringResource(id = R.string.txt_change_language),
                actionItemList = actionList,
                dismissButtonText = stringResource(R.string.txt_cancel),
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
                title = stringResource(id = R.string.txt_choose_reciter),
                actionItemList = actionList,
                dismissButtonText = stringResource(id = R.string.txt_cancel),
                onDismissClick = { isDialogForChangeQoriShown = false },
                onConfirmClick = { isDialogForChangeQoriShown = false }
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen({})
}