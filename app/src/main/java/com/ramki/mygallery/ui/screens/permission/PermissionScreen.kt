package com.ramki.mygallery.ui.screens.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramki.mygallery.R
import com.ramki.mygallery.extention.getAppSettingsIntent
import com.ramki.mygallery.utils.PermissionHandler

@Composable
fun PermissionScreen(
    navigateToGallery: () -> Unit
) {
    val context = LocalContext.current
    val permissionHandler = remember { PermissionHandler(context) }

    val appSettingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (permissionHandler.hasStoragePermission()) {
            navigateToGallery()
        }
    }

    PermissionContent(
        onOpenSettings = {
            if (permissionHandler.shouldShowRequestPermissionRationale()) {
                navigateToGallery()
            } else {
                appSettingsLauncher.launch(context.getAppSettingsIntent())
            }
        }
    )
}

@Composable
private fun PermissionContent(
    onOpenSettings: () -> Unit
) {

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = stringResource(R.string.permission_message),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = onOpenSettings,
            ) {
                Text(text = stringResource(R.string.permission_open_settings))
            }
        }
    }

}

@Preview
@Composable
private fun PermissionScreenPreview() {

    PermissionScreen { }
}
