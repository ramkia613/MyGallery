package com.ramki.mygallery.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.video.VideoFrameDecoder

@Composable
fun GalleryImage(modifier: Modifier = Modifier, path: String) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(path)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(true)
        .build()

    AsyncImage(
        model = request,
        imageLoader = imageLoader,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray),
        contentScale = ContentScale.Crop
    )
}
