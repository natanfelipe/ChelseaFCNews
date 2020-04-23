package com.br.natanfelipe.chelseafcnews.di

import com.br.natanfelipe.chelseafcnews.datasource.NewsApi
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSource
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSourceFactory
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideRepository(get()) }
    single { provideDataSourceFactory() }
    viewModel { provideViewModel(get()) }
    factory { provideDataSource() }
}

fun provideDataSource() = NewsDataSource()
fun provideDataSourceFactory() = NewsDataSourceFactory()
fun provideRepository(newsApi: NewsApi) = NewsRepository(newsApi)
fun provideViewModel(newsDataSourceFactory: NewsDataSourceFactory) = HomeViewModel(newsDataSourceFactory)
