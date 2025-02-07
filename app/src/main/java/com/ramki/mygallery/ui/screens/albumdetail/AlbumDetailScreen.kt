package com.ramki.mygallery.ui.screens.albumdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramki.mygallery.data.model.MediaFile
import com.ramki.mygallery.data.model.MediaGroup
import com.ramki.mygallery.data.model.MediaType
import com.ramki.mygallery.navigation.AppDestination
import com.ramki.mygallery.ui.screens.albumdetail.component.AlbumItems
import kotlinx.coroutines.flow.flowOf

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
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
        ) {
            items(pagingItems.itemCount) { index ->
                val item = pagingItems[index]!!
                AlbumItems(item)
            }
        }

    }
}

@Preview
@Composable
private fun AlbumDetailScreenScreen() {
    val mediaFile = MediaFile(
        id = 1L,
        path = "",
        name = "Name",
        folderName = "MyFolder",
        dateAdded = 0L,
        type = MediaType.IMAGE,

        )

    val mediaGroup = MediaGroup(
        day = "Today",
        mediaFiles = listOf(mediaFile)
    )
    val pagingItems = flowOf(
        PagingData.from(listOf(mediaGroup))
    ).collectAsLazyPagingItems()

    AlbumDetailContent(
        name = "Album Name",
        pagingItems = pagingItems,
        onBackClick = {}
    )
}
