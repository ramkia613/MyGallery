package com.ramki.mygallery.ui.screens.albumdetail

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType
import com.ramki.mygallery.extention.getDuration
import com.ramki.mygallery.navigation.AppDestination
import com.ramki.mygallery.ui.screens.gallery.component.GalleryImage

@Composable
fun AlbumDetailScreen(
    viewModel: AlbumDetailViewModel = hiltViewModel(),
    albumDetail: AppDestination.AlbumDetail,
    onBackClick: () -> Unit
) {
    val name = viewModel.name.collectAsStateWithLifecycle().value
    val pagingItems = viewModel.mediaGroup.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.getAlbumMedia(albumDetail)
    }
    AlbumDetailContent(
        name = name,
        pagingItems = pagingItems,
        onBackClick = onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailContent(
    name: String,
    pagingItems: LazyPagingItems<MediaGroup>,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = name)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(pagingItems.itemCount) { index ->
                val item = pagingItems[index]!!
                AlbumItems(item)
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AlbumItems(mediaGroup: MediaGroup) {
    val configuration = LocalConfiguration.current

    val screenWidth = with(LocalDensity.current) {
        configuration.screenWidthDp.dp / 4.3f
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


@Preview
@Composable
private fun AlbumDetailScreenScreen() {

}