package com.app.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun CatalogTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Colors.Canvas)
            .padding(horizontal = Spacing.Base, vertical = Spacing.Sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Theme.typography.headingSm,
            color = Colors.InkDeep,
        )
        if (onBackClick != null) {
            Text(
                text = "Back",
                style = Theme.typography.buttonMd,
                color = Colors.Primary,
                modifier = Modifier.clickable(onClick = onBackClick),
            )
        }
    }
}
