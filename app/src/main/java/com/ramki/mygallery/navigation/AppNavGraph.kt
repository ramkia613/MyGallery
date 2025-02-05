package com.ramki.mygallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ramki.mygallery.ui.screens.gallery.GalleryScreen
import com.ramki.mygallery.ui.screens.permission.PermissionScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Gallery
    ) {
        composable<AppDestination.Gallery> {
            GalleryScreen(
                onPermission = {
                    navController.navigate(AppDestination.Permission)
                }
            )
        }

        composable<AppDestination.Permission> {
            PermissionScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}
