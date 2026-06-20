package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun ArticleListOfflineBanner(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .testTag("offline_banner")
            .fillMaxWidth()
            .background(Colors.WarningBg)
            .padding(horizontal = Spacing.Base, vertical = Spacing.Sm),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.WifiOff,
            contentDescription = null,
            modifier = Modifier
                .padding(end = Spacing.Xs)
                .size(20.dp),
            tint = Colors.InkDeep,
        )
        Text(
            text = "You're offline — showing saved headlines",
            style = Theme.typography.bodySmBold,
            color = Colors.InkDeep,
        )
    }
}
