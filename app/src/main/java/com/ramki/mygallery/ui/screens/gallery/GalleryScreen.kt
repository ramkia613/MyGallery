@file:OptIn(ExperimentalMaterial3Api::class)

package com.ramki.mygallery.ui.screens.gallery

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GalleryScreen(
    onPermission: () -> Unit
) {

    GalleryContent(
        onPermission = onPermission
    )
}

@Composable
private fun GalleryContent(
    onPermission: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Gallery")
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->

        Button(
            onClick = onPermission,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(text = "Get Permission")
        }

    }
}

@Preview
@Composable
private fun GalleryScreenPreview() {
    GalleryContent() {

    }
}