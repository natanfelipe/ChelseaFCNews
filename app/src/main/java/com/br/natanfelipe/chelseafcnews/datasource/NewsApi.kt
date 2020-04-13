package com.br.natanfelipe.chelseafcnews.datasource

import com.br.natanfelipe.chelseafcnews.model.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("apiKey") key: String
    ):Response<List<Articles>>
}