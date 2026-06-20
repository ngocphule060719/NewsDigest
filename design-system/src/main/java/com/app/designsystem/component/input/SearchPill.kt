package com.app.designsystem.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

/**
 * Top-nav search field pill.
 *
 * Meta source: `search-pill`.
 *
 * Visual states: default, pressed, disabled, focused, error.
 */
@Composable
fun SearchPill(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val visuals = resolveFieldVisuals(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        default = FieldVisuals(
            backgroundColor = Colors.SurfaceSoft,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.SurfaceSoft,
            borderWidth = 0.dp,
        ),
        pressed = FieldVisuals(
            backgroundColor = Colors.HairlineSoft,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.HairlineSoft,
            borderWidth = 0.dp,
        ),
        disabled = FieldVisuals(
            backgroundColor = Colors.SurfaceSoft,
            textColor = Colors.Stone,
            placeholderColor = Colors.DisabledText,
            borderColor = Colors.SurfaceSoft,
            borderWidth = 0.dp,
        ),
        focused = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.FbBlue,
            borderWidth = 2.dp,
        ),
        error = FieldVisuals(
            backgroundColor = Colors.SurfaceSoft,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.CriticalStrong,
            borderWidth = 1.dp,
        ),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 40.dp)
            .height(40.dp)
            .background(visuals.backgroundColor, RoundedCornerShape(Shapes.Full))
            .border(visuals.borderWidth, visuals.borderColor, RoundedCornerShape(Shapes.Full))
            .padding(horizontal = Spacing.Lg, vertical = Spacing.Md),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            textStyle = Theme.typography.bodySm.copy(color = visuals.textColor),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(Colors.FbBlue),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = Theme.typography.bodySm,
                            color = visuals.placeholderColor,
                        )
                    }
                    innerTextField()
                }
            },
        )
    }
}

@Preview(name = "SearchPill — default")
@Composable
private fun SearchPillDefaultPreview() {
    Theme {
        SearchPill(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "SearchPill — filled")
@Composable
private fun SearchPillFilledPreview() {
    Theme {
        SearchPill(
            value = "Ray-Ban Meta",
            onValueChange = {},
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "SearchPill — focused")
@Composable
private fun SearchPillFocusedPreview() {
    Theme {
        SearchPill(
            value = "Quest",
            onValueChange = {},
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "SearchPill — disabled")
@Composable
private fun SearchPillDisabledPreview() {
    Theme {
        SearchPill(
            value = "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "SearchPill — error")
@Composable
private fun SearchPillErrorPreview() {
    Theme {
        SearchPill(
            value = "?",
            onValueChange = {},
            isError = true,
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}
