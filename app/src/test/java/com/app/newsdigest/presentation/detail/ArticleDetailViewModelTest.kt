package com.app.newsdigest.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.presentation.articlelist.FakeBookmarkRepository
import com.app.newsdigest.presentation.articlelist.FakeNewsRepository
import com.app.newsdigest.presentation.common.AppUiStrings
import com.app.newsdigest.presentation.navigation.AppRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleDetailViewModelTest {

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
    fun loadArticle_success_populatesFields() = runTest {
        val article = sampleArticle("detail-1")
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(article))

        val viewModel = createViewModel(articleId = "detail-1", newsRepository = newsRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.title.isNotBlank() }
            assertEquals("Title detail-1", state.title)
            assertEquals("Content detail-1", state.content)
            assertEquals("https://example.com/detail-1.jpg", state.imageUrl)
            assertNull(state.error)
        }
    }

    @Test
    fun loadArticle_notFound_showsError() = runTest {
        val newsRepository = FakeNewsRepository()

        val viewModel = createViewModel(articleId = "missing", newsRepository = newsRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.error != null }
            assertNotNull(state.error)
            assertTrue(state.title.isBlank())
        }
    }

    @Test
    fun loadArticle_fromCache_whenNetworkFails() = runTest {
        val article = sampleArticle("cached-detail")
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(article))
        newsRepository.setRefreshShouldFail(true)

        val viewModel =
            createViewModel(articleId = "cached-detail", newsRepository = newsRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.title.isNotBlank() }
            assertEquals("Title cached-detail", state.title)
            assertEquals(1, newsRepository.getArticleCallCount)
        }
    }

    @Test
    fun toggleBookmark_add_success() = runTest {
        val article = sampleArticle("bookmark-add")
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(article))

        val viewModel = createViewModel(articleId = "bookmark-add", newsRepository = newsRepository)
        awaitArticleLoaded(viewModel)

        viewModel.onIntent(ArticleDetailIntent.ToggleBookmark)

        viewModel.uiState.test {
            val state = awaitUntil { it.isBookmarked }
            assertTrue(state.isBookmarked)
        }
    }

    @Test
    fun toggleBookmark_remove_success() = runTest {
        val article = sampleArticle("bookmark-remove")
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(article))
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.addBookmark(article)

        val viewModel = createViewModel(
            articleId = "bookmark-remove",
            newsRepository = newsRepository,
            bookmarkRepository = bookmarkRepository,
        )
        awaitArticleLoaded(viewModel)

        viewModel.onIntent(ArticleDetailIntent.ToggleBookmark)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isBookmarked }
            assertFalse(state.isBookmarked)
        }
    }

    @Test
    fun toggleBookmark_failure_keepsBookmarkState() = runTest {
        val article = sampleArticle("bookmark-fail")
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(article))
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.setAddShouldFail(true)

        val viewModel = createViewModel(
            articleId = "bookmark-fail",
            newsRepository = newsRepository,
            bookmarkRepository = bookmarkRepository,
        )
        awaitArticleLoaded(viewModel)

        viewModel.onIntent(ArticleDetailIntent.ToggleBookmark)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading }
            assertFalse(state.isBookmarked)
        }
        assertEquals(AppUiStrings.BOOKMARK_FAILURE, viewModel.flowSnackbarMessage.value.get())
    }

    @Test
    fun retry_afterError_reloads() = runTest {
        val article = sampleArticle("retry-article")
        val newsRepository = FakeNewsRepository()
        newsRepository.setGetArticleFailRemaining(1)
        newsRepository.seed(listOf(article))

        val viewModel =
            createViewModel(articleId = "retry-article", newsRepository = newsRepository)

        viewModel.uiState.test {
            awaitUntil { !it.isLoading && it.error != null }
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onIntent(ArticleDetailIntent.LoadArticle)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.error == null && it.title.isNotBlank() }
            assertEquals("Title retry-article", state.title)
        }
    }

    @Test
    fun shareIntent_payload() = runTest {
        val article = sampleArticle("share-article")
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(article))

        val viewModel =
            createViewModel(articleId = "share-article", newsRepository = newsRepository)
        awaitArticleLoaded(viewModel)

        viewModel.onIntent(ArticleDetailIntent.ShareArticle)

        val payload = viewModel.shareArticle.value.get()
        assertNotNull(payload)
        assertEquals("Title share-article", payload?.title)
        assertEquals("https://example.com/share-article", payload?.url)
    }

    private fun createViewModel(
        articleId: String,
        newsRepository: FakeNewsRepository = FakeNewsRepository(),
        bookmarkRepository: FakeBookmarkRepository = FakeBookmarkRepository(),
    ): ArticleDetailViewModel {
        val savedStateHandle = SavedStateHandle(mapOf(AppRoutes.ARG_ARTICLE_ID to articleId))
        return ArticleDetailViewModel(savedStateHandle, newsRepository, bookmarkRepository)
    }

    private suspend fun awaitArticleLoaded(viewModel: ArticleDetailViewModel) {
        viewModel.uiState.test {
            awaitUntil { !it.isLoading && it.title.isNotBlank() }
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
