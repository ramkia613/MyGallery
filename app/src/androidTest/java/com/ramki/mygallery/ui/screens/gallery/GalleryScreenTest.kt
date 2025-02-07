package com.ramki.mygallery.ui.screens.gallery

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ramki.mygallery.test.MockUtil
import org.junit.Rule
import org.junit.Test

class GalleryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun show_gallery_screen_with_albums() {
        val isGridView = mutableStateOf(true)
        composeTestRule.setContent {
            GalleryContent(
                albums = MockUtil.albums,
                isGridView = isGridView,
            ) {

            }
        }

        composeTestRule.onNodeWithText("Gallery").assertIsDisplayed()

        MockUtil.albums.forEach {
            composeTestRule.onNodeWithText(it.name).assertIsDisplayed()
            composeTestRule.onNodeWithText("${it.mediaCount} items").assertIsDisplayed()
        }
    }
}
