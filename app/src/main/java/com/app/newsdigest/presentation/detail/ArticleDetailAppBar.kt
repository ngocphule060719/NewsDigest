package com.app.newsdigest.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.designsystem.component.button.PlainIconButton
import com.app.designsystem.component.news.BookmarkToggleIcon
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Elevation
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

private val AppBarHeight = 64.dp

@Composable
fun ArticleDetailAppBar(
    title: String,
    isBookmarked: Boolean,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
    showActions: Boolean = true,
    showTitleShimmer: Boolean = false,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = Colors.Canvas,
        shadowElevation = Elevation.Level1,
        tonalElevation = 0.dp,
    ) {
        ColumnWithDivider {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppBarHeight)
                    .padding(horizontal = Spacing.Base)
                    .background(Colors.Canvas),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PlainIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    onClick = onBackClick,
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Spacing.Sm),
                    contentAlignment = Alignment.Center,
                ) {
                    when {
                        showTitleShimmer -> {
                            TitleShimmerBar()
                        }

                        title.isNotBlank() -> {
                            Text(
                                text = title,
                                style = Theme.typography.subtitleMd.copy(fontWeight = FontWeight.SemiBold),
                                color = Colors.Ink,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        else -> {
                            Text(
                                text = "ND",
                                style = Theme.typography.headingLg.copy(fontWeight = FontWeight.Bold),
                                color = Colors.Ink,
                            )
                        }
                    }
                }

                if (showActions) {
                    BookmarkToggleIcon(
                        isBookmarked = isBookmarked,
                        onClick = onBookmarkClick,
                    )
                    PlainIconButton(
                        icon = Icons.Filled.Share,
                        contentDescription = "Share",
                        onClick = onShareClick,
                        modifier = Modifier.padding(start = Spacing.Xs),
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnWithDivider(
    content: @Composable () -> Unit,
) {
    androidx.compose.foundation.layout.Column {
        content()
        HorizontalDivider(color = Colors.Hairline, thickness = 1.dp)
    }
}

@Composable
private fun TitleShimmerBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.6f)
            .height(20.dp)
            .shimmerBackground(shape = RoundedCornerShape(Shapes.Sm)),
    )
}
