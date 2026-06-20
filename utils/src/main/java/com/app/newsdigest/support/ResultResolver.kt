package com.app.newsdigest.support

import com.app.newsdigest.log.Logger

class ResultResolver {
    suspend fun <T> resolve(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: Exception) {
            val message = """
                    Resolve Failed:
                    Happen in ${block.javaClass.enclosingClass?.name ?: "AnonymousClass"}
                    Method: ${block.javaClass.enclosingMethod?.name ?: "AnonymousMethod"}
                    Message: $e
                """.trimIndent()

            Logger.w("ResultResolver", message, e)

            Result.error(e)
        }
    }
}
