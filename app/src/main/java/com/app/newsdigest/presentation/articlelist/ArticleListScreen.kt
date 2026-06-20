package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.app.designsystem.foundation.Colors
import com.app.newsdigest.presentation.common.AppSnackbarHost

@Composable
fun ArticleListScreen(
    state: ArticleListUiState,
    onIntent: (ArticleListIntent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onOpenBookmarks: () -> Unit = {},
    onOpenDetail: (String) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = Colors.Canvas,
        snackbarHost = {
            AppSnackbarHost(snackbarHostState = snackbarHostState)
        },
        topBar = { ArticleListAppBar() },
        bottomBar = {
            ArticleListBottomNav(
                isNewsSelected = true,
                onNewsClick = { /* already on Article List */ },
                onSavedClick = onOpenBookmarks,
            )
        },
    ) { padding ->
        ArticleListContent(
            state = state,
            onIntent = onIntent,
            onOpenDetail = onOpenDetail,
            modifier = Modifier.padding(padding),
        )
    }
}
