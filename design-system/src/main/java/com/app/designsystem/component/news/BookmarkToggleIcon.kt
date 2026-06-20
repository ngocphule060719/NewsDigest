package com.app.designsystem.component.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

@Composable
fun BookmarkToggleIcon(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    bookmarkedTint: Color = Colors.PrimaryDeep,
    unbookmarkedTint: Color = Colors.InkDeep,
) {
    val tint = if (isBookmarked) bookmarkedTint else unbookmarkedTint

    Box(
        modifier = modifier
            .size(40.dp)
            .semantics { role = Role.Button }
            .clickable(
                enabled = enabled,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
            modifier = Modifier.size(24.dp),
            tint = tint,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkToggleIconPreview() {
    Theme {
        BookmarkToggleIcon(isBookmarked = false, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkToggleIconBookmarkedPreview() {
    Theme {
        BookmarkToggleIcon(isBookmarked = true, onClick = {})
    }
}
