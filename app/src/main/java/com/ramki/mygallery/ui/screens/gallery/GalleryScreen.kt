@file:OptIn(ExperimentalMaterial3Api::class)

package com.ramki.mygallery.ui.screens.gallery

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramki.mygallery.R
import com.ramki.mygallery.utils.PermissionHandler

@Composable
fun GalleryScreen(
    navigateToPermission: () -> Unit
) {
    val context = LocalContext.current
    val permissionHandler = remember { PermissionHandler(context) }

    val permissionLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions.all { it.value }
            if (granted) {
                //TODO: Get data from View Model
            } else {
                navigateToPermission()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!permissionHandler.hasStoragePermission()) {
            permissionLaunch.launch(permissionHandler.getRequiredPermissions())
        } else {
            //TODO: Get data from View Model
        }
    }

    GalleryContent()
}

@Composable
private fun GalleryContent() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.gallery_title))
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
            //TODO: Add Albums list
        }
    }
}

@Preview
@Composable
private fun GalleryScreenPreview() {
    GalleryContent()
}