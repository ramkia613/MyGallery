package com.ramki.mygallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ramki.mygallery.data.local.MediaLocalDataSource
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    suspend fun getAlbums(): Flow<List<Album>>

    suspend fun getAlbumMedia(albumName: String, mediaType: MediaType, isContainsAll: Boolean):
        Flow<PagingData<MediaGroup>>

}

class GalleryRepositoryImpl @Inject constructor(
    private val localDataSource: MediaLocalDataSource
) : GalleryRepository {

    override suspend fun getAlbums(): Flow<List<Album>> {
        return localDataSource.getAlbums()
    }

    override suspend fun getAlbumMedia(albumName: String, mediaType: MediaType, isContainsAll: Boolean):
        Flow<PagingData<MediaGroup>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { MediaPagingSource(localDataSource, albumName, mediaType, isContainsAll) }
        ).flow
    }
}
