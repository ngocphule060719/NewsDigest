package com.app.newsdigest.presentation.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.app.designsystem.foundation.Colors
import com.app.newsdigest.presentation.common.AppSnackbarHost

@Composable
fun ArticleDetailScreen(
    state: ArticleDetailUiState,
    onIntent: (ArticleDetailIntent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val isErrorOnly = state.error != null && !state.hasLoadedArticle()
    val isLoading = state.isLoading && !state.hasLoadedArticle()

    Scaffold(
        modifier = modifier.testTag("article_detail_screen"),
        containerColor = Colors.Canvas,
        snackbarHost = {
            AppSnackbarHost(snackbarHostState = snackbarHostState)
        },
        topBar = {
            ArticleDetailAppBar(
                title = state.title,
                isBookmarked = state.isBookmarked,
                onBackClick = onBack,
                onBookmarkClick = { onIntent(ArticleDetailIntent.ToggleBookmark) },
                onShareClick = { onIntent(ArticleDetailIntent.ShareArticle) },
                showActions = !isErrorOnly,
                showTitleShimmer = isLoading,
            )
        },
    ) { padding ->
        ArticleDetailContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.padding(padding),
        )
    }
}
