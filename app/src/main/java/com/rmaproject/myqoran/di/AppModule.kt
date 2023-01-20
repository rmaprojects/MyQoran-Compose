package com.rmaproject.myqoran.di

import android.app.Application
import androidx.room.Room
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.data.local.QoranDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQoranDatabase(app: Application): QoranDatabase {
        return Room.databaseBuilder(
            app,
            QoranDatabase::class.java,
            QoranDatabase.DATABASE_NAME,
        ).createFromInputStream {
            app.applicationContext.resources.openRawResource(R.raw.qoran)
        }.build()
    }
}