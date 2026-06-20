package com.app.newsdigest.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun singleThemeRoot() {
        var capturedPrimary = Colors.Canvas

        composeTestRule.setContent {
            Theme {
                capturedPrimary = MaterialTheme.colorScheme.primary
                Box(
                    modifier = Modifier
                        .testTag("theme_root")
                        .background(MaterialTheme.colorScheme.background),
                )
            }
        }

        assertEquals(Colors.Primary, capturedPrimary)
        composeTestRule.onNodeWithTag("theme_root").assertIsDisplayed()
    }
}
