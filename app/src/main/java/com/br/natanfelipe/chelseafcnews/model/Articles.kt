package com.br.natanfelipe.chelseafcnews.model

data class Articles(
    val source: Sources,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)