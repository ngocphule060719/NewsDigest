package com.app.newsdigest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.designsystem.theme.Theme
import com.app.newsdigest.presentation.articlelist.ArticleListIntent
import com.app.newsdigest.presentation.articlelist.ArticleListScreen
import com.app.newsdigest.presentation.articlelist.ArticleListUiState
import com.app.newsdigest.presentation.bookmarks.BookmarksScreen
import com.app.newsdigest.presentation.bookmarks.BookmarksUiState
import com.app.newsdigest.presentation.detail.ArticleDetailScreen
import com.app.newsdigest.presentation.detail.ArticleDetailUiState

@Composable
fun TestNavGraph(
    articleListState: ArticleListUiState,
    onArticleListIntent: (ArticleListIntent) -> Unit = {},
    detailState: ArticleDetailUiState = ArticleDetailUiState(isLoading = true),
    bookmarksState: BookmarksUiState = BookmarksUiState(isLoading = true),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    Theme {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.ARTICLE_LIST,
            modifier = modifier,
        ) {
            composable(AppRoutes.ARTICLE_LIST) {
                ArticleListScreen(
                    state = articleListState,
                    onIntent = onArticleListIntent,
                    onOpenDetail = { id -> navController.navigate(AppRoutes.articleDetail(id)) },
                    onOpenBookmarks = { navController.navigate(AppRoutes.BOOKMARKS) },
                )
            }
            composable(
                route = AppRoutes.ARTICLE_DETAIL,
                arguments = listOf(
                    navArgument(AppRoutes.ARG_ARTICLE_ID) { type = NavType.StringType },
                ),
            ) {
                ArticleDetailScreen(
                    state = detailState,
                    onIntent = {},
                    onBack = { navController.popBackStack() },
                )
            }
            composable(AppRoutes.BOOKMARKS) {
                BookmarksScreen(
                    state = bookmarksState,
                    onIntent = {},
                    onOpenNews = { navController.popBackStack() },
                    onOpenDetail = { id -> navController.navigate(AppRoutes.articleDetail(id)) },
                )
            }
        }
    }
}
