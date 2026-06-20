package com.app.newsdigest.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun PlaceholderScreen(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.Base),
        ) {
            Text(text = title, style = Theme.typography.headingMd, color = Colors.InkDeep)
            Text(
                text = subtitle,
                style = Theme.typography.bodyMd,
                color = Colors.Steel,
                modifier = Modifier.padding(top = Spacing.Sm),
            )
            Row(
                modifier = Modifier.padding(top = Spacing.Xl),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Sm),
                content = actions,
            )
        }
    }
}
