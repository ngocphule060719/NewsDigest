package com.app.newsdigest.presentation.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.component.button.GhostButton
import com.app.newsdigest.presentation.navigation.PlaceholderScreen

@Composable
fun BookmarksPlaceholder(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlaceholderScreen(
        title = "Bookmarks",
        subtitle = "Phase 6 — offline saved articles",
        modifier = modifier,
        actions = {
            GhostButton(text = "Back", onClick = onBack)
        },
    )
}
