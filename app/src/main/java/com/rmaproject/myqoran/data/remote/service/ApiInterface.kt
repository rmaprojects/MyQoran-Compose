package com.rmaproject.myqoran.data.remote.service

import com.rmaproject.myqoran.data.remote.model.AdzanScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("day")
    suspend fun getAdzanSchedule(
        @Query("latitude") latitude:String,
        @Query("longitude") longitude:String
    ): AdzanScheduleResponse
}