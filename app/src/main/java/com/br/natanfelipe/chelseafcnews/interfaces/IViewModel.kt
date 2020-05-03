package com.br.natanfelipe.chelseafcnews.interfaces

import com.br.natanfelipe.chelseafcnews.model.Articles
import okhttp3.ResponseBody

interface IViewModel {
    fun errorMessage(errorBody: ResponseBody)

    fun setState(data: List<Articles>)
}