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
import com.app.newsdigest.presentation.articlelist.ArticleListPlaceholder
import com.app.newsdigest.presentation.bookmarks.BookmarksPlaceholder
import com.app.newsdigest.presentation.detail.ArticleDetailPlaceholder

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Theme {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.ARTICLE_LIST,
            modifier = modifier,
        ) {
            composable(AppRoutes.ARTICLE_LIST) {
                ArticleListPlaceholder(
                    onOpenDetail = { id -> navController.navigate(AppRoutes.articleDetail(id)) },
                    onOpenBookmarks = { navController.navigate(AppRoutes.BOOKMARKS) },
                )
            }
            composable(
                route = AppRoutes.ARTICLE_DETAIL,
                arguments = listOf(
                    navArgument(AppRoutes.ARG_ARTICLE_ID) { type = NavType.StringType },
                ),
            ) { entry ->
                val articleId = entry.arguments?.getString(AppRoutes.ARG_ARTICLE_ID).orEmpty()
                ArticleDetailPlaceholder(
                    articleId = articleId,
                    onBack = { navController.popBackStack() },
                )
            }
            composable(AppRoutes.BOOKMARKS) {
                BookmarksPlaceholder(
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
