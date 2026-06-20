package com.app.newsdigest.presentation.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import com.app.newsdigest.presentation.detail.OpenUrlPayload
import com.app.newsdigest.presentation.detail.ShareArticlePayload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun launchShareArticle(
    context: Context,
    payload: ShareArticlePayload,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, payload.title)
        putExtra(Intent.EXTRA_TEXT, payload.url)
    }
    try {
        context.startActivity(Intent.createChooser(intent, null))
    } catch (_: ActivityNotFoundException) {
        scope.launch {
            snackbarHostState.showSnackbar("No app available to share this article")
        }
    }
}

fun launchOpenInBrowser(
    context: Context,
    payload: OpenUrlPayload,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(payload.url))
    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        scope.launch {
            snackbarHostState.showSnackbar("No browser available to open this link")
        }
    }
}
