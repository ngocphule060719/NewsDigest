package com.app.newsdigest.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.app.designsystem.component.badge.BadgePromo
import com.app.designsystem.component.button.GhostButton
import com.app.designsystem.component.button.PrimaryButton
import com.app.designsystem.component.news.ErrorState
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

private const val ERROR_BODY_FALLBACK =
    "Unable to load this article. It may have been removed from your cache."

@Composable
fun ArticleDetailContent(
    state: ArticleDetailUiState,
    onIntent: (ArticleDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading && !state.hasLoadedArticle() -> {
            ArticleDetailSkeleton(modifier = modifier)
        }

        state.error != null && !state.hasLoadedArticle() -> {
            ErrorContent(
                message = state.error ?: ERROR_BODY_FALLBACK,
                onRetry = { onIntent(ArticleDetailIntent.LoadArticle) },
                modifier = modifier,
            )
        }

        else -> {
            LoadedContent(
                state = state,
                onIntent = onIntent,
                modifier = modifier,
            )
        }
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
            .testTag("article_detail_error"),
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
private fun LoadedContent(
    state: ArticleDetailUiState,
    onIntent: (ArticleDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
    ) {
        if (!state.imageUrl.isNullOrBlank()) {
            ArticleDetailHero(
                imageUrl = state.imageUrl,
                contentDescription = state.title,
            )
        }

        Column(
            modifier = Modifier.padding(
                horizontal = Spacing.Base,
                vertical = Spacing.Xl,
            ),
            verticalArrangement = Arrangement.spacedBy(Spacing.Md),
        ) {
            if (!state.categoryLabel.isNullOrBlank()) {
                BadgePromo(text = state.categoryLabel)
            }

            if (state.metadata.isNotBlank()) {
                Text(
                    text = state.metadata,
                    style = Theme.typography.caption,
                    color = Colors.Stone,
                )
            }

            Text(
                text = state.title,
                style = Theme.typography.headingSm,
                color = Colors.Ink,
            )

            state.description?.takeIf { it.isNotBlank() }?.let { description ->
                Text(
                    text = description,
                    style = Theme.typography.subtitleMd,
                    color = Colors.Steel,
                )
            }

            state.content?.takeIf { it.isNotBlank() }?.let { content ->
                Text(
                    text = content,
                    style = Theme.typography.bodyMd,
                    color = Colors.Ink,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Lg))

            PrimaryButton(
                text = "Open in browser",
                onClick = { onIntent(ArticleDetailIntent.OpenInBrowser) },
                modifier = Modifier.fillMaxWidth(),
            )

            GhostButton(
                text = "Share",
                onClick = { onIntent(ArticleDetailIntent.ShareArticle) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.Xs),
            )
        }
    }
}
