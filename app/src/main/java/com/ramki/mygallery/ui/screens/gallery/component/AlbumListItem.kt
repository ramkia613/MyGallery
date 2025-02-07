package com.ramki.mygallery.ui.screens.gallery.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.ui.component.GalleryImage

@Composable
fun AlbumListItem(
    album: Album,
    onItemClick: (Album) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(album) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GalleryImage(
            modifier = Modifier.size(64.dp),
            path = album.thumbnailPath,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = album.name)

            Spacer(modifier = Modifier.size(4.dp))

            Text(text = "${album.mediaCount} items")
        }
    }
}
