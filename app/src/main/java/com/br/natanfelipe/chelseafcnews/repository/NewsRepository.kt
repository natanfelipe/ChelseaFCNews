package com.br.natanfelipe.chelseafcnews.repository

import com.br.natanfelipe.chelseafcnews.BuildConfig
import com.br.natanfelipe.chelseafcnews.datasource.NewsApi

class NewsRepository(val newsApi: NewsApi) {

    suspend fun getAllNews() = newsApi.getAllNews(
        BuildConfig.THEME, 1, "en", BuildConfig.API_KEY
    )
}