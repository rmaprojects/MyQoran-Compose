package com.rmaproject.myqoran.service.location

import android.location.Location

sealed class LocationTrackerCondition<T> {
    class NoGps<Nothing>: LocationTrackerCondition<Nothing>()
    class MissingPermission<Nothing>: LocationTrackerCondition<Nothing>()
    data class Success<T>(val location: Location?): LocationTrackerCondition<T>()
    class Error<T>: LocationTrackerCondition<T>()
}