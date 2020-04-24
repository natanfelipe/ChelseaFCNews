package com.br.natanfelipe.chelseafcnews.viewmodel

import androidx.lifecycle.ViewModel
import com.br.natanfelipe.chelseafcnews.model.Articles

class DetailsViewModel(private val articles: Articles): ViewModel() {

    val image: String
        get() = articles.urlToImage

    val title: String
        get() = articles.title

    val content: String
        get() = articles.content

    val author: String
        get() = articles.author

    val date: String
        get() = articles.publishedAt

    val link: String
        get() = articles.url


}