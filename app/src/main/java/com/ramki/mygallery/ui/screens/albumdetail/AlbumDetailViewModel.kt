package com.ramki.mygallery.ui.screens.albumdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ramki.mygallery.data.repository.GalleryRepository
import com.ramki.mygallery.navigation.AppDestination
import com.ramki.mygallery.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

}
