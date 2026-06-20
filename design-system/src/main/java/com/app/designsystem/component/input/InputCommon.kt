package com.app.designsystem.component.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

internal data class FieldVisuals(
    val backgroundColor: Color,
    val textColor: Color,
    val placeholderColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
)

@Composable
internal fun resolveFieldVisuals(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: MutableInteractionSource,
    default: FieldVisuals,
    pressed: FieldVisuals,
    disabled: FieldVisuals,
    focused: FieldVisuals,
    error: FieldVisuals,
): FieldVisuals {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    return when {
        !enabled -> disabled
        isError -> error
        isPressed -> pressed
        isFocused -> focused
        else -> default
    }
}
