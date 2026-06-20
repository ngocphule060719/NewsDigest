package com.app.newsdigest.presentation.bookmarks

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.app.designsystem.theme.Theme
import org.junit.Rule
import org.junit.Test

class BookmarksScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun empty_showsCta() {
        composeTestRule.setContent {
            Theme {
                BookmarksScreen(
                    state = BookmarksUiState(isLoading = false, bookmarks = emptyList()),
                    onIntent = {},
                    onOpenNews = {},
                    onOpenDetail = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("bookmarks_screen").assertExists()
        composeTestRule.onNodeWithText("No saved articles yet").assertExists()
        composeTestRule.onNodeWithTag("bookmarks_empty_cta").assertExists()
        composeTestRule.onNodeWithText("Browse news").assertExists()
    }
}
