package com.br.natanfelipe.chelseafcnews.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebArticleViewModel(private val url: String): ViewModel() {

    private val _loading = MutableLiveData<Int>().apply { value = View.VISIBLE }
    private val _loadingVisibility = MutableLiveData<Int>()
    private val _errorVisibility = MutableLiveData<Int>()
    val linkUrl = url
    val loading: LiveData<Int>
        get() = _loading
    val loadingVisibility:LiveData<Int>
        get() = _loadingVisibility
    val errorVisibility: LiveData<Int>
        get() = _errorVisibility

    fun isUrlWorking(): Boolean {
        val isToLoad = !linkUrl.isNullOrEmpty()

        if(!isToLoad) {
           _errorVisibility.value = View.VISIBLE
        } else {
            _errorVisibility.value = View.GONE
        }

        return isToLoad
    }


    fun handleLoading(loading: Int) {
        _loading.value = loading
        if(loading == 100){
            _loadingVisibility.value = View.GONE
        }
    }
}