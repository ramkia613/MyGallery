package com.ramki.mygallery.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ramki.mygallery.data.local.MediaLocalDataSource
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType

class MediaPagingSource(
    private val dataSource: MediaLocalDataSource,
    private val albumName: String,
    private val mediaType: MediaType,
    private val isContainsAll: Boolean,
) : PagingSource<Int, MediaGroup>() {

    override fun getRefreshKey(state: PagingState<Int, MediaGroup>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaGroup> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val (imagePaths, totalSize) = dataSource.getMediaByAlbum(
                albumName,
                mediaType,
                isContainsAll,
                pageSize,
                page
            )

            LoadResult.Page(
                data = imagePaths,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page * pageSize < totalSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
