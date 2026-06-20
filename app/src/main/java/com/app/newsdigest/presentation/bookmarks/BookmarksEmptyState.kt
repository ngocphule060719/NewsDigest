package com.app.newsdigest.presentation.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.designsystem.component.button.GhostButton
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun BookmarksEmptyState(
    onBrowseNews: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = Spacing.Xs)
                .size(96.dp)
                .clip(CircleShape)
                .background(Colors.SurfaceSoft),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Colors.Steel,
            )
        }

        Text(
            text = "No saved articles yet",
            style = Theme.typography.subtitleLg,
            color = Colors.InkDeep,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Xs),
        )

        Text(
            text = "Articles you bookmark will appear here for offline reading.",
            style = Theme.typography.bodyMd,
            color = Colors.Steel,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Base),
        )

        GhostButton(
            text = "Browse news",
            onClick = onBrowseNews,
            modifier = Modifier.testTag("bookmarks_empty_cta"),
        )
    }
}
