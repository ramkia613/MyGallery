package com.ramki.mygallery.data.repository

import androidx.paging.PagingSource
import androidx.paging.map
import app.cash.turbine.test
import com.ramki.mygallery.data.local.MediaLocalDataSource
import com.ramki.mygallery.test.MockUtil
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GalleryRepositoryImplTest {

    private lateinit var repository: GalleryRepository

    private val localDataSource: MediaLocalDataSource = mockk()
    private val mediaPagingSource: MediaPagingSource = mockk()

    @Before
    fun setUp() {
        repository = GalleryRepositoryImpl(localDataSource)
    }

    @Test
    fun get_albums_from_local_data_source() = runTest {

        coEvery { localDataSource.getAlbums() } returns flowOf(MockUtil.albums)

        repository.getAlbums().test {
            assertEquals(MockUtil.albums, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun get_media_from_local_data_source() = runTest {
        val response = listOf(MockUtil.mediaGroup)
        val expected = PagingSource.LoadResult.Page(
            data = response,
            prevKey = 0,
            nextKey = null
        )

        coEvery { mediaPagingSource.load(any()) } returns expected

        repository.getAlbumMedia(
            albumName = MockUtil.albumDetail.name,
            mediaType = MockUtil.albumDetail.type,
            isContainsAll = MockUtil.albumDetail.isContainsAll
        ).test {
            val item = awaitItem()

            item.map {
                assertEquals(response, it)
            }
        }
    }
}
