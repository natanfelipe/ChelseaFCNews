package com.br.natanfelipe.chelseafcnews.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSourceFactory
import com.br.natanfelipe.chelseafcnews.model.Articles
import okhttp3.ResponseBody

class HomeViewModel(private val newsDataSourceFactory: NewsDataSourceFactory): BaseViewModel() {
    var articles: LiveData<PagedList<Articles>>
    val mutableProgressVisibility = MutableLiveData<Int>().apply { View.VISIBLE }
    val progressVisibility: LiveData<Int>
    get() = mutableProgressVisibility
    private val mutableListVisibility = MutableLiveData<Int>().apply { View.GONE }
    val listVisibility: LiveData<Int>
    get() = mutableListVisibility
    private val mutableErrorMessageVisibility = MutableLiveData<Int>().apply { View.GONE }
    val errorMessageVisibility: LiveData<Int>
    get() = mutableErrorMessageVisibility
    private val mutableErrorMessageText = MutableLiveData<Int>().apply { R.string.generic_error }
    val errorMessageText: LiveData<Int>
    get() = mutableErrorMessageText

    init {
        articles = initPagination()
    }

    fun refresh() {
        mutableProgressVisibility.value = View.VISIBLE
        mutableErrorMessageVisibility.value = View.GONE
        mutableListVisibility.value = View.GONE
    }

    fun loadData(): LiveData<PagedList<Articles>> {
        if (articles.value?.size == 0) {
            mutableErrorMessageVisibility.value = View.VISIBLE
            mutableListVisibility.value = View.GONE
        } else {
            mutableErrorMessageVisibility.value = View.GONE
            mutableErrorMessageText.value = R.string.generic_error
            mutableListVisibility.value = View.VISIBLE
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

    fun displayErrorMessage(isDeviceOffline: Boolean) {
        mutableErrorMessageVisibility.value = View.VISIBLE
        mutableListVisibility.value = View.GONE
        mutableProgressVisibility.value = View.GONE
        mutableErrorMessageText.value = if (!isDeviceOffline) R.string.internet_error else R.string.generic_error
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override fun errorMessage(errorBody: ResponseBody) {
        TODO("Not yet implemented")
    }

    override fun setState(data: List<Articles>) {
        TODO("Not yet implemented")
    }
}