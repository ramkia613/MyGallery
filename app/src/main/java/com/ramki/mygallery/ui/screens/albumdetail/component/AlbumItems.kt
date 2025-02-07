package com.ramki.mygallery.ui.screens.albumdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType
import com.ramki.mygallery.extention.getDuration
import com.ramki.mygallery.ui.component.GalleryImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlbumItems(mediaGroup: MediaGroup) {
    val configuration = LocalConfiguration.current

    val screenWidth = with(LocalDensity.current) {
        configuration.screenWidthDp.dp / 4.5f
    }

    Text(text = mediaGroup.day)

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        mediaGroup.mediaFiles.forEach {
            Box(
                modifier = Modifier.size(screenWidth),
                contentAlignment = Alignment.TopEnd
            ) {
                GalleryImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    path = it.path
                )
                if (it.type == MediaType.VIDEO) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.duration.getDuration(),
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        )
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
}
