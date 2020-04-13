package com.br.natanfelipe.chelseafcnews.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.br.natanfelipe.chelseafcnews.model.ArticlesList
import com.br.natanfelipe.chelseafcnews.repository.NewsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NewsRepository): BaseViewModel() {
    val articlesList by lazy { MutableLiveData<ArticlesList>() }
    private val loading = MutableLiveData<Int>().apply { View.VISIBLE }
    val progressVisibility: LiveData<Int>
    get() = loading
    private val showList = MutableLiveData<Int>().apply { View.VISIBLE }
    val listVisibility: LiveData<Int>
    get() = showList
    private val showError = MutableLiveData<Int>().apply { View.VISIBLE }
    val errorMessageVisibility: LiveData<Int>
    get() = showError

    fun refresh() {
        loading.value = View.VISIBLE
        showError.value = View.GONE
        showList.value = View.GONE
        getNews()
    }

    fun getNews() {
        launch {
            val response = repository.getAllNews()
            loading.value = View.GONE

            if(response.isSuccessful) {
                showList.value = View.VISIBLE
                articlesList.value = response.body()
            } else {
                showError.value = View.VISIBLE
            }
        }
    }
}