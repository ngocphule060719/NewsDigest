package com.app.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Shapes

internal data class ButtonVisuals(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color = Color.Transparent,
    val borderWidth: Dp = 0.dp,
)

@Composable
internal fun resolveButtonVisuals(
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    default: ButtonVisuals,
    pressed: ButtonVisuals,
    disabled: ButtonVisuals,
    focused: ButtonVisuals,
): ButtonVisuals {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    return when {
        !enabled -> disabled
        isPressed -> pressed
        isFocused -> focused
        else -> default
    }
}

@Composable
internal fun PillTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues,
    textStyle: TextStyle,
    shape: Shape = RoundedCornerShape(Shapes.Full),
    defaultVisuals: ButtonVisuals,
    pressedVisuals: ButtonVisuals,
    disabledVisuals: ButtonVisuals,
    focusedVisuals: ButtonVisuals,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val visuals = resolveButtonVisuals(
        enabled = enabled,
        interactionSource = interactionSource,
        default = defaultVisuals,
        pressed = pressedVisuals,
        disabled = disabledVisuals,
        focused = focusedVisuals,
    )

    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = 44.dp)
            .semantics { role = Role.Button }
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        shape = shape,
        color = visuals.containerColor,
        contentColor = visuals.contentColor,
        border = if (visuals.borderWidth > 0.dp) {
            BorderStroke(visuals.borderWidth, visuals.borderColor)
        } else {
            null
        },
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            CompositionLocalProvider(LocalContentColor provides visuals.contentColor) {
                Text(text = text, style = textStyle, color = visuals.contentColor)
            }
        }
    }
}

@Composable
internal fun PillSurfaceButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(Shapes.Full),
    defaultVisuals: ButtonVisuals,
    pressedVisuals: ButtonVisuals,
    disabledVisuals: ButtonVisuals,
    focusedVisuals: ButtonVisuals,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val visuals = resolveButtonVisuals(
        enabled = enabled,
        interactionSource = interactionSource,
        default = defaultVisuals,
        pressed = pressedVisuals,
        disabled = disabledVisuals,
        focused = focusedVisuals,
    )

    Surface(
        modifier = modifier
            .semantics { role = Role.Button }
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        shape = shape,
        color = visuals.containerColor,
        contentColor = visuals.contentColor,
        border = if (visuals.borderWidth > 0.dp) {
            BorderStroke(visuals.borderWidth, visuals.borderColor)
        } else {
            null
        },
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        CompositionLocalProvider(LocalContentColor provides visuals.contentColor) {
            content()
        }
    }
}
