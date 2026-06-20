package com.app.newsdigest.support

class Once<out T>(
    private var value: T?
) {
    fun get(): T? {
        if (value != null) {
            val r = value
            value = null

            return r
        }

        return null
    }
}
