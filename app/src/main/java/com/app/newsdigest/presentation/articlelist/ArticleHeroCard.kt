package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.designsystem.component.news.BookmarkToggleIcon
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun ArticleHeroCard(
    title: String,
    excerpt: String,
    metadata: String,
    imageUrl: String?,
    isBookmarked: Boolean,
    isOffline: Boolean,
    onClick: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val cardShape = Shapes.roundedXxxl
    val cardModifier = modifier
        .fillMaxWidth()
        .then(if (isOffline) Modifier.alpha(0.8f) else Modifier)

    Surface(
        modifier = cardModifier
            .testTag("article_card")
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = Colors.Hairline, shape = cardShape),
        shape = cardShape,
        color = Colors.Canvas,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ArticleThumbnail(
                imageUrl = imageUrl,
                size = ArticleThumbnailSize.Hero,
                isGrayscale = isOffline,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                modifier = Modifier.padding(Spacing.Xxl),
                verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
            ) {
                Text(
                    text = title,
                    style = Theme.typography.subtitleLg,
                    color = Colors.Ink,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = excerpt,
                    style = Theme.typography.bodyMd,
                    color = Colors.Steel,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                HorizontalDivider(color = Colors.HairlineSoft)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = metadata,
                        style = Theme.typography.caption,
                        color = Colors.Stone,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                    BookmarkToggleIcon(
                        isBookmarked = isBookmarked,
                        onClick = onBookmarkToggle,
                        modifier = Modifier.padding(start = Spacing.Sm),
                    )
                }
            }
        }
    }
}
