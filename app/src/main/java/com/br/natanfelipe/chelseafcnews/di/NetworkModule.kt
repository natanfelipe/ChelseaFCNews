package com.br.natanfelipe.chelseafcnews.di

import com.br.natanfelipe.chelseafcnews.BuildConfig
import com.br.natanfelipe.chelseafcnews.datasource.NewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideClient() }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(NewsApi::class.java)

fun provideClient() = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor())
    .build()