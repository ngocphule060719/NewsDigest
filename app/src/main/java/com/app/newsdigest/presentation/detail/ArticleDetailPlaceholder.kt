package com.app.newsdigest.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.component.button.GhostButton
import com.app.newsdigest.presentation.navigation.PlaceholderScreen

@Composable
fun ArticleDetailPlaceholder(
    articleId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlaceholderScreen(
        title = "Article Detail",
        subtitle = "articleId: $articleId",
        modifier = modifier,
        actions = {
            GhostButton(text = "Back", onClick = onBack)
        },
    )
}
