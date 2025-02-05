package com.ramki.mygallery.ui.screens.permission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PermissionScreen(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {

        Button(
            onClick = onBack,
        ) {
            Text(text = "Go to setting")
        }
    }
}

@Preview
@Composable
private fun PermissionScreenPreview() {

    PermissionScreen { }
}
