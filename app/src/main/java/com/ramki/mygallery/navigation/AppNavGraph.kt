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
                navigateToPermission = {
                    navController.navigate(AppDestination.Permission) {
                        popUpTo(AppDestination.Gallery) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<AppDestination.Permission> {
            PermissionScreen(
                navigateToGallery = {
                    navController.navigate(AppDestination.Gallery) {
                        popUpTo(AppDestination.Permission) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}
