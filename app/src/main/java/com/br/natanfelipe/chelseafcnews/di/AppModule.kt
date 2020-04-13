package com.br.natanfelipe.chelseafcnews.di

import com.br.natanfelipe.chelseafcnews.datasource.NewsApi
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideRepository(get()) }
    viewModel { provideViewModel(get()) }
}

fun provideRepository(newsApi: NewsApi) = NewsRepository(newsApi)
fun provideViewModel(repository: NewsRepository) = HomeViewModel(repository)
