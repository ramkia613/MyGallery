package com.ramki.mygallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.ramki.mygallery.ui.screens.albumdetail.AlbumDetailScreen
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
                },
                navigateToAlbum = {
                    navController.navigate(
                        AppDestination.AlbumDetail(
                            name = it.name,
                            type = it.type,
                            isContainsAll = it.isContainsAll
                        )
                    )
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

        composable<AppDestination.AlbumDetail> {
            val albumDetail = it.toRoute<AppDestination.AlbumDetail>()
            AlbumDetailScreen(
                albumDetail = albumDetail,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
