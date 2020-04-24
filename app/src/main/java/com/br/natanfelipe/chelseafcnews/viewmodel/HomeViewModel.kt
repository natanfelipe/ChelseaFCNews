package com.br.natanfelipe.chelseafcnews.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSourceFactory
import com.br.natanfelipe.chelseafcnews.model.Articles

class HomeViewModel(private val newsDataSourceFactory: NewsDataSourceFactory): BaseViewModel() {
    var articles: LiveData<PagedList<Articles>>
    val loading = MutableLiveData<Int>().apply { View.VISIBLE }
    val progressVisibility: LiveData<Int>
    get() = loading
    private val showList = MutableLiveData<Int>().apply { View.GONE }
    val listVisibility: LiveData<Int>
    get() = showList
    private val showError = MutableLiveData<Int>().apply { View.GONE }
    val errorMessageVisibility: LiveData<Int>
    get() = showError

    init {
        articles = initPagination()
    }

    fun refresh() {
        loading.value = View.VISIBLE
        showError.value = View.GONE
        showList.value = View.GONE
    }

    fun loadData(): LiveData<PagedList<Articles>> {
        if(articles.value?.size == 0) {
            showError.value = View.VISIBLE
            showList.value = View.GONE
        } else {
            showError.value = View.GONE
            showList.value = View.VISIBLE
        }

        return articles
    }

    private fun initPagination(): LiveData<PagedList<Articles>> {
        val pagedList = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(true)
            .build()

        return LivePagedListBuilder(newsDataSourceFactory,pagedList).build()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel();
    }
}