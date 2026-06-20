package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Elevation
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

private val AppBarHeight = 64.dp

@Composable
fun ArticleListAppBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = Colors.Canvas,
        shadowElevation = Elevation.Level1,
        tonalElevation = 0.dp,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppBarHeight)
                    .padding(horizontal = Spacing.Base)
                    .background(Colors.Canvas),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "News Digest",
                    style = Theme.typography.headingLg.copy(fontWeight = FontWeight.Bold),
                    color = Colors.Ink,
                )
            }

            HorizontalDivider(color = Colors.Hairline, thickness = 1.dp)
        }
    }
}
