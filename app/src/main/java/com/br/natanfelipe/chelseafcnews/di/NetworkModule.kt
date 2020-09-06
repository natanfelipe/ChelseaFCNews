package com.br.natanfelipe.chelseafcnews.di

import com.br.natanfelipe.chelseafcnews.BuildConfig
import com.br.natanfelipe.chelseafcnews.common.*
import com.br.natanfelipe.chelseafcnews.datasource.NewsApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { provideClient() }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(client: OkHttpClient): NewsApi = Retrofit.Builder().apply {
    baseUrl(BuildConfig.BASE_URL)
    addConverterFactory(GsonConverterFactory.create())
    client(client)
}.build().create(NewsApi::class.java)


fun provideClient(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(provideLoggingInterceptor())
            .addNetworkInterceptor {
                provideRequestParams(it)
            }
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()

private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

private fun provideRequestParams(chain: Interceptor.Chain): Response {
    var request = chain.request()
    val newUrl = request.url.newBuilder().apply {
        addQueryParameter("q", BuildConfig.THEME)
        addQueryParameter("language", Locale.getDefault().language)
        addQueryParameter("apiKey", BuildConfig.API_KEY)
    }.build()

    request = request.newBuilder().url(newUrl).build()

    return chain.proceed(request)
}