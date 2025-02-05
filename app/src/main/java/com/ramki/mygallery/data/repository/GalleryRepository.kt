package com.ramki.mygallery.data.repository

import com.ramki.mygallery.data.local.MediaLocalDataSource
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.model.MediaFile
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    suspend fun getAlbums(): Flow<List<Album>>

    suspend fun getAlbumMedia(albumName: String): List<MediaFile>

}

class GalleryRepositoryImpl @Inject constructor(private val localDataSource: MediaLocalDataSource) : GalleryRepository {

    override suspend fun getAlbums(): Flow<List<Album>> {
        return localDataSource.getAlbums()
    }

    override suspend fun getAlbumMedia(albumName: String): List<MediaFile> {
        TODO("Not yet implemented")
    }
}
