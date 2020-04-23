package com.br.natanfelipe.chelseafcnews.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.br.natanfelipe.chelseafcnews.databinding.ItemListBinding
import com.br.natanfelipe.chelseafcnews.model.Articles

class HomeViewHolder(var view: ItemListBinding): RecyclerView.ViewHolder(view.root){
    fun bind(article: Articles) {
        view.article = article
    }
}