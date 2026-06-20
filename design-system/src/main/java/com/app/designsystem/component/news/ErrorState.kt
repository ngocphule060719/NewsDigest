package com.app.designsystem.component.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.component.button.PrimaryButton
import com.app.designsystem.component.button.SecondaryButton
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

/**
 * Full-screen or inline error presentation with optional retry action.
 *
 * Uses `critical` / `criticalStrong` accent and `body-md` message typography.
 *
 * @param message User-facing error description.
 * @param onRetry Optional retry callback; when null, no retry button is shown.
 * @param retryLabel Label for the primary retry button.
 * @param useSecondaryRetry When true and [onRetry] is set, uses `button-secondary` styling.
 */
@Composable
fun ErrorState(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    retryLabel: String = "Try again",
    useSecondaryRetry: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.Xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        Text(
            text = "Something went wrong",
            style = Theme.typography.subtitleLg,
            color = Colors.CriticalStrong,
            textAlign = TextAlign.Center,
        )

        Text(
            text = message,
            style = Theme.typography.bodyMd,
            color = Colors.Ink,
            textAlign = TextAlign.Center,
        )

        if (onRetry != null) {
            if (useSecondaryRetry) {
                SecondaryButton(
                    text = retryLabel,
                    onClick = onRetry,
                )
            } else {
                PrimaryButton(
                    text = retryLabel,
                    onClick = onRetry,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorStateWithRetryPreview() {
    Theme {
        ErrorState(
            message = "We couldn't load the latest headlines. Check your connection and try again.",
            onRetry = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorStateNoRetryPreview() {
    Theme {
        ErrorState(
            message = "This article is no longer available.",
            modifier = Modifier.fillMaxSize(),
        )
    }
}
