package com.br.natanfelipe.chelseafcnews.di

import com.br.natanfelipe.chelseafcnews.datasource.NewsApi
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSource
import com.br.natanfelipe.chelseafcnews.datasource.NewsDataSourceFactory
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import com.br.natanfelipe.chelseafcnews.viewmodel.DetailsViewModel
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import com.br.natanfelipe.chelseafcnews.viewmodel.WebArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideRepository(get()) }
    single { NewsDataSource(get()) }
    factory { provideNewsDataSourceFactory() }
    viewModel { provideViewModel(get()) }
    viewModel { (articles: Articles) -> DetailsViewModel(articles) }
    viewModel { (url: String) -> WebArticleViewModel(url) }
}

fun provideNewsDataSourceFactory() = NewsDataSourceFactory()
fun provideRepository(newsApi: NewsApi) = NewsRepository(newsApi)
fun provideViewModel(newsDataSourceFactory: NewsDataSourceFactory): HomeViewModel = HomeViewModel(newsDataSourceFactory)

