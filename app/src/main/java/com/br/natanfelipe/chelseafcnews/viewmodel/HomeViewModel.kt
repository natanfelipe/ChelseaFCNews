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

class HomeViewModel(newsDataSourceFactory: NewsDataSourceFactory): ViewModel() {
    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(true)
        .build()

    private val pagedList = LivePagedListBuilder(newsDataSourceFactory, config)
        .build()

    val mutableProgressVisibility = MutableLiveData<Int>().apply { View.VISIBLE }
    val progressVisibility: LiveData<Int>
        get() = mutableProgressVisibility

    val mutableErrorMessageVisibility = MutableLiveData<Int>().apply { View.GONE }
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
        pagedList.value?.dataSource?.invalidate()
        mutableErrorMessageVisibility.value = View.GONE
        mutableProgressVisibility.value = View.VISIBLE
    }

    fun loadData(): LiveData<PagedList<Articles>> = pagedList

    fun displayError(isConnected: Boolean) {
        mutableErrorMessageVisibility.value = View.VISIBLE
        mutableProgressVisibility.value = View.GONE
        mutableErrorMessageText.value = if (isConnected) R.string.generic_error else R.string.internet_error
    }

}