package com.br.natanfelipe.chelseafcnews.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.br.natanfelipe.chelseafcnews.model.Articles
import org.koin.core.KoinComponent
import org.koin.core.inject

class NewsDataSourceFactory: DataSource.Factory<Int,Articles>(), KoinComponent {

    private val postLiveData = MutableLiveData<NewsDataSource>()
    val articleDataSource: NewsDataSource by inject()

    override fun create(): DataSource<Int, Articles> {
        postLiveData.postValue(articleDataSource)
        return articleDataSource
    }



}