package com.rmaproject.myqoran.ui.screen.adzanschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.service.location.LocationClient
import com.rmaproject.myqoran.service.location.LocationTrackerCondition
import com.rmaproject.myqoran.ui.screen.adzanschedule.state.AdzanScheduleState
import com.rmaproject.myqoran.ui.screen.adzanschedule.state.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    fun getLocationUpdates() {
        viewModelScope.launch {
            _adzanScheduleState.emit(AdzanScheduleState.Idle)
            locationClient.getLocationUpdates()
                .onEach { tracker ->
                    when (tracker) {
                        is LocationTrackerCondition.Error -> {
                            _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.OTHERS))
                        }

                        is LocationTrackerCondition.MissingPermission -> {
                            _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.PERMISSION_ERROR))
                        }

                        is LocationTrackerCondition.NoGps -> {
                            _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.NO_GPS))
                        }

                        is LocationTrackerCondition.Success -> {
                            val latitude = tracker.location?.latitude
                            val longitude = tracker.location?.longitude
                            if (latitude == null || longitude == null) {
                                _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.OTHERS))
                                return@onEach
                            }
                            _currentLocation.emit(CurrentLocation(longitude, latitude))
                            val responseResult = repository.getAdzanSchedule(
                                latitude.toString(),
                                longitude.toString()
                            )
                            _adzanScheduleState.emit(AdzanScheduleState.Success(responseResult.times[0]))
                            cancel()
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    data class CurrentLocation(
        val longitude: Double,
        val latitude: Double
    )
}