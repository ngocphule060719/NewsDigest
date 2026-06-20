@file:Suppress(IMPORTANT)

package com.app.newsdigest.support

import com.app.newsdigest.meta.IMPORTANT

class CriteriaCollector private constructor(
    private var initial: Boolean
) {
    companion object {
        fun byAnd(): CriteriaCollector {
            return CriteriaCollector(true)
        }

        fun byOr(): CriteriaCollector {
            return CriteriaCollector(false)
        }
    }

    fun and(criteria: Boolean) = apply {
        initial = initial && criteria
    }

    fun or(criteria: Boolean) = apply {
        initial = initial || criteria
    }

    fun getCondition(): Boolean {
        return initial
    }
}
