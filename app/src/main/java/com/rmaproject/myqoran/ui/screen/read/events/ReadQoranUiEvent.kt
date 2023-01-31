package com.rmaproject.myqoran.ui.screen.read.events

sealed class ReadQoranUiEvent {
    object Idle: ReadQoranUiEvent()
    data class SuccessAddToBookmark(val message: String): ReadQoranUiEvent()
    data class SuccessCopiedAyah(val message: String): ReadQoranUiEvent()
    data class SuccessSharedAyah(val message: String): ReadQoranUiEvent()
}
