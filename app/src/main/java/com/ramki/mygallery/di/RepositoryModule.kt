package com.ramki.mygallery.di

import com.ramki.mygallery.data.local.MediaLocalDataSource
import com.ramki.mygallery.data.repository.GalleryRepository
import com.ramki.mygallery.data.repository.GalleryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideGalleryRepository(mediaLocalDataSource: MediaLocalDataSource): GalleryRepository {
        return GalleryRepositoryImpl(mediaLocalDataSource)
    }
}
