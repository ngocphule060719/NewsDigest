package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun ArticleListBottomNav(
    isNewsSelected: Boolean,
    onNewsClick: () -> Unit,
    onSavedClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Colors.Canvas,
        shape = RoundedCornerShape(topStart = Shapes.Xl, topEnd = Shapes.Xl),
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Colors.HairlineSoft),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = Spacing.Xs),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomNavItem(
                label = "News",
                icon = Icons.Filled.Article,
                selected = isNewsSelected,
                onClick = onNewsClick,
            )
            BottomNavItem(
                label = "Saved",
                icon = if (isNewsSelected) Icons.Outlined.BookmarkBorder else Icons.Filled.Bookmark,
                selected = !isNewsSelected,
                onClick = onSavedClick,
                testTag = "bottom_nav_saved",
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String? = null,
) {
    val contentColor = if (selected) Colors.OnInkButton else Colors.InkDeep
    val containerColor = if (selected) Colors.InkButton else Colors.Canvas
    val shape = RoundedCornerShape(Shapes.Full)

    Surface(
        onClick = onClick,
        modifier = modifier
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
            .semantics {
                role = Role.Tab
                contentDescription = label
            },
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Spacing.Xl, vertical = Spacing.Xs),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.Xxs),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = contentColor,
            )
            Text(
                text = label,
                style = Theme.typography.bodySmBold,
                color = contentColor,
            )
        }
    }
}
