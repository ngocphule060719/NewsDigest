package com.app.designsystem.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class CardVisuals(
    val backgroundColor: Color,
    val borderColor: Color,
)

@Composable
internal fun resolveCardVisuals(
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    default: CardVisuals,
    pressed: CardVisuals,
    disabled: CardVisuals,
): CardVisuals {
    val isPressed by interactionSource.collectIsPressedAsState()

    return when {
        !enabled -> disabled
        isPressed -> pressed
        else -> default
    }
}

/**
 * Flat marketing card surface — elevation level 0 per Meta do's/don'ts.
 *
 * No shadow; border + rounding carry the card hierarchy.
 */
@Composable
internal fun FlatCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape,
    borderWidth: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    defaultVisuals: CardVisuals,
    pressedVisuals: CardVisuals,
    disabledVisuals: CardVisuals,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    val visuals = resolveCardVisuals(
        enabled = enabled,
        interactionSource = interactionSource,
        default = defaultVisuals,
        pressed = pressedVisuals,
        disabled = disabledVisuals,
    )
    val border = BorderStroke(borderWidth, visuals.borderColor)

    if (onClick != null) {
        Surface(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            color = visuals.backgroundColor,
            border = border,
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            interactionSource = interactionSource,
        ) {
            Column(content = content)
        }
    } else {
        Surface(
            modifier = modifier,
            shape = shape,
            color = visuals.backgroundColor,
            border = border,
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
        ) {
            Column(content = content)
        }
    }
}
