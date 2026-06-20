package com.app.designsystem.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.designsystem.component.input.RadioOption
import com.app.designsystem.component.input.SearchPill
import com.app.designsystem.component.input.TextField
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun InputsScreen() {
    var text by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Toggle error state",
                    style = Theme.typography.bodySm,
                    color = Colors.Charcoal,
                )
                Switch(checked = showError, onCheckedChange = { showError = it })
            }
        }
        item {
            CatalogSample(label = "TextField · text-input") {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = "Email",
                    placeholder = "you@example.com",
                    errorMessage = if (showError) "Enter a valid email address" else null,
                )
            }
        }
        item {
            CatalogSample(label = "SearchPill · search-pill") {
                SearchPill(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = "Search headlines",
                )
            }
        }
        item {
            CatalogSample(label = "RadioOption · radio-option") {
                Column {
                    RadioOption(
                        label = "Top headlines",
                        selected = selectedOption == 0,
                        onClick = { selectedOption = 0 },
                    )
                    RadioOption(
                        label = "Technology",
                        selected = selectedOption == 1,
                        onClick = { selectedOption = 1 },
                    )
                    RadioOption(
                        label = "Business",
                        selected = selectedOption == 2,
                        onClick = { selectedOption = 2 },
                    )
                }
            }
        }
    }
}
