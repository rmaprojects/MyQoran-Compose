package com.rmaproject.myqoran.ui.screen.adzanschedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.service.location.LocationClient
import com.rmaproject.myqoran.ui.screen.adzanschedule.state.AdzanScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdzanScheduleViewModel @Inject constructor(
    private val repository: QoranRepository,
    private val locationClient: LocationClient
) : ViewModel() {

    private val _adzanScheduleState: MutableStateFlow<AdzanScheduleState> =
        MutableStateFlow(AdzanScheduleState.Idle)
    val adzanScheduleState = _adzanScheduleState.asStateFlow()

    private val _currentLocation: MutableStateFlow<CurrentLocation> =
        MutableStateFlow(CurrentLocation(0.0, 0.0))
    val currentLocation: StateFlow<CurrentLocation> = _currentLocation.asStateFlow()

    init {
        viewModelScope.launch {
            locationClient.getLocationUpdates(1000L)
                .catch { e ->
                    e.printStackTrace()
                    Log.d("ERR", e.toString())
                    _adzanScheduleState.emit(
                        AdzanScheduleState.Error(
                            e.message ?: "Error Occurred"
                        )
                    )
                }
                .takeWhile { _adzanScheduleState.value is AdzanScheduleState.Idle }
                .collect { location ->
                    val latitude = location.latitude
                    val longitude = location.longitude
                    try {
                        val response =
                            repository.getAdzanSchedule(latitude.toString(), longitude.toString())
                        _adzanScheduleState.emit(
                            AdzanScheduleState.Success(response.times.first())
                        )
                        _currentLocation.value = CurrentLocation(
                            latitude = latitude,
                            longitude = longitude
                        )

                    } catch (e: Exception) {
                        _adzanScheduleState.emit(
                            AdzanScheduleState.Error(
                                e.localizedMessage ?: "Problem Occurred when fetching data"
                            )
                        )
                    }
                }
        }
    }

    data class CurrentLocation(
        val longitude: Double,
        val latitude: Double
    )
}