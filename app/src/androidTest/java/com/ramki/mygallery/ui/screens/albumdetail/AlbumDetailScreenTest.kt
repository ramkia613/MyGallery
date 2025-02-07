package com.ramki.mygallery.ui.screens.albumdetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramki.mygallery.test.MockUtil
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class AlbumDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun show_screen_with_album_name_and_media_groups() {
        val expectedName = "Test Album"
        composeTestRule.setContent {

            val pagingItems = flowOf(PagingData.from(MockUtil.mediaGroups)).collectAsLazyPagingItems()

            AlbumDetailContent(
                name = expectedName,
                pagingItems = pagingItems,
                onBackClick = {}
            )
        }

        composeTestRule.onNodeWithText(expectedName).assertIsDisplayed()

        MockUtil.mediaGroups.forEach {
            composeTestRule.onNodeWithText(it.day).assertIsDisplayed()
        }
    }
}
