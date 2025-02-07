package com.ramki.mygallery.ui.screens.permission

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class PermissionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun show_permission_screen() {
        composeTestRule.setContent {
            PermissionContent { }
        }

        composeTestRule.onNodeWithText("To continue using MyGallery, please grant the necessary permissions.")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Open Settings").assertIsDisplayed()
    }
}
