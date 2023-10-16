package com.rmaproject.myqoran.ui.screen.adzanschedule.state

import com.rmaproject.myqoran.data.remote.model.Time

sealed class AdzanScheduleState {
    data class Success(val data: Time) : AdzanScheduleState()
    data class Error(val errorType: ErrorType) : AdzanScheduleState()
    object Idle: AdzanScheduleState()
}

enum class ErrorType {
    NO_GPS,
    PERMISSION_ERROR,
    OTHERS
}