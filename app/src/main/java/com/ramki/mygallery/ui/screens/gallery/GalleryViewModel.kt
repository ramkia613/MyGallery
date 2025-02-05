package com.ramki.mygallery.ui.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.repository.GalleryRepository
import com.ramki.mygallery.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _albums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())

    val albums = _albums.asStateFlow()

    fun getAlbums() {
        viewModelScope.launch {
            galleryRepository.getAlbums()
                .flowOn(dispatchersProvider.io)
                .collect { albums ->
                    _albums.value = albums
                }
        }
    }
}
