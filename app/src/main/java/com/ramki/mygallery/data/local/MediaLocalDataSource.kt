package com.ramki.mygallery.data.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.model.MediaFile
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType
import com.ramki.mygallery.extention.getDay
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val ALL_IMAGES = "All Images"
private const val ALL_VIDEOS = "All Videos"

class MediaLocalDataSource @Inject constructor(private val context: Context) {

    fun getAlbums(): Flow<List<Album>> = flow {
        val albumMap = mutableMapOf<String, Album>()

        // Fetch images & Videos
        val images = fetchMediaByType(MediaType.IMAGE)
        val videos = fetchMediaByType(MediaType.VIDEO)

        if (images.isNotEmpty()) {
            // Add special albums
            albumMap[ALL_IMAGES] =
                Album(ALL_IMAGES, images.size, images.firstOrNull()?.path ?: "", MediaType.IMAGE, true)

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
            albumMap[ALL_VIDEOS] =
                Album(ALL_VIDEOS, videos.size, videos.firstOrNull()?.path ?: "", MediaType.VIDEO, true)

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

    fun getMediaByAlbum(
        albumName: String,
        type: MediaType,
        isContainsAll: Boolean,
        pageSize: Int,
        pageNumber: Int
    ): Pair<List<MediaGroup>, Int> {
        val imagePaths = mutableListOf<MediaFile>()

        val uri = when (type) {
            MediaType.IMAGE -> MediaQuery.MediaStoreImageUri
            MediaType.VIDEO -> MediaQuery.MediaStoreVideoUri
        }
        val selection = if (isContainsAll) null else when (type) {
            MediaType.IMAGE -> MediaQuery.Selection.ImageAlbum
            MediaType.VIDEO -> MediaQuery.Selection.VideoAlbum
        }
        val selectionArgs = if (isContainsAll) null else arrayOf(albumName)
        val projection = MediaQuery.AlbumProjection
        val sortOrder = when (type) {
            MediaType.IMAGE -> MediaQuery.SortOrder.Image
            MediaType.VIDEO -> MediaQuery.SortOrder.Video
        }
        val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        cursor?.use {
            val totalImages = it.count // Get total count of images in the album
            val startIndex = (pageNumber - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, totalImages)
            if (it.moveToPosition(startIndex)) {
                for (i in 0 until (endIndex - startIndex)) {
                    imagePaths.add(getMediaFile(it, type))
                    it.moveToNext()
                }
            }

            val data = imagePaths.groupBy { it.dateAdded.getDay() }.map {
                MediaGroup(it.key, it.value)
            }
            return Pair(data, totalImages)
        }

        return Pair(emptyList(), 0)
    }

    private fun fetchMediaByType(type: MediaType): List<MediaFile> {
        val uri = when (type) {
            MediaType.IMAGE -> MediaQuery.MediaStoreImageUri
            MediaType.VIDEO -> MediaQuery.MediaStoreVideoUri
        }
        val selection = null
        val selectionArgs = null
        val projection = MediaQuery.AlbumProjection
        val sortOrder = when (type) {
            MediaType.IMAGE -> MediaQuery.SortOrder.Image
            MediaType.VIDEO -> MediaQuery.SortOrder.Video
        }
        val cursor = context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        return getMediaFiles(cursor, type)
    }

    private fun getMediaFiles(cursor: Cursor?, type: MediaType): List<MediaFile> {
        val mediaItems = mutableListOf<MediaFile>()
        cursor?.use {
            while (it.moveToNext()) {
                mediaItems.add(getMediaFile(it, type))
            }
        }
        return mediaItems
    }

    private fun getMediaFile(cursor: Cursor, type: MediaType): MediaFile {
        return with(cursor) {
            val id = getLong(getColumnIndexOrThrow(MediaStore.Images.Media._ID))
            val name = getString(getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            val path = getString(getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            var folderName = getString(getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
            folderName = folderName ?: run {
                val items = path.split("/")
                items[items.size - 2]
            }
            val date = getLong(getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
            val duration = getLong(getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
            MediaFile(
                id = id,
                name = name,
                folderName = folderName,
                path = path,
                dateAdded = date,
                type = type,
                duration = duration
            )
        }
    }
}
