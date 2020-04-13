package com.br.natanfelipe.chelseafcnews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.br.natanfelipe.chelseafcnews.model.ArticlesList
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NewsRepository): BaseViewModel() {
    val articlesList by lazy { MutableLiveData<ArticlesList>() }
    val isLoading by lazy { MutableLiveData<Boolean>() }
    val isError by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        isLoading.value = true
        getNews()
    }

    fun getNews() {
        launch {
            val response = repository.getAllNews()

            isLoading.value = false

            if(response.isSuccessful) {
                isError.value = false
                articlesList.value = response.body()
            } else {
                isError.value = true
            }
        }
    }
}