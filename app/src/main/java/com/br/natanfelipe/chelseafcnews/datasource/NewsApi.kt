package com.br.natanfelipe.chelseafcnews.datasource

import com.br.natanfelipe.chelseafcnews.model.ArticlesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getAllNews(
        @Query("page") page: Int
    ): Response<ArticlesList>
}