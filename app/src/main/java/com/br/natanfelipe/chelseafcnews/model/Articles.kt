package com.br.natanfelipe.chelseafcnews.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticlesList(
    val articles: List<Articles>,
    val status: String,
    val totalResults: Int
): Parcelable

@Parcelize
data class Articles(
    val source: Sources,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
):Parcelable