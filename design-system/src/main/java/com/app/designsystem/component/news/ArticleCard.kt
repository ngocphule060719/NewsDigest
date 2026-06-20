package com.app.designsystem.component.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.component.card.CardVisuals
import com.app.designsystem.component.card.FlatCard
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

/**
 * News article list card using `card-product-feature` chrome.
 *
 * Layout: 1:1 thumbnail (`product-thumbnail`) + title (`heading-sm`) +
 * source/date metadata (`body-sm`, `steel`) + bookmark toggle icon.
 *
 * @param title Article headline.
 * @param metadata Formatted source and/or publish date (e.g. "BBC News · 2h ago").
 * @param isBookmarked When true, shows filled bookmark icon.
 * @param isLoading When true, renders a skeleton placeholder instead of content.
 * @param thumbnail Optional slot for image content; defaults to a soft placeholder.
 * @param onClick Invoked when the card body is tapped.
 * @param onBookmarkToggle Invoked when the bookmark icon is tapped.
 */
@Composable
fun ArticleCard(
    title: String,
    metadata: String,
    modifier: Modifier = Modifier,
    isBookmarked: Boolean = false,
    isLoading: Boolean = false,
    thumbnail: @Composable (BoxScope.(Modifier) -> Unit)? = null,
    onClick: () -> Unit = {},
    onBookmarkToggle: () -> Unit = {},
) {
    FlatCard(
        modifier = modifier,
        enabled = !isLoading,
        shape = RoundedCornerShape(Shapes.Xxxl),
        onClick = if (isLoading) null else onClick,
        defaultVisuals = CardVisuals(
            backgroundColor = Colors.Canvas,
            borderColor = Colors.HairlineSoft,
        ),
        pressedVisuals = CardVisuals(
            backgroundColor = Colors.SurfaceSoft,
            borderColor = Colors.HairlineSoft,
        ),
        disabledVisuals = CardVisuals(
            backgroundColor = Colors.Canvas,
            borderColor = Colors.Hairline,
        ),
    ) {
        Column(modifier = Modifier.padding(Spacing.Xxl)) {
            if (isLoading) {
                ArticleCardSkeletonContent()
            } else {
                ArticleCardContent(
                    title = title,
                    metadata = metadata,
                    isBookmarked = isBookmarked,
                    thumbnail = thumbnail,
                    onBookmarkToggle = onBookmarkToggle,
                )
            }
        }
    }
}

@Composable
internal fun ArticleCardContent(
    title: String,
    metadata: String,
    isBookmarked: Boolean,
    thumbnail: @Composable (BoxScope.(Modifier) -> Unit)?,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Base),
        verticalAlignment = Alignment.Top,
    ) {
        ArticleCardThumbnail(thumbnail = thumbnail)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.Xs),
        ) {
            Text(
                text = title,
                style = Theme.typography.headingSm,
                color = Colors.InkDeep,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = metadata,
                style = Theme.typography.bodySm,
                color = Colors.Steel,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        BookmarkToggleIcon(
            isBookmarked = isBookmarked,
            onClick = onBookmarkToggle,
        )
    }
}

@Composable
internal fun ArticleCardThumbnail(
    thumbnail: @Composable (BoxScope.(Modifier) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(88.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(Shapes.Xl))
            .background(Colors.SurfaceSoft),
        contentAlignment = Alignment.Center,
    ) {
        if (thumbnail != null) {
            thumbnail(Modifier.fillMaxWidth())
        }
    }
}

@Composable
internal fun ArticleCardSkeletonContent(
    modifier: Modifier = Modifier,
) {
    val skeletonColor = Colors.HairlineSoft
    val skeletonShape = RoundedCornerShape(4.dp)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Base),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .width(88.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(Shapes.Xl))
                .background(skeletonColor),
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.Xs),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(skeletonShape)
                    .background(skeletonColor),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(skeletonShape)
                    .background(skeletonColor),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(14.dp)
                    .clip(skeletonShape)
                    .background(skeletonColor),
            )
        }

        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
                .background(skeletonColor),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleCardDefaultPreview() {
    Theme {
        ArticleCard(
            title = "Global markets rally as inflation data beats expectations",
            metadata = "Reuters · 3h ago",
            isBookmarked = false,
            onClick = {},
            onBookmarkToggle = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleCardBookmarkedPreview() {
    Theme {
        ArticleCard(
            title = "Scientists discover new method for carbon capture at scale",
            metadata = "Nature · Yesterday",
            isBookmarked = true,
            onClick = {},
            onBookmarkToggle = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleCardLoadingPreview() {
    Theme {
        ArticleCard(
            title = "",
            metadata = "",
            isLoading = true,
        )
    }
}
