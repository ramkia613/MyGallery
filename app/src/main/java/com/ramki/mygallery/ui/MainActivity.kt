package com.ramki.mygallery.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ramki.mygallery.navigation.AppNavGraph
import com.ramki.mygallery.ui.theme.MyGalleryTheme
import dagger.hilt.EntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyGalleryTheme {
                val rememberNavController = rememberNavController()
                AppNavGraph(navController = rememberNavController)
            }
        }
    }
}
