package com.ramki.mygallery.data.local

import android.content.Context
import android.provider.MediaStore
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.model.MediaFile
import com.ramki.mygallery.data.model.MediaType
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaLocalDataSource @Inject constructor(private val context: Context) {

    fun getAlbums(): Flow<List<Album>> = flow {
        val albumMap = mutableMapOf<String, Album>()

        // Fetch images & Videos
        val images = fetchMediaByType(MediaType.IMAGE)
        val videos = fetchMediaByType(MediaType.VIDEO)

        if (images.isNotEmpty()) {
            // Add special albums
            albumMap["All Images"] =
                Album("All Images", images.size, images.firstOrNull()?.path ?: "", MediaType.IMAGE, true)

            //Add Items by Group
            images.groupBy { it.folderName }.forEach { (folderName, mediaItems) ->
                val album = Album(
                    name = folderName,
                    mediaCount = mediaItems.size,
                    thumbnailPath = mediaItems.firstOrNull()?.path.orEmpty(),
                    type = MediaType.IMAGE
                )
                albumMap[folderName] = album
            }
        }

        if (videos.isNotEmpty()) {
            // Add special albums
            albumMap["All Videos"] =
                Album("All Videos", videos.size, videos.firstOrNull()?.path ?: "", MediaType.VIDEO, true)

            //Add Items by Group
            videos.groupBy { it.folderName }.forEach { (folderName, mediaItems) ->
                val album = Album(
                    name = folderName,
                    mediaCount = mediaItems.size,
                    thumbnailPath = mediaItems.firstOrNull()?.path ?: "",
                    type = MediaType.VIDEO
                )
                val existingAlbum = albumMap[folderName]
                if (existingAlbum != null) {
                    albumMap[folderName] = existingAlbum.copy(
                        mediaCount = existingAlbum.mediaCount + album.mediaCount
                    )
                } else {
                    albumMap[folderName] = album
                }
            }
        }

        emit(
            albumMap.values.map { it }
        )
    }

    private fun fetchMediaByType(type: MediaType): List<MediaFile> {
        val mediaItems = mutableListOf<MediaFile>()
        val uri = MediaStore.Files.getContentUri("external")

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
        val selectionArg = when (type) {
            MediaType.IMAGE -> MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString()
            MediaType.VIDEO -> MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
        )

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            uri,
            projection,
            selection,
            arrayOf(selectionArg),
            sortOrder
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val name = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                var folderName = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                folderName = folderName ?: run {
                    val items = path.split("/")
                    items[items.size - 2]
                }
                val date = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
                mediaItems.add(
                    MediaFile(
                        id = id,
                        name = name,
                        folderName = folderName,
                        path = path,
                        dateAdded = date,
                        type = type
                    )
                )
            }
        }
        return mediaItems
    }
}
