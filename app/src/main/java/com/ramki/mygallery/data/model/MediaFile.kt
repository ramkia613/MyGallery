package com.ramki.mygallery.data.model

data class MediaFile(
    val id: Long,
    val path: String,
    val name: String,
    val folderName: String,
    val dateAdded: Long,
    val type: MediaType,
    val duration: Long = 0L
)

enum class MediaType {
    IMAGE, VIDEO
}
