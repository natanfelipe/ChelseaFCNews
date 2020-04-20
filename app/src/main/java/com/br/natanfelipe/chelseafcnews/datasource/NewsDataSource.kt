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
    private var totalPages = 0
        get() {
            if (field == 0) return field
            return field / 20
        }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Articles>
    ) {
        launch(Dispatchers.IO) {
            val response = repository.getAllNews(initialPage)

            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        val articlesList = it.articles
                        callback.onResult(articlesList, null, initialPage++)
                    }
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        if(params.key <= initialPage) {
            launch(Dispatchers.IO) {
                val response = repository.getAllNews(initialPage)
                when {
                    response.isSuccessful -> {
                        response.body()?.let {
                            val articlesList = it.articles
                            callback.onResult(articlesList,params.key+1)
                        }
                    }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {}

    fun closeScope(){
        job.cancel()
    }
}