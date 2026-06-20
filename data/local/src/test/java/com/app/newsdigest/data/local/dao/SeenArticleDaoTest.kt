package com.app.newsdigest.data.local.dao

import com.app.newsdigest.data.local.InMemoryDatabaseHelper
import com.app.newsdigest.data.local.entity.SeenArticleEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class SeenArticleDaoTest {
    private lateinit var seenArticleDao: SeenArticleDao

    @Before
    fun setUp() {
        seenArticleDao = InMemoryDatabaseHelper.create().seenArticleDao()
    }

    @Test
    fun insertAndGetAllArticleIds_returnsInsertedIds() = runTest {
        seenArticleDao.insertAll(
            listOf(
                SeenArticleEntity(articleId = "s1", seenAt = 1_000L),
                SeenArticleEntity(articleId = "s2", seenAt = 2_000L),
            ),
        )

        val ids = seenArticleDao.getAllArticleIds()

        assertEquals(2, ids.size)
        assertEquals(setOf("s1", "s2"), ids.toSet())
    }

    @Test
    fun insertDuplicate_ignoresSecondInsert() = runTest {
        seenArticleDao.insert(SeenArticleEntity(articleId = "s1", seenAt = 1_000L))
        seenArticleDao.insert(SeenArticleEntity(articleId = "s1", seenAt = 2_000L))

        assertEquals(1, seenArticleDao.count())
    }
}
