package com.ramki.mygallery.ui.screens.gallery

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramki.mygallery.R
import com.ramki.mygallery.data.model.Album
import com.ramki.mygallery.data.model.MediaType
import com.ramki.mygallery.ui.screens.gallery.component.AlbumGridItem
import com.ramki.mygallery.ui.screens.gallery.component.AlbumListItem
import com.ramki.mygallery.utils.PermissionHandler

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    navigateToPermission: () -> Unit,
    navigateToAlbum: (Album) -> Unit
) {
    val albums = galleryViewModel.albums.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val permissionHandler = remember { PermissionHandler(context) }
    val isGridView = remember { mutableStateOf(true) }

    val permissionLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions.all { it.value }
            if (granted) {
                galleryViewModel.getAlbums()
            } else {
                navigateToPermission()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!permissionHandler.hasStoragePermission()) {
            permissionLaunch.launch(permissionHandler.getRequiredPermissions())
        } else {
            galleryViewModel.getAlbums()
        }
    }

    GalleryContent(
        albums = albums,
        isGridView = isGridView,
        onItemClick = navigateToAlbum
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryContent(
    albums: List<Album>,
    isGridView: MutableState<Boolean>,
    onItemClick: (Album) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.gallery_title))
                },
                actions = {
                    IconButton(
                        onClick = { isGridView.value = !isGridView.value }
                    ) {
                        Icon(
                            painter = if (isGridView.value) {
                                painterResource(R.drawable.ic_list_view)
                            } else {
                                painterResource(R.drawable.ic_grid_view)
                            },
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->

        if (isGridView.value) {
            AlbumGrid(innerPadding, albums, onItemClick)
        } else {
            AlbumList(innerPadding, albums, onItemClick)
        }
    }
}

@Composable
fun AlbumGrid(
    innerPadding: PaddingValues,
    albums: List<Album>,
    onItemClick: (Album) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(innerPadding),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(albums) {
            AlbumGridItem(it, onItemClick)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}


@Composable
fun AlbumList(
    innerPadding: PaddingValues,
    albums: List<Album>,
    onItemClick: (Album) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(innerPadding)
    ) {
        items(albums, key = { it.name }) {
            AlbumListItem(it, onItemClick)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Preview
@Composable
private fun GalleryScreenPreview() {
    val isGridView = remember { mutableStateOf(false) }
    GalleryContent(
        listOf(
            Album(
                name = "Album 1",
                mediaCount = 5,
                thumbnailPath = "path/to/thumbnail1.jpg",
                type = MediaType.IMAGE
            ),
            Album(
                name = "Album 2",
                mediaCount = 5,
                thumbnailPath = "path/to/thumbnail1.jpg",
                type = MediaType.IMAGE
            )
        ),
        isGridView = isGridView,
        onItemClick = {}
    )
}
