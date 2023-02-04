package com.rmaproject.myqoran.ui.screen.adzanschedule

import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.components.MyQoranHomeAppBar
import com.rmaproject.myqoran.ui.screen.adzanschedule.component.FindQiblaCard
import com.rmaproject.myqoran.ui.screen.adzanschedule.state.AdzanScheduleState
import java.util.Locale.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdzanScheduleScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AdzanScheduleViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            MyQoranHomeAppBar(
                openDrawer = openDrawer,
                currentDestination = stringResource(R.string.txt_sholat_schedule)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            viewModel.currentLocation.collectAsState().let {
                @Suppress("DEPRECATION") val address = Geocoder(
                    context,
                    getDefault()
                ).getFromLocation(
                    it.value.latitude,
                    it.value.longitude,
                    1,
                )
                if (!address.isNullOrEmpty()) {
                    val locality = address[0].locality
                    val subLocality = address[0].subLocality
                    val subAdminArea = address[0].subAdminArea
                    val currentLocation = "$locality, $subLocality, $subAdminArea"
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.txt_your_location, currentLocation),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                viewModel.adzanScheduleState.collectAsState(initial = AdzanScheduleState.Idle)
                    .let { state ->
                        when (val event = state.value) {
                            is AdzanScheduleState.Idle -> {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Column(
                                        modifier = Modifier.align(Alignment.Center),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = stringResource(R.string.txt_please_turn_on_location),
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            is AdzanScheduleState.Error -> {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = event.message,
                                        style = MaterialTheme.typography.headlineLarge
                                    )
                                }
                            }
                            is AdzanScheduleState.Success -> {
                                val sholatTime = event.data
                                FindQiblaCard(timeEvent = "Shubuh", sholatTime = sholatTime.fajr)
                                Spacer(modifier = Modifier.height(16.dp))
                                FindQiblaCard(timeEvent = "Dzuhur", sholatTime = sholatTime.dhuhr)
                                Spacer(modifier = Modifier.height(16.dp))
                                FindQiblaCard(timeEvent = "Ashar", sholatTime = sholatTime.asr)
                                Spacer(modifier = Modifier.height(16.dp))
                                FindQiblaCard(
                                    timeEvent = "Maghrib",
                                    sholatTime = sholatTime.maghrib
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                FindQiblaCard(timeEvent = "Isya", sholatTime = sholatTime.isha)
                            }
                        }
                    }
            }
        }
    }
}