package com.ramki.mygallery.data.local

import android.net.Uri
import android.provider.MediaStore

object MediaQuery {
    val MediaStoreImageUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val MediaStoreVideoUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    val AlbumProjection = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.DATA,
        MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Files.FileColumns.DATE_ADDED,
        MediaStore.Files.FileColumns.SIZE,
        MediaStore.Files.FileColumns.MIME_TYPE,
    )

    object Selection {
        val ImageAlbum: String = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?"
        val VideoAlbum: String = MediaStore.Video.Media.BUCKET_DISPLAY_NAME + "=?"
    }


    object SortOrder {
        val Image = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val Video = "${MediaStore.Video.Media.DATE_ADDED} DESC"
    }
}
