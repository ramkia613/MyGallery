package com.ramki.mygallery.di

import android.content.Context
import com.ramki.mygallery.data.local.MediaLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Provides
    fun provideMediaLocalDataSource(@ApplicationContext context: Context): MediaLocalDataSource {
        return MediaLocalDataSource(context)
    }
}
