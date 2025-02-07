package com.ramki.mygallery.ui.screens.gallery.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.ui.component.GalleryImage


@Composable
fun AlbumGridItem(
    album: Album,
    onItemClick: (Album) -> Unit
) {
    Column(
        modifier = Modifier.clickable { onItemClick(album) },
    ) {
        GalleryImage(
            modifier = Modifier
                .fillMaxSize(),
            path = album.thumbnailPath,
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(text = album.name)
        Text(text = "${album.mediaCount} items")
    }
}
