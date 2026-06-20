package com.app.designsystem.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

/**
 * Standard form text field.
 *
 * Meta source: `text-input` / `text-input-focused` / `text-input-error`.
 *
 * Visual states: default, pressed, disabled, focused, error.
 */
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isError = errorMessage != null
    val visuals = resolveFieldVisuals(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        default = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.Hairline,
            borderWidth = 1.dp,
        ),
        pressed = FieldVisuals(
            backgroundColor = Colors.SurfaceSoft,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.Hairline,
            borderWidth = 1.dp,
        ),
        disabled = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.Stone,
            placeholderColor = Colors.DisabledText,
            borderColor = Colors.Hairline,
            borderWidth = 1.dp,
        ),
        focused = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.FbBlue,
            borderWidth = 2.dp,
        ),
        error = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.Ink,
            placeholderColor = Colors.Steel,
            borderColor = Colors.CriticalStrong,
            borderWidth = 1.dp,
        ),
    )

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = Theme.typography.bodySm,
                color = if (enabled) Colors.InkDeep else Colors.Stone,
                modifier = Modifier.padding(bottom = Spacing.Xxs),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 44.dp)
                .height(44.dp)
                .background(visuals.backgroundColor, RoundedCornerShape(Shapes.Lg))
                .border(visuals.borderWidth, visuals.borderColor, RoundedCornerShape(Shapes.Lg))
                .padding(horizontal = Spacing.Md),
            contentAlignment = Alignment.CenterStart,
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = Theme.typography.bodyMd.copy(color = visuals.textColor),
                singleLine = singleLine,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                cursorBrush = SolidColor(Colors.FbBlue),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(
                                text = placeholder,
                                style = Theme.typography.bodyMd,
                                color = visuals.placeholderColor,
                            )
                        }
                        innerTextField()
                    }
                },
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = Theme.typography.bodySm,
                color = Colors.CriticalStrong,
                modifier = Modifier.padding(top = Spacing.Xxs),
            )
        }
    }
}

@Preview(name = "TextField — default")
@Composable
private fun TextFieldDefaultPreview() {
    Theme {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = "Email address",
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "TextField — filled")
@Composable
private fun TextFieldFilledPreview() {
    Theme {
        TextField(
            value = "hello@meta.com",
            onValueChange = {},
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "TextField — error")
@Composable
private fun TextFieldErrorPreview() {
    Theme {
        TextField(
            value = "invalid",
            onValueChange = {},
            errorMessage = "Enter a valid email address",
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "TextField — disabled")
@Composable
private fun TextFieldDisabledPreview() {
    Theme {
        TextField(
            value = "hello@meta.com",
            onValueChange = {},
            enabled = false,
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}
