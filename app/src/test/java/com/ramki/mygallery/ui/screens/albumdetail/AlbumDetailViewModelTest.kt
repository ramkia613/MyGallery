package com.ramki.mygallery.ui.screens.albumdetail

import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.ramki.mygallery.data.repository.GalleryRepository
import com.ramki.mygallery.test.CoroutineTestRule
import com.ramki.mygallery.test.MockUtil
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumDetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: AlbumDetailViewModel
    private val repository: GalleryRepository = mockk()

    @Before
    fun setUp() {
        viewModel = AlbumDetailViewModel(
            galleryRepository = repository,
            dispatchersProvider = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider,
        )
    }

    @Test
    fun initialize_with_empty_name() = runTest {
        viewModel.name.test {
            assertEquals("", awaitItem())
        }
    }

    @Test
    fun fetch_album_media() = runTest {
        val expectedData = MockUtil.mediaGroup
        val expectedPagingData = PagingData.from(listOf(expectedData))

        coEvery { repository.getAlbumMedia(any(), any(), any()) } returns flowOf(expectedPagingData)

        viewModel.getAlbumMedia(MockUtil.albumDetail)

        viewModel.name.test {
            assertEquals(MockUtil.albumDetail.name, awaitItem())
        }

        viewModel.mediaGroup.test {
            val data = awaitItem()
            data.map {
                assertEquals(expectedData, it)
            }
        }
    }
}
