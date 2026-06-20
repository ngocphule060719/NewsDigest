package com.app.newsdigest.presentation.articlelist

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleListViewModelTest {

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

    private fun createViewModel(
        newsRepository: FakeNewsRepository = FakeNewsRepository(),
        bookmarkRepository: FakeBookmarkRepository = FakeBookmarkRepository(),
        connectivityMonitor: FakeConnectivityMonitor = FakeConnectivityMonitor(),
    ): ArticleListViewModel = ArticleListViewModel(
        newsRepository = newsRepository,
        bookmarkRepository = bookmarkRepository,
        connectivityMonitor = connectivityMonitor,
    )

    @Test
    fun loadArticles_disconnectedWithCache_setsOfflineStale() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(sampleArticle("cached-1")))
        val connectivityMonitor = FakeConnectivityMonitor(initialConnected = false)

        val viewModel = createViewModel(
            newsRepository = newsRepository,
            connectivityMonitor = connectivityMonitor,
        )

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.articles.isNotEmpty() }
            assertTrue(state.isOfflineStale)
            assertTrue(state.useHeroLayout)
            assertNull(state.error)
        }
    }

    @Test
    fun loadArticles_emptyAfterSuccessfulRefresh_setsIsEmptyCategory() = runTest {
        val newsRepository = FakeNewsRepository()
        val viewModel = createViewModel(newsRepository = newsRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.isEmptyCategory }
            assertTrue(state.isEmptyCategory)
            assertTrue(state.articles.isEmpty())
            assertNull(state.error)
        }
    }

    @Test
    fun toggleBookmark_addFails_keepsBookmarkFalse() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(sampleArticle("bookmark-fail")))
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.setAddShouldFail(true)

        val viewModel = createViewModel(
            newsRepository = newsRepository,
            bookmarkRepository = bookmarkRepository,
        )
        awaitInitialLoad(viewModel)

        viewModel.onIntent(ArticleListIntent.ToggleBookmark("bookmark-fail"))

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading }
            assertFalse(state.articles.first { it.id == "bookmark-fail" }.isBookmarked)
        }
        assertEquals(AppUiStrings.BOOKMARK_FAILURE, viewModel.flowSnackbarMessage.value.get())
    }

    @Test
    fun toggleBookmark_removeFails_keepsBookmarkTrue() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(sampleArticle("bookmark-keep")))
        val bookmarkRepository = FakeBookmarkRepository()
        bookmarkRepository.addBookmark(sampleArticle("bookmark-keep"))
        bookmarkRepository.setRemoveShouldFail(true)

        val viewModel = createViewModel(
            newsRepository = newsRepository,
            bookmarkRepository = bookmarkRepository,
        )
        awaitInitialLoad(viewModel)

        viewModel.onIntent(ArticleListIntent.ToggleBookmark("bookmark-keep"))

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading }
            assertTrue(state.articles.first { it.id == "bookmark-keep" }.isBookmarked)
        }
        assertEquals(AppUiStrings.BOOKMARK_FAILURE, viewModel.flowSnackbarMessage.value.get())
    }

    @Test
    fun selectCategory_whileRefreshing_usesLastSelectedCategory() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(
            listOf(
                sampleArticle("general-1", Category.GENERAL),
                sampleArticle("tech-1", Category.TECHNOLOGY),
            ),
        )
        val viewModel = createViewModel(newsRepository = newsRepository)

        awaitInitialLoad(viewModel)
        newsRepository.setRefreshDelayMs(200)

        viewModel.onIntent(ArticleListIntent.Refresh)
        viewModel.onIntent(ArticleListIntent.SelectCategory(Category.TECHNOLOGY))

        viewModel.uiState.test {
            val state = awaitUntil {
                !it.isRefreshing &&
                        it.selectedCategory == Category.TECHNOLOGY &&
                        it.articles.all { article -> article.id == "tech-1" }
            }
            assertEquals(Category.TECHNOLOGY, state.selectedCategory)
            assertEquals(1, state.articles.size)
        }
    }

    @Test
    fun loadArticles_success_isLoadingFalseAndArticlesNonEmpty() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(
            listOf(
                sampleArticle("1"),
                sampleArticle("2"),
            ),
        )

        val viewModel = createViewModel(newsRepository = newsRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.articles.isNotEmpty() }
            assertFalse(state.isLoading)
            assertTrue(state.articles.isNotEmpty())
        }
    }

    @Test
    fun loadArticles_refreshFailsWithEmptyCache_showsError() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.setRefreshShouldFail(true)

        val viewModel = createViewModel(newsRepository = newsRepository)

        viewModel.uiState.test {
            val state = awaitUntil { !it.isLoading && it.error != null }
            assertNotNull(state.error)
            assertTrue(state.articles.isEmpty())
        }
    }

    @Test
    fun refresh_setsRefreshingTrueThenFalse() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(sampleArticle("1")))
        val viewModel = createViewModel(newsRepository = newsRepository)

        awaitInitialLoad(viewModel)
        newsRepository.setRefreshDelayMs(200)

        viewModel.onIntent(ArticleListIntent.Refresh)

        viewModel.uiState.test {
            val refreshing = awaitUntil { it.isRefreshing }
            assertTrue(refreshing.isRefreshing)

            val done = awaitUntil { !it.isRefreshing }
            assertFalse(done.isRefreshing)
        }
    }

    @Test
    fun selectCategory_doesNotSetFullScreenLoading() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(
            listOf(
                sampleArticle("general-1", Category.GENERAL),
                sampleArticle("tech-1", Category.TECHNOLOGY),
            ),
        )
        val viewModel = createViewModel(newsRepository = newsRepository)

        awaitInitialLoad(viewModel)

        viewModel.onIntent(ArticleListIntent.SelectCategory(Category.TECHNOLOGY))

        viewModel.uiState.test {
            val state = awaitUntil {
                it.selectedCategory == Category.TECHNOLOGY && it.articles.isNotEmpty()
            }
            assertFalse(state.isLoading)
            assertEquals(Category.TECHNOLOGY, state.categoryTabs.first { it.isSelected }.category)
        }
    }

    @Test
    fun selectCategory_updatesSelectedCategoryAndFiltersArticles() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(
            listOf(
                sampleArticle("general-1", Category.GENERAL),
                sampleArticle("tech-1", Category.TECHNOLOGY),
            ),
        )
        val viewModel = createViewModel(newsRepository = newsRepository)

        awaitInitialLoad(viewModel)

        viewModel.onIntent(ArticleListIntent.SelectCategory(Category.TECHNOLOGY))

        viewModel.uiState.test {
            val state = awaitUntil {
                it.selectedCategory == Category.TECHNOLOGY &&
                        it.articles.isNotEmpty() &&
                        it.articles.all { article -> article.id == "tech-1" }
            }
            assertEquals(Category.TECHNOLOGY, state.selectedCategory)
            assertEquals(1, state.articles.size)
            assertEquals("tech-1", state.articles.first().id)
        }
    }

    @Test
    fun toggleBookmark_addAndRemove_flipsBookmarkState() = runTest {
        val newsRepository = FakeNewsRepository()
        newsRepository.seed(listOf(sampleArticle("bookmark-1")))
        val viewModel = createViewModel(newsRepository = newsRepository)

        awaitInitialLoad(viewModel)

        viewModel.onIntent(ArticleListIntent.ToggleBookmark("bookmark-1"))
        viewModel.uiState.test {
            val bookmarked = awaitUntil {
                it.articles.find { article -> article.id == "bookmark-1" }?.isBookmarked == true
            }
            assertTrue(bookmarked.articles.first { it.id == "bookmark-1" }.isBookmarked)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onIntent(ArticleListIntent.ToggleBookmark("bookmark-1"))
        viewModel.uiState.test {
            val unbookmarked = awaitUntil {
                it.articles.find { article -> article.id == "bookmark-1" }?.isBookmarked == false
            }
            assertFalse(unbookmarked.articles.first { it.id == "bookmark-1" }.isBookmarked)
        }
    }

    private suspend fun awaitInitialLoad(viewModel: ArticleListViewModel) {
        viewModel.uiState.test {
            awaitUntil { it.articles.isNotEmpty() }
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
