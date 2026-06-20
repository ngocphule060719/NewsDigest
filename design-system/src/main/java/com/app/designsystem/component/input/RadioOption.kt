package com.app.designsystem.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

/**
 * Configurator option card with radio selection affordance.
 *
 * Meta source: `radio-option` / `radio-option-selected`.
 *
 * Visual states: unselected, selected, pressed, disabled, focused, error.
 */
@Composable
fun RadioOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val selectionVisuals = if (selected) {
        FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.InkDeep,
            placeholderColor = Colors.Steel,
            borderColor = Colors.RadioSelectedBorder,
            borderWidth = 2.dp,
        )
    } else {
        FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.InkDeep,
            placeholderColor = Colors.Steel,
            borderColor = Colors.InkDeep12,
            borderWidth = 1.dp,
        )
    }

    val visuals = resolveFieldVisuals(
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        default = selectionVisuals,
        pressed = selectionVisuals.copy(backgroundColor = Colors.SurfaceSoft),
        disabled = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.Stone,
            placeholderColor = Colors.DisabledText,
            borderColor = Colors.Hairline,
            borderWidth = 1.dp,
        ),
        focused = if (selected) {
            selectionVisuals
        } else {
            selectionVisuals.copy(
                borderColor = Colors.FbBlue,
                borderWidth = 2.dp,
            )
        },
        error = FieldVisuals(
            backgroundColor = Colors.Canvas,
            textColor = Colors.InkDeep,
            placeholderColor = Colors.Steel,
            borderColor = Colors.CriticalStrong,
            borderWidth = 1.dp,
        ),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 44.dp)
            .semantics {
                role = Role.RadioButton
                this.selected = selected
            }
            .clip(RoundedCornerShape(Shapes.Lg))
            .background(visuals.backgroundColor)
            .border(visuals.borderWidth, visuals.borderColor, RoundedCornerShape(Shapes.Lg))
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(Spacing.Lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioIndicator(selected = selected, enabled = enabled)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = Spacing.Md),
        ) {
            Text(
                text = label,
                style = Theme.typography.bodyMdBold,
                color = visuals.textColor,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = Theme.typography.bodySm,
                    color = if (enabled) Colors.Steel else Colors.DisabledText,
                    modifier = Modifier.padding(top = Spacing.Xxs),
                )
            }
        }
    }
}

@Composable
private fun RadioIndicator(
    selected: Boolean,
    enabled: Boolean,
) {
    val ringColor = when {
        !enabled -> Colors.Hairline
        selected -> Colors.RadioSelectedBorder
        else -> Colors.Hairline
    }
    val fillColor = when {
        !enabled -> Colors.DisabledText
        selected -> Colors.RadioSelectedBorder
        else -> Colors.Canvas
    }

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(2.dp, ringColor, CircleShape)
            .padding(4.dp)
            .clip(CircleShape)
            .background(fillColor),
    )
}

@Preview(name = "RadioOption — unselected")
@Composable
private fun RadioOptionUnselectedPreview() {
    Theme {
        RadioOption(
            label = "128 GB",
            subtitle = "Enough for most apps and games",
            selected = false,
            onClick = {},
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "RadioOption — selected")
@Composable
private fun RadioOptionSelectedPreview() {
    Theme {
        RadioOption(
            label = "256 GB",
            subtitle = "More room for mixed reality media",
            selected = true,
            onClick = {},
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "RadioOption — disabled")
@Composable
private fun RadioOptionDisabledPreview() {
    Theme {
        RadioOption(
            label = "512 GB",
            subtitle = "Unavailable in your region",
            selected = false,
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}

@Preview(name = "RadioOption — error")
@Composable
private fun RadioOptionErrorPreview() {
    Theme {
        RadioOption(
            label = "Select a storage option",
            selected = false,
            onClick = {},
            isError = true,
            modifier = Modifier.padding(Spacing.Base),
        )
    }
}
