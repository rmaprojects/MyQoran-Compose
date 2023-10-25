package com.rmaproject.myqoran.ui.screen.read.events

sealed class PlayAyahEvent {
    object SkipPrevious: PlayAyahEvent()
    object PlayPauseAyah: PlayAyahEvent()
    object PauseAyah: PlayAyahEvent()
    object SkipNext: PlayAyahEvent()
    object StopAyah: PlayAyahEvent()
    object ChangePlayerMode: PlayAyahEvent()
}
