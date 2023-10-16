package com.rmaproject.myqoran.service.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn

class LocationClientImpl(
    private val application: Application,
    private val client: FusedLocationProviderClient,
    private val externalScope: CoroutineScope
) : LocationClient {
    override fun getLocationUpdates(): Flow<LocationTrackerCondition<Location?>> =
        callbackFlow<LocationTrackerCondition<Location?>> {

            val locationRequest = LocationRequest.Builder(1000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            val isFineLocationPermitted = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val isCoarseLocationPermitted = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val locationManager = application.getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager

            val isGpsEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (!isGpsEnabled) {
                trySend(LocationTrackerCondition.NoGps())
            }

            if (!(isFineLocationPermitted || isCoarseLocationPermitted)) {
                trySend(LocationTrackerCondition.MissingPermission())
            }

            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    trySend(LocationTrackerCondition.Success(result.lastLocation))
                }
            }

            client.requestLocationUpdates(
                locationRequest,
                callback,
                Looper.getMainLooper()
            ).addOnFailureListener { exception ->
                trySend(LocationTrackerCondition.Error())
                close(exception)
            }

            awaitClose {
                client.removeLocationUpdates(callback)
            }
        }.shareIn(
            externalScope,
            replay = 0,
            started = SharingStarted.WhileSubscribed()
        )
}