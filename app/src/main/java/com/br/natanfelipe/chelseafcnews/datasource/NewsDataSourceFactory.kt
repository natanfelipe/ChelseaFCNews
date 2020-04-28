package com.br.natanfelipe.chelseafcnews.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.br.natanfelipe.chelseafcnews.model.Articles

class NewsDataSourceFactory: DataSource.Factory<Int,Articles>() {

    private val postLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, Articles> {
        postLiveData.postValue(newsDataSource())
        return newsDataSource()
    }

    private fun newsDataSource(): NewsDataSource = NewsDataSource()


}