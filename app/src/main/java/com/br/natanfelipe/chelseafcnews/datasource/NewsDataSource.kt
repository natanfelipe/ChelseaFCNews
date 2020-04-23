package com.br.natanfelipe.chelseafcnews.datasource

import androidx.paging.PageKeyedDataSource
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

class NewsDataSource : PageKeyedDataSource<Int, Articles>(),
    KoinComponent, CoroutineScope {

    val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val repository: NewsRepository by inject()
    private var initialPage = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Articles>
    ) {
        launch(Dispatchers.IO) {
            getNews(initialPage, 2, callback, null)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        val page = params.key
        launch(Dispatchers.IO) {
            getNews(page, page + 1, null, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {}

    suspend fun getNews(
        requestedPage: Int,
        nextPage: Int,
        initialCallback: LoadInitialCallback<Int, Articles>?,
        callback: LoadCallback<Int, Articles>?
    ) {
        val response = repository.getAllNews(requestedPage)
        when {
            response.isSuccessful -> {
                response.body()?.let {
                    val articlesList = it.articles
                    initialCallback?.onResult(articlesList, null, nextPage)
                    callback?.onResult(articlesList, nextPage)
                }
            }
        }
    }
}