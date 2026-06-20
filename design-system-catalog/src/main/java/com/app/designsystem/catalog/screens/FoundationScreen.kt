package com.app.designsystem.catalog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun FoundationScreen() {
    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            CatalogSectionTitle(title = "Color swatches", tokenGroup = "Colors")
        }
        item {
            ColorSwatchGrid()
        }
        item {
            CatalogSectionTitle(title = "Type ramp", tokenGroup = "Typography")
        }
        item {
            TypeRampList()
        }
        item {
            CatalogSectionTitle(title = "Spacing ruler", tokenGroup = "Spacing")
        }
        item {
            SpacingRuler()
        }
        item {
            CatalogSectionTitle(title = "Shape samples", tokenGroup = "Shapes")
        }
        item {
            ShapeSamples()
        }
    }
}

@Composable
private fun ColorSwatchGrid() {
    val swatches = listOf(
        "Primary" to Colors.Primary,
        "PrimaryDeep" to Colors.PrimaryDeep,
        "InkButton" to Colors.InkButton,
        "Success" to Colors.Success,
        "Warning" to Colors.Warning,
        "Critical" to Colors.Critical,
        "Canvas" to Colors.Canvas,
        "SurfaceSoft" to Colors.SurfaceSoft,
        "Hairline" to Colors.Hairline,
        "InkDeep" to Colors.InkDeep,
        "Ink" to Colors.Ink,
        "Charcoal" to Colors.Charcoal,
        "Steel" to Colors.Steel,
    )

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.Sm)) {
        swatches.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Sm),
            ) {
                row.forEach { (name, color) ->
                    ColorSwatch(
                        name = name,
                        color = color,
                        modifier = Modifier.weight(1f),
                    )
                }
                if (row.size == 1) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(Shapes.Md))
                .background(color)
                .border(1.dp, Colors.Hairline, RoundedCornerShape(Shapes.Md)),
        )
        Text(
            text = name,
            style = Theme.typography.caption,
            color = Colors.Charcoal,
            modifier = Modifier.padding(top = Spacing.Xxs),
        )
    }
}

@Composable
private fun TypeRampList() {
    val styles = listOf(
        "heroDisplay" to Theme.typography.heroDisplay,
        "displayLg" to Theme.typography.displayLg,
        "headingLg" to Theme.typography.headingLg,
        "headingMd" to Theme.typography.headingMd,
        "headingSm" to Theme.typography.headingSm,
        "bodyMd" to Theme.typography.bodyMd,
        "bodySm" to Theme.typography.bodySm,
        "caption" to Theme.typography.caption,
        "buttonMd" to Theme.typography.buttonMd,
    )

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.Base)) {
        styles.forEach { (name, style) ->
            Column {
                Text(
                    text = name,
                    style = Theme.typography.caption,
                    color = Colors.Steel,
                )
                Text(
                    text = "The quick brown fox",
                    style = style,
                    color = Colors.InkDeep,
                    modifier = Modifier.padding(top = Spacing.Xxs),
                )
            }
        }
    }
}

@Composable
private fun SpacingRuler() {
    val tokens = listOf(
        "Xxs" to Spacing.Xxs,
        "Xs" to Spacing.Xs,
        "Sm" to Spacing.Sm,
        "Md" to Spacing.Md,
        "Base" to Spacing.Base,
        "Lg" to Spacing.Lg,
        "Xl" to Spacing.Xl,
        "Xxl" to Spacing.Xxl,
        "Section" to Spacing.Section,
        "Hero" to Spacing.Hero,
    )

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.Sm)) {
        tokens.forEach { (name, size) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Sm),
            ) {
                Text(
                    text = name,
                    style = Theme.typography.caption,
                    color = Colors.Charcoal,
                    modifier = Modifier.size(width = 64.dp, height = 20.dp),
                )
                Box(
                    modifier = Modifier
                        .size(width = size, height = Spacing.Sm)
                        .clip(RoundedCornerShape(Shapes.Xs))
                        .background(Colors.Primary),
                )
                Text(
                    text = "${size.value.toInt()}dp",
                    style = Theme.typography.caption,
                    color = Colors.Steel,
                )
            }
        }
    }
}

@Composable
private fun ShapeSamples() {
    val shapes = listOf(
        "Xs" to Shapes.Xs,
        "Sm" to Shapes.Sm,
        "Md" to Shapes.Md,
        "Lg" to Shapes.Lg,
        "Xl" to Shapes.Xl,
        "Full" to Shapes.Full,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Sm),
    ) {
        shapes.forEach { (name, radius) ->
            val shape = RoundedCornerShape(radius)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(shape)
                        .background(Colors.SurfaceSoft)
                        .border(1.dp, Colors.Hairline, shape),
                )
                Text(
                    text = name,
                    style = Theme.typography.caption,
                    color = Colors.Steel,
                    modifier = Modifier.padding(top = Spacing.Xxs),
                )
            }
        }
    }
}

@Composable
internal fun CatalogSectionTitle(
    title: String,
    tokenGroup: String? = null,
    metaSource: String? = null,
) {
    Column {
        Text(
            text = title,
            style = Theme.typography.headingMd,
            color = Colors.InkDeep,
        )
        if (tokenGroup != null) {
            Text(
                text = tokenGroup,
                style = Theme.typography.caption,
                color = Colors.Steel,
            )
        }
        if (metaSource != null) {
            Text(
                text = "Source: $metaSource",
                style = Theme.typography.caption,
                color = Colors.Primary,
            )
        }
    }
}

@Composable
internal fun CatalogSample(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.Sm),
    ) {
        Text(
            text = label,
            style = Theme.typography.captionBold,
            color = Colors.Charcoal,
            modifier = Modifier.padding(bottom = Spacing.Xs),
        )
        content()
    }
}
