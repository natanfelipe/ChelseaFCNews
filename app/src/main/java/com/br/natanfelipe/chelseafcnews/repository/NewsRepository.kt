package com.br.natanfelipe.chelseafcnews.repository

import com.br.natanfelipe.chelseafcnews.datasource.NewsApi

class NewsRepository(val newsApi: NewsApi) {

    suspend fun getAllNews(page: Int) = newsApi.getAllNews(page)
}