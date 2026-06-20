package com.app.newsdigest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.newsdigest.support.Once
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> CollectOnce(
    onceFlow: StateFlow<Once<T>>,
    onEvent: (T) -> Unit,
) {
    val onceHolder by onceFlow.collectAsStateWithLifecycle()
    LaunchedEffect(onceHolder) {
        onceHolder.get()?.let(onEvent)
    }
}
