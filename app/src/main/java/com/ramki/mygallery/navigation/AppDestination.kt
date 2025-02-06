package com.ramki.mygallery.navigation

import com.ramki.mygallery.data.model.MediaType
import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Gallery : AppDestination

    @Serializable
    data object Permission : AppDestination

    @Serializable
    data class AlbumDetail(
        val name: String,
        val type: MediaType,
        val isContainsAll: Boolean
    ) : AppDestination
}
