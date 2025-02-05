package com.ramki.mygallery.data.model

data class Album(
    val name: String,
    val mediaCount: Int,
    val thumbnailPath: String,
    val type: MediaType,
    val isContainsAll: Boolean = false
)
