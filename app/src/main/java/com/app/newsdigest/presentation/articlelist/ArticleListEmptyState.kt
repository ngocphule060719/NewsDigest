package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Sports
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme
import com.app.newsdigest.domain.model.Category

@Composable
fun ArticleListEmptyState(
    categoryLabel: String,
    category: Category,
    onRefresh: () -> Unit,
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
                imageVector = categoryIcon(category),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Colors.Steel,
            )
        }

        Text(
            text = "No articles in $categoryLabel right now",
            style = Theme.typography.subtitleLg,
            color = Colors.InkDeep,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Xs),
        )

        Text(
            text = "Try checking another category or refreshing.",
            style = Theme.typography.bodyMd,
            color = Colors.Steel,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Base),
        )

        RefreshGhostButton(onClick = onRefresh)
    }
}

@Composable
private fun RefreshGhostButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(Shapes.Full)

    Row(
        modifier = modifier
            .testTag("empty_category_refresh")
            .clip(shape)
            .border(width = 2.dp, color = Colors.InkDeep12, shape = shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 22.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = Colors.InkDeep,
        )
        Text(
            text = "Refresh",
            style = Theme.typography.buttonMd,
            color = Colors.InkDeep,
        )
    }
}

private fun categoryIcon(category: Category): ImageVector {
    return when (category) {
        Category.GENERAL -> Icons.Outlined.Article
        Category.BUSINESS -> Icons.Outlined.BusinessCenter
        Category.ENTERTAINMENT -> Icons.Outlined.Movie
        Category.HEALTH -> Icons.Outlined.Favorite
        Category.SCIENCE -> Icons.Outlined.Science
        Category.SPORTS -> Icons.Outlined.Sports
        Category.TECHNOLOGY -> Icons.Outlined.Computer
    }
}
