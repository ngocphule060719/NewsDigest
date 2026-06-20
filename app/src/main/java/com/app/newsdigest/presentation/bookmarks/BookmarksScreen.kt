package com.app.newsdigest.presentation.bookmarks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.app.designsystem.foundation.Colors
import com.app.newsdigest.presentation.articlelist.ArticleListAppBar
import com.app.newsdigest.presentation.articlelist.ArticleListBottomNav
import com.app.newsdigest.presentation.common.AppSnackbarHost

@Composable
fun BookmarksScreen(
    state: BookmarksUiState,
    onIntent: (BookmarksIntent) -> Unit,
    onOpenNews: () -> Unit,
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = modifier.testTag("bookmarks_screen"),
        containerColor = Colors.Canvas,
        snackbarHost = {
            AppSnackbarHost(snackbarHostState = snackbarHostState)
        },
        topBar = { ArticleListAppBar() },
        bottomBar = {
            ArticleListBottomNav(
                isNewsSelected = false,
                onNewsClick = onOpenNews,
                onSavedClick = {},
            )
        },
    ) { padding ->
        BookmarksContent(
            state = state,
            onIntent = onIntent,
            onOpenDetail = onOpenDetail,
            onOpenNews = onOpenNews,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        )
    }
}
