package com.app.newsdigest.presentation.bookmarks

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.presentation.articlelist.FakeBookmarkRepository
import com.app.newsdigest.presentation.articlelist.FakeNewsRepository
import com.app.newsdigest.presentation.common.AppUiStrings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarksViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        AppDispatchers.initialize()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun observeBookmarks_emitsSortedList() = runTest {
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.addBookmark(sampleArticle("older"))
        Thread.sleep(5)
        bookmarkRepository.addBookmark(sampleArticle("newer"))

        val viewModel = BookmarksViewModel(bookmarkRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.bookmarks.size == 2 }
            assertEquals("newer", state.bookmarks.first().articleId)
            assertEquals("older", state.bookmarks.last().articleId)
        }
    }

    @Test
    fun emptyBookmarks_showsEmptyState() = runTest {
        val viewModel = BookmarksViewModel(FakeBookmarkRepository())

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading }
            assertFalse(state.isLoading)
            assertTrue(state.bookmarks.isEmpty())
        }
    }

    @Test
    fun removeBookmark_success_updatesList() = runTest {
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.addBookmark(sampleArticle("remove-me"))
        bookmarkRepository.addBookmark(sampleArticle("keep-me"))

        val viewModel = BookmarksViewModel(bookmarkRepository)

        awaitInitialLoad(viewModel)

        viewModel.onIntent(BookmarksIntent.ToggleBookmark("remove-me"))

        viewModel.uiState.test {
            val state = awaitUntil {
                it.bookmarks.size == 1 && it.bookmarks.none { bookmark -> bookmark.articleId == "remove-me" }
            }
            assertEquals(1, state.bookmarks.size)
            assertEquals("keep-me", state.bookmarks.first().articleId)
        }
    }

    @Test
    fun removeBookmark_failure_keepsItemInList() = runTest {
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.addBookmark(sampleArticle("stay"))
        bookmarkRepository.setRemoveShouldFail(true)

        val viewModel = BookmarksViewModel(bookmarkRepository)
        awaitInitialLoad(viewModel)

        viewModel.onIntent(BookmarksIntent.ToggleBookmark("stay"))

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading }
            assertEquals(1, state.bookmarks.size)
            assertEquals("stay", state.bookmarks.first().articleId)
        }
        assertEquals(AppUiStrings.BOOKMARK_FAILURE, viewModel.flowSnackbarMessage.value.get())
    }

    @Test
    fun load_doesNotCallNewsRepository() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(sampleArticle("should-not-load")))

        val viewModel = BookmarksViewModel(FakeBookmarkRepository())

        viewModel.uiState.test {
            awaitUntil { !it.isLoading }
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(0, newsRepository.refreshTopHeadlinesCallCount)
        assertEquals(0, newsRepository.getArticleCallCount)
    }

    private suspend fun awaitInitialLoad(viewModel: BookmarksViewModel) {
        viewModel.uiState.test {
            awaitUntil { !it.isLoading && it.bookmarks.isNotEmpty() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    private suspend fun <T> ReceiveTurbine<T>.awaitUntil(predicate: (T) -> Boolean): T {
        var item = awaitItem()
        while (!predicate(item)) {
            item = awaitItem()
        }
        return item
    }

    private fun sampleArticle(
        id: String,
        category: Category = Category.GENERAL,
    ) = Article(
        id = id,
        title = "Title $id",
        description = "Description $id",
        content = "Content $id",
        url = "https://example.com/$id",
        imageUrl = "https://example.com/$id.jpg",
        sourceName = "Source",
        publishedAt = Instant.parse("2026-01-01T00:00:00Z"),
        category = category,
    )
}
