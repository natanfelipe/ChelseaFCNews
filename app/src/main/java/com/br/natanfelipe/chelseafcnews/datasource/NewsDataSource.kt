package com.br.natanfelipe.chelseafcnews.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.br.natanfelipe.chelseafcnews.common.INITIAL_PAGE
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.model.NetworkState
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import com.br.natanfelipe.chelseafcnews.util.EspressoIdlingResources
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsDataSource(private val repository: NewsRepository) : PageKeyedDataSource<Int, Articles>(),
    CoroutineScope {

    var loadState: MutableLiveData<NetworkState> = MutableLiveData()
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Articles>
    ) {
        getNews(INITIAL_PAGE, INITIAL_PAGE + 1, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        val page = params.key
        getNews(page, page + 1, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {}

    private fun getNews(
        requestedPage: Int,
        nextPage: Int,
        initialCallback: LoadInitialCallback<Int, Articles>?,
        callback: LoadCallback<Int, Articles>?
    ) {
        loadState.postValue(NetworkState.LOADING)

        launch {
            val response = repository.getAllNews(requestedPage)
            when {
                response.isSuccessful -> {
                    loadState.postValue(NetworkState.LOADED)
                    response.body()?.let {
                        val articlesList = it.articles
                        initialCallback?.onResult(articlesList, null, nextPage)
                        callback?.onResult(articlesList, nextPage)

                        EspressoIdlingResources.decrement()
                    }
                }
                else -> {
                    loadState.postValue(NetworkState.error(response.message()))
                }
            }
        }
    }
}