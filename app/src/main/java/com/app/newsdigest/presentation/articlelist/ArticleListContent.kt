package com.app.newsdigest.presentation.articlelist

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.app.designsystem.component.news.ArticleCard
import com.app.designsystem.component.news.ErrorState
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing

@Composable
fun ArticleListContent(
    state: ArticleListUiState,
    onIntent: (ArticleListIntent) -> Unit,
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        ArticleListCategoryTabsSection(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.padding(top = Spacing.Sm),
        )

        when {
            state.isLoading -> {
                ArticleListHeroSkeleton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = Spacing.Sm),
                )
            }

            state.isLoadingArticles -> {
                ArticleListHeroSkeleton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = Spacing.Sm),
                )
            }

            state.error != null && state.articles.isEmpty() -> {
                ErrorContent(
                    message = state.error,
                    onRetry = { onIntent(ArticleListIntent.Refresh) },
                    modifier = Modifier.weight(1f),
                )
            }

            state.isEmptyCategory -> {
                EmptyContent(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier.weight(1f),
                )
            }

            else -> {
                FeedContent(
                    state = state,
                    onIntent = onIntent,
                    onOpenDetail = onOpenDetail,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ArticleListCategoryTabsSection(
    state: ArticleListUiState,
    onIntent: (ArticleListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        CategoryTabsSkeleton(
            modifier = modifier.testTag("category_pill_skeleton"),
        )
    } else {
        ArticleListCategoryTabs(
            tabs = state.categoryTabs,
            onSelect = { category -> onIntent(ArticleListIntent.SelectCategory(category)) },
            modifier = modifier,
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("article_list_error"),
        contentAlignment = Alignment.Center,
    ) {
        ErrorState(
            message = message,
            onRetry = onRetry,
            retryLabel = "Try again",
        )
    }
}

@Composable
private fun EmptyContent(
    state: ArticleListUiState,
    onIntent: (ArticleListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categoryLabel = state.categoryTabs
        .firstOrNull { it.isSelected }
        ?.label
        ?: state.selectedCategory.displayLabel()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        ArticleListEmptyState(
            categoryLabel = categoryLabel,
            category = state.selectedCategory,
            onRefresh = { onIntent(ArticleListIntent.Refresh) },
            modifier = Modifier.testTag("empty_category"),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedContent(
    state: ArticleListUiState,
    onIntent: (ArticleListIntent) -> Unit,
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        if (state.isOfflineStale) {
            ArticleListOfflineBanner(
                modifier = Modifier.testTag("offline_banner"),
            )
        }

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onIntent(ArticleListIntent.Refresh) },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            indicator = {
                if (state.isRefreshing) {
                    RefreshSpinner(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = Spacing.Sm),
                    )
                }
            },
        ) {
            ArticleFeedList(
                state = state,
                onIntent = onIntent,
                onOpenDetail = onOpenDetail,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (state.isRefreshing) 0.6f else 1f),
            )
        }
    }
}

@Composable
private fun ArticleFeedList(
    state: ArticleListUiState,
    onIntent: (ArticleListIntent) -> Unit,
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = Spacing.Base,
            vertical = Spacing.Sm,
        ),
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        items(
            items = state.articles,
            key = { it.id },
        ) { article ->
            if (state.useHeroLayout) {
                ArticleHeroCard(
                    title = article.title,
                    excerpt = article.summary.orEmpty(),
                    metadata = article.metadata,
                    imageUrl = article.imageUrl,
                    isBookmarked = article.isBookmarked,
                    isOffline = state.isOfflineStale,
                    onClick = { onOpenDetail(article.id) },
                    onBookmarkToggle = {
                        onIntent(ArticleListIntent.ToggleBookmark(article.id))
                    },
                    contentDescription = article.title,
                )
            } else {
                ArticleCard(
                    title = article.title,
                    metadata = article.metadata,
                    isBookmarked = article.isBookmarked,
                    modifier = Modifier.testTag("article_card"),
                    thumbnail = { thumbnailModifier ->
                        ArticleThumbnail(
                            imageUrl = article.imageUrl,
                            size = ArticleThumbnailSize.Compact,
                            modifier = thumbnailModifier,
                            contentDescription = article.title,
                        )
                    },
                    onClick = { onOpenDetail(article.id) },
                    onBookmarkToggle = {
                        onIntent(ArticleListIntent.ToggleBookmark(article.id))
                    },
                )
            }
        }
    }
}

@Composable
private fun RefreshSpinner(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "pullToRefresh")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "refreshRotation",
    )

    Icon(
        imageVector = Icons.Filled.Refresh,
        contentDescription = "Refreshing",
        modifier = modifier
            .size(32.dp)
            .graphicsLayer { rotationZ = rotation },
        tint = Colors.Primary,
    )
}

@Composable
private fun CategoryTabsSkeleton(
    modifier: Modifier = Modifier,
    pillCount: Int = 4,
) {
    val pillShape = RoundedCornerShape(Shapes.Full)
    val pillWidths = listOf(72.dp, 88.dp, 64.dp, 80.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Base),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Xs),
    ) {
        repeat(pillCount) { index ->
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .width(pillWidths[index % pillWidths.size])
                    .clip(pillShape)
                    .categoryPillShimmer(),
            )
        }
    }
}

@Composable
private fun Modifier.categoryPillShimmer(): Modifier {
    val transition = rememberInfiniteTransition(label = "categoryPillShimmer")
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "categoryPillShimmerTranslate",
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Colors.SurfaceSoft,
            Colors.HairlineSoft,
            Colors.SurfaceSoft,
        ),
        start = Offset(translate - 500f, 0f),
        end = Offset(translate, 0f),
    )

    return background(brush)
}
