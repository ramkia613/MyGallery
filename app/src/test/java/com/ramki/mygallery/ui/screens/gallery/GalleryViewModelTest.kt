package com.ramki.mygallery.ui.screens.gallery

import app.cash.turbine.test
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.repository.GalleryRepository
import com.ramki.mygallery.test.CoroutineTestRule
import com.ramki.mygallery.test.MockUtil
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: GalleryViewModel

    private val repository: GalleryRepository = mockk()

    @Before
    fun setUp() {
        viewModel = GalleryViewModel(
            galleryRepository = repository,
            dispatchersProvider = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider,
        )
    }

    @Test
    fun initialize_albums_with_empty_list() = runTest {
        viewModel.albums.test {
            assertEquals(emptyList<Album>(), awaitItem())
        }
    }

    @Test
    fun fetch_albums() = runTest {
        coEvery { repository.getAlbums() } returns flowOf(MockUtil.albums)

        viewModel.getAlbums()

        viewModel.albums.test {
            advanceUntilIdle()
            assertEquals(MockUtil.albums, expectMostRecentItem())
        }
    }
}
