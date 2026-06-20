package com.app.newsdigest.presentation.common

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.newsdigest.support.Once
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CollectOnceSnackbar(
    flowSnackbarMessage: StateFlow<Once<String>>,
    snackbarHostState: SnackbarHostState,
) {
    val snackbarEvent by flowSnackbarMessage.collectAsStateWithLifecycle()
    val message = snackbarEvent.get()

    LaunchedEffect(snackbarEvent) {
        message?.let { snackbarHostState.showSnackbar(it) }
    }
}

@Composable
fun AppSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    )
}
