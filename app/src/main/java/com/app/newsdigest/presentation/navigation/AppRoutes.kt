package com.app.newsdigest.presentation.navigation

object AppRoutes {
    const val ARTICLE_LIST = "article_list"
    const val ARTICLE_DETAIL = "article_detail/{articleId}"
    const val BOOKMARKS = "bookmarks"
    const val ARG_ARTICLE_ID = "articleId"
    fun articleDetail(articleId: String): String = "article_detail/$articleId"
}
