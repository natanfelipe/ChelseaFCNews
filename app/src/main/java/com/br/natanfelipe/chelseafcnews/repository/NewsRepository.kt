package com.br.natanfelipe.chelseafcnews.repository

import com.br.natanfelipe.chelseafcnews.BuildConfig
import com.br.natanfelipe.chelseafcnews.datasource.NewsApi

class NewsRepository(val newsApi: NewsApi) {

    suspend fun getAllNews(page: Int) = newsApi.getAllNews(
        BuildConfig.THEME, page, "en", BuildConfig.API_KEY
    )
}