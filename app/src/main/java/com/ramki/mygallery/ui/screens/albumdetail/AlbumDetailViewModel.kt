package com.ramki.mygallery.ui.screens.albumdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.repository.GalleryRepository
import com.ramki.mygallery.navigation.AppDestination
import com.ramki.mygallery.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val dispatchersProvider: DispatchersProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val album = savedStateHandle.toRoute<AppDestination.AlbumDetail>()

    private val _name: MutableStateFlow<String> = MutableStateFlow(album.name)
    val name = _name.asStateFlow()

    private val _mediaGroup: MutableStateFlow<PagingData<MediaGroup>> = MutableStateFlow(value = PagingData.empty())
    val mediaGroup = _mediaGroup.asStateFlow()

    init {
        getAlbumMedia()
    }

    fun getAlbumMedia() {
        viewModelScope.launch {
            galleryRepository
                .getAlbumMedia(album.name, album.type, album.isContainsAll)
                .cachedIn(viewModelScope)
                .flowOn(dispatchersProvider.io)
                .collect {
                    _mediaGroup.value = it
                }
        }
    }
}
