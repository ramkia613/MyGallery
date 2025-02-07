package com.ramki.mygallery.test

import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.model.MediaFile
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType
import com.ramki.mygallery.navigation.AppDestination


object MockUtil {

    val album = Album(
        name = "name",
        mediaCount = 10,
        thumbnailPath = "path",
        type = MediaType.IMAGE,
        isContainsAll = false
    )

    val albums = listOf(
        album.copy(name = "name1", mediaCount = 15, isContainsAll = true),
        album
    )

    val albumDetail = AppDestination.AlbumDetail(
        name = "Test Album",
        type = MediaType.IMAGE,
        isContainsAll = false,
    )

    val mediaFile = MediaFile(
        id = 1,
        path = "path",
        name = "name",
        folderName = "folderName",
        dateAdded = 1000,
        type = MediaType.IMAGE,
        duration = 0L
    )

    val mediaGroup = MediaGroup(
        day = "Today",
        mediaFiles = listOf(mediaFile)
    )

    val mediaGroups = listOf(
        mediaGroup,
        mediaGroup.copy("Yesterday")
    )
}
