package com.app.newsdigest.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.designsystem.theme.Theme
import com.app.newsdigest.presentation.articlelist.ArticleListScreen
import com.app.newsdigest.presentation.articlelist.ArticleListViewModel
import com.app.newsdigest.presentation.bookmarks.BookmarksScreen
import com.app.newsdigest.presentation.bookmarks.BookmarksViewModel
import com.app.newsdigest.presentation.common.CollectOnceSnackbar
import com.app.newsdigest.presentation.common.launchOpenInBrowser
import com.app.newsdigest.presentation.common.launchShareArticle
import com.app.newsdigest.presentation.detail.ArticleDetailScreen
import com.app.newsdigest.presentation.detail.ArticleDetailViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    deepLinkDestination: String? = null,
) {
    LaunchedEffect(deepLinkDestination) {
        val destination = deepLinkDestination ?: return@LaunchedEffect
        navController.navigate(destination) {
            popUpTo(AppRoutes.ARTICLE_LIST) {
                inclusive = destination == AppRoutes.ARTICLE_LIST
            }
            launchSingleTop = true
        }
    }

    Theme {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.ARTICLE_LIST,
            modifier = modifier,
        ) {
            composable(AppRoutes.ARTICLE_LIST) {
                val viewModel: ArticleListViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                CollectOnceSnackbar(
                    flowSnackbarMessage = viewModel.flowSnackbarMessage,
                    snackbarHostState = snackbarHostState,
                )

                ArticleListScreen(
                    state = state,
                    onIntent = viewModel::onIntent,
                    snackbarHostState = snackbarHostState,
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
                val viewModel: ArticleDetailViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }
                val context = LocalContext.current
                val scope = rememberCoroutineScope()

                CollectOnceSnackbar(
                    flowSnackbarMessage = viewModel.flowSnackbarMessage,
                    snackbarHostState = snackbarHostState,
                )

                CollectOnce(
                    onceFlow = viewModel.shareArticle,
                    onEvent = { payload ->
                        launchShareArticle(
                            context = context,
                            payload = payload,
                            snackbarHostState = snackbarHostState,
                            scope = scope,
                        )
                    },
                )

                CollectOnce(
                    onceFlow = viewModel.openInBrowser,
                    onEvent = { payload ->
                        launchOpenInBrowser(
                            context = context,
                            payload = payload,
                            snackbarHostState = snackbarHostState,
                            scope = scope,
                        )
                    },
                )

                ArticleDetailScreen(
                    state = state,
                    onIntent = viewModel::onIntent,
                    snackbarHostState = snackbarHostState,
                    onBack = { navController.popBackStack() },
                )
            }
            composable(AppRoutes.BOOKMARKS) {
                val viewModel: BookmarksViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                CollectOnceSnackbar(
                    flowSnackbarMessage = viewModel.flowSnackbarMessage,
                    snackbarHostState = snackbarHostState,
                )

                BookmarksScreen(
                    state = state,
                    onIntent = viewModel::onIntent,
                    snackbarHostState = snackbarHostState,
                    onOpenNews = {
                        navController.navigate(AppRoutes.ARTICLE_LIST) {
                            popUpTo(AppRoutes.ARTICLE_LIST) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onOpenDetail = { id -> navController.navigate(AppRoutes.articleDetail(id)) },
                )
            }
        }
    }
}
