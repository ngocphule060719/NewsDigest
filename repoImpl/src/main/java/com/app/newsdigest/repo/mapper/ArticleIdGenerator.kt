package com.app.newsdigest.repo.mapper

import java.security.MessageDigest

fun String.toArticleId(): String {
    val digest = MessageDigest.getInstance("SHA-256")
        .digest(trim().toByteArray(Charsets.UTF_8))
    return digest.joinToString("") { "%02x".format(it) }
}
