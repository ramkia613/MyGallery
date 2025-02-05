package com.ramki.mygallery.data.model

data class MediaFile(
    val id: Long,
    val path: String,
    val name: String,
    val folderName: String,
    val dateAdded: Long,
    val type: MediaType
)

enum class MediaType {
    IMAGE, VIDEO
}
