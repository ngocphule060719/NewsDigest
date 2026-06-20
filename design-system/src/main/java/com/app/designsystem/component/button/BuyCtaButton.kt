package com.app.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Commerce buy-now CTA — cobalt `{colors.primary}` only; never used on marketing surfaces.
 *
 * Meta source: `button-buy-cta` / `button-buy-cta-pressed`.
 */
@Composable
fun BuyCtaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    PillTextButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 14.dp),
        textStyle = Theme.typography.buttonMd,
        defaultVisuals = ButtonVisuals(
            containerColor = Colors.Primary,
            contentColor = Colors.OnPrimary,
        ),
        pressedVisuals = ButtonVisuals(
            containerColor = Colors.PrimaryDeep,
            contentColor = Colors.OnPrimary,
        ),
        disabledVisuals = ButtonVisuals(
            containerColor = Colors.DisabledText,
            contentColor = Colors.Canvas,
        ),
        focusedVisuals = ButtonVisuals(
            containerColor = Colors.Primary,
            contentColor = Colors.OnPrimary,
            borderColor = Colors.FbBlue,
            borderWidth = 2.dp,
        ),
    )
}

@Preview(name = "BuyCtaButton — default")
@Composable
private fun BuyCtaButtonDefaultPreview() {
    Theme {
        BuyCtaButton(text = "Add to cart", onClick = {})
    }
}

@Preview(name = "BuyCtaButton — disabled")
@Composable
private fun BuyCtaButtonDisabledPreview() {
    Theme {
        BuyCtaButton(text = "Add to cart", onClick = {}, enabled = false)
    }
}
