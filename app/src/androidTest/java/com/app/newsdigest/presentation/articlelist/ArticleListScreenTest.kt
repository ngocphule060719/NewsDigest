package com.app.newsdigest.presentation.articlelist

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.app.designsystem.theme.Theme
import org.junit.Rule
import org.junit.Test

class ArticleListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loading_showsHeroSkeletons() {
        composeTestRule.setContent {
            Theme {
                ArticleListScreen(
                    state = ArticleListTestFixtures.loadingState(),
                    onIntent = {},
                )
            }
        }

        composeTestRule
            .onAllNodesWithTag("hero_skeleton")
            .assertCountEquals(3)
    }

    @Test
    fun error_showsTryAgain() {
        composeTestRule.setContent {
            Theme {
                ArticleListScreen(
                    state = ArticleListTestFixtures.errorState(),
                    onIntent = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Something went wrong").assertExists()
        composeTestRule.onNodeWithText("Try again").assertExists()
    }

    @Test
    fun emptyCategory_showsRefresh() {
        composeTestRule.setContent {
            Theme {
                ArticleListScreen(
                    state = ArticleListTestFixtures.emptyCategoryState(),
                    onIntent = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("No articles in General right now")
            .assertExists()
        composeTestRule.onNodeWithTag("empty_category_refresh").assertExists()
        composeTestRule.onNodeWithText("Refresh").assertExists()
    }

    @Test
    fun offlineBanner_visible_whenStale() {
        composeTestRule.setContent {
            Theme {
                ArticleListScreen(
                    state = ArticleListTestFixtures.offlineStaleState(),
                    onIntent = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("offline_banner").assertExists()
        composeTestRule
            .onNodeWithText("You're offline — showing saved headlines")
            .assertExists()
    }
}
