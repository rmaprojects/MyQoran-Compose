package com.rmaproject.myqoran.di

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.rmaproject.myqoran.BuildConfig
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.data.local.BookmarkDatabase
import com.rmaproject.myqoran.data.local.QoranDatabase
import com.rmaproject.myqoran.data.remote.service.ApiInterface
import com.rmaproject.myqoran.service.location.LocationClient
import com.rmaproject.myqoran.service.location.LocationClientImpl
import com.rmaproject.myqoran.service.player.MyPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snow.player.PlayerClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQoranDatabase(app: Application): QoranDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            QoranDatabase::class.java,
            QoranDatabase.DATABASE_NAME,
        ).createFromInputStream {
            app.applicationContext.resources.openRawResource(R.raw.qoran)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiConfig(): ApiInterface {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(app: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            BookmarkDatabase::class.java,
            BookmarkDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePlayerClient(app: Application): PlayerClient {
        return PlayerClient.newInstance(app.applicationContext, MyPlayerService::class.java)
    }

    @Provides
    @Singleton
    fun provideExternalCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideLocationClient(
        app: Application,
        coroutineScope: CoroutineScope
    ): LocationClient {
        return LocationClientImpl(
            app,
            LocationServices.getFusedLocationProviderClient(app.applicationContext),
            coroutineScope
        )
    }
}