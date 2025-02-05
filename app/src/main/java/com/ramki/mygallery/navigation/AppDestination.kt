package com.ramki.mygallery.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Gallery : AppDestination

    @Serializable
    data object Permission : AppDestination

    @Serializable
    data object AlbumDetail : AppDestination
}
