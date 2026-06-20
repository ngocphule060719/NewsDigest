package com.app.newsdigest.presentation.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.app.newsdigest.presentation.articlelist.ArticleListTestFixtures
import com.app.newsdigest.presentation.bookmarks.BookmarksUiState
import org.junit.Rule
import org.junit.Test

class NavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNav_saved_opensBookmarksRoute() {
        composeTestRule.setContent {
            TestNavGraph(
                articleListState = ArticleListTestFixtures.loadedState(),
                bookmarksState = BookmarksUiState(isLoading = false, bookmarks = emptyList()),
            )
        }

        composeTestRule.onNodeWithTag("bottom_nav_saved").performClick()

        composeTestRule.onNodeWithTag("bookmarks_screen").assertExists()
        composeTestRule.onNodeWithText("No saved articles yet").assertExists()
    }

    @Test
    fun articleCard_click_opensDetail() {
        composeTestRule.setContent {
            TestNavGraph(
                articleListState = ArticleListTestFixtures.loadedState(),
            )
        }

        composeTestRule.onNodeWithTag("article_card").performClick()

        composeTestRule.onNodeWithTag("article_detail_screen").assertExists()
    }
}
