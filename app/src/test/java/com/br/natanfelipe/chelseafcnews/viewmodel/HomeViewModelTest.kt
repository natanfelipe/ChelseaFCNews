package com.br.natanfelipe.chelseafcnews.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.br.natanfelipe.chelseafcnews.ArticleResponseSuccessMock
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSourceFactory
import com.br.natanfelipe.chelseafcnews.di.appModule
import com.br.natanfelipe.chelseafcnews.di.networkModule
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.model.ArticlesList
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import retrofit2.Response

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest: KoinTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val repository: NewsRepository = mockk()

    val loadInitialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Articles> = mockk()

    val newsDataSourceFactory = NewsDataSourceFactory(repository)

    val homeViewModel = HomeViewModel(newsDataSourceFactory)

    private val dispatcher = TestCoroutineDispatcher()

    val articlesList: LiveData<PagedList<Articles>> = mockk()

    private val successResponse = ArticleResponseSuccessMock()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin { modules(listOf(networkModule, appModule)) }
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `get news from service`() {

        dispatcher.runBlockingTest {
            coEvery{ newsDataSourceFactory.newsDataSource().getNews(1, 2, loadInitialCallback, null) } coAnswers {
                articlesList
            }
        }

        val articles = homeViewModel.articles
        articles.observeForever {  }
        assertEquals(1, articles.value?.size)
        /*val result = homeViewModel.articles
        result.observeForever {}
        assertEquals(1, result.value?.size)*/
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}