package com.br.natanfelipe.chelseafcnews.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSourceFactory
import com.br.natanfelipe.chelseafcnews.model.Articles

class HomeViewModel(private val newsDataSourceFactory: NewsDataSourceFactory): ViewModel() {
    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(true)
        .build()

    private val pagedList = LivePagedListBuilder(newsDataSourceFactory, config)
        .build()

    private val mutableErrorMessageVisibility = MutableLiveData<Int>().apply { View.GONE }
    val errorMessageVisibility: LiveData<Int>
    get() = mutableErrorMessageVisibility

    private val mutableErrorMessageText = MutableLiveData<Int>().apply { R.string.generic_error }
    val errorMessageText: LiveData<Int>
    get() = mutableErrorMessageText

    val articlesDataSource = newsDataSourceFactory.articleDataSource

    init {
        mutableErrorMessageText.value = R.string.generic_error
        mutableErrorMessageVisibility.value = View.GONE
    }

    fun refresh() {
        articlesDataSource.invalidate()
        mutableErrorMessageVisibility.value = View.GONE
    }

    fun loadData(): LiveData<PagedList<Articles>> = pagedList

    fun displayError(isConnected: Boolean) {
        mutableErrorMessageVisibility.value = View.VISIBLE
        mutableErrorMessageText.value = if (isConnected) R.string.generic_error else R.string.internet_error
    }

}