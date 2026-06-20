package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.component.button.PillTab
import com.app.designsystem.foundation.Spacing
import com.app.newsdigest.domain.model.Category

@Composable
fun ArticleListCategoryTabs(
    tabs: List<CategoryTabUiState>,
    onSelect: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Spacing.Base),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Xs),
    ) {
        items(
            items = tabs,
            key = { it.category },
        ) { tab ->
            PillTab(
                text = tab.label,
                selected = tab.isSelected,
                onClick = { onSelect(tab.category) },
            )
        }
    }
}
