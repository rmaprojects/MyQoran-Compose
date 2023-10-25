package com.rmaproject.myqoran.ui.screen.onboarding

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.data.kotpref.SettingsPreferences
import com.rmaproject.myqoran.utils.GlobalState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun OnBoardingScreen(
    navigateToHome: () -> Unit,
) {

    val context = LocalContext.current

    BackHandler {
        Toast.makeText(context, R.string.txt_finish_app_introduction_first, Toast.LENGTH_SHORT)
            .show()
    }

    val requiredLocation = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val permissionState = rememberMultiplePermissionsState(permissions = requiredLocation)

    val pagerState = rememberPagerState { 5 }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 1) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(128.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1F),
                ) {
                    if (pagerState.currentPage > 0) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        ) {
                            Text(text = stringResource(R.string.txt_prev_onboarding))
                        }
                    }
                }
                Row(
                    modifier = Modifier.weight(2F),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (0..4).forEach { i ->
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "Step $i",
                            tint = if (pagerState.currentPage == i) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
                TextButton(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < 4) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                return@launch
                            }
                            if (pagerState.currentPage == 4) {
                                navigateToHome()
                                SettingsPreferences.isOnBoarding = false
                                GlobalState.isOnBoarding = false
                            }
                        }
                    }
                ) {
                    Text(
                        text = if (pagerState.currentPage < 4) stringResource(R.string.txt_next_onboarding) else stringResource(
                            R.string.txt_finish_onboarding
                        )
                    )
                }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { pageNumber ->
                when (pageNumber) {
                    0 -> OnBoardingContentWelcome()
                    1 -> OnBoardingContentLocation()
                    2 -> OnBoardingContentTheme()
                    3 -> OnBoardingContentLanguage()
                    4 -> OnBoardingContentComplete()
                }
            }
        }
    }
}

@Composable
private fun OnBoardingContentWelcome(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_welcome_myqoran_onboarding),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(14.dp))
        Image(
            painter = painterResource(id = R.drawable.my_qoran_logo),
            contentDescription = "MyQoran Logo"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.txt_press_next_to_start),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun OnBoardingContentLocation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_allow_location_onboarding),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(id = R.drawable.location_vector),
            contentDescription = "Location Graphics"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.txt_desc_allow_location_onboarding),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun OnBoardingContentLanguage(
    modifier: Modifier = Modifier
) {

    var isDialogShowed by remember {
        mutableStateOf(false)
    }
    var languageNameState by remember {
        mutableIntStateOf(
            SettingsPreferences.currentLanguage
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_change_lang_onboarding),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Icon(
            imageVector = Icons.Default.Language,
            contentDescription = "Language Graphics",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .size(128.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_desc_change_lang_onboarding),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box {
            TextButton(
                onClick = {
                    isDialogShowed = true
                }
            ) {
                Icon(imageVector = Icons.Default.Language, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (languageNameState == 1) "English"
                    else "Indonesia"
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Language"
                )
            }
            DropdownMenu(
                expanded = isDialogShowed,
                onDismissRequest = { isDialogShowed = false },
                offset = DpOffset(32.dp, 0.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Indonesia") },
                    onClick = {
                        isDialogShowed = false
                        SettingsPreferences.currentLanguage = SettingsPreferences.INDONESIAN
                        languageNameState = SettingsPreferences.INDONESIAN
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "English") },
                    onClick = {
                        isDialogShowed = false
                        SettingsPreferences.currentLanguage = SettingsPreferences.ENGLISH
                        languageNameState = SettingsPreferences.ENGLISH
                    }
                )
            }
        }
    }
}

@Composable
private fun OnBoardingContentTheme(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_dark_mode_onboarding),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (GlobalState.isDarkMode) {
            Image(
                painter = painterResource(id = R.drawable.dark_mode_active),
                contentDescription = "Dark Mode"
            )
        }
        if (!GlobalState.isDarkMode) {
            Image(
                painter = painterResource(id = R.drawable.light_mode_active),
                contentDescription = "Dark Mode"
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_dark_mode_desc_onboarding),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Switch(
                checked = GlobalState.isDarkMode,
                onCheckedChange = {
                    GlobalState.isDarkMode = it
                    SettingsPreferences.isDarkMode = it
                }
            )
        }
    }
}

@Composable
private fun OnBoardingContentComplete(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.txt_finished_onboarding),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier.size(256.dp),
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = "Checkmark"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.txt_please_use_app_onboarding),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        OnBoardingContentComplete()
    }
}