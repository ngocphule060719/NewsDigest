package com.app.newsdigest.presentation.common

fun resolveRefreshErrorMessage(isConnected: Boolean): String {
    return if (isConnected) {
        AppUiStrings.SERVER_ERROR_BODY
    } else {
        AppUiStrings.OFFLINE_ERROR_BODY
    }
}
