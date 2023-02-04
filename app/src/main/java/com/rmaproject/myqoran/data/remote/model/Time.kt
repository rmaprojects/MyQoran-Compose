package com.rmaproject.myqoran.data.remote.model


import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("asr")
    val asr: String,
    @SerializedName("dhuha")
    val dhuha: String,
    @SerializedName("dhuhr")
    val dhuhr: String,
    @SerializedName("fajr")
    val fajr: String,
    @SerializedName("gregorian")
    val gregorian: String,
    @SerializedName("hijri")
    val hijri: String,
    @SerializedName("imsak")
    val imsak: String,
    @SerializedName("isha")
    val isha: String,
    @SerializedName("maghrib")
    val maghrib: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("timestamp")
    val timestamp: Long
)