package com.br.natanfelipe.chelseafcnews.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.natanfelipe.chelseafcnews.R

class WebArticleViewModel(private val url: String): ViewModel() {

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility:LiveData<Int>
        get() = _loadingVisibility
    private val _errorVisibility = MutableLiveData<Int>()
    val linkUrl = url
    private val _errorMessage = MutableLiveData<Any>()
    val errorMessage: LiveData<Any>
        get() = _errorMessage

    val errorVisibility: LiveData<Int>
        get() = _errorVisibility
    private val _webViewVisibility = MutableLiveData<Int>()
    val webViewVisibility: LiveData<Int>
        get() = _webViewVisibility


    fun isUrlWorking(): Boolean = linkUrl.isNotEmpty()

    fun loadPage() {
        handleErrorVisibility(isNotError = true, isGenericError = null)
    }

    fun genericError() {
        handleErrorVisibility(isNotError = false, isGenericError = true)
    }

    fun isDeviceOnline(isConnected: Boolean) {
        handleErrorVisibility(false, isConnected)
    }


    private fun handleErrorVisibility(isNotError: Boolean, isGenericError: Boolean?) {
        _loadingVisibility.value = View.GONE
        if (isNotError) {
            _webViewVisibility.value = View.VISIBLE
            _errorVisibility.value = View.GONE
            _errorMessage.value = ""
        } else {
            _webViewVisibility.value = View.GONE
            _errorVisibility.value = View.VISIBLE
            isGenericError?.let {
                _errorMessage.value = if (it) R.string.generic_error else R.string.internet_error

            }
        }
    }
}