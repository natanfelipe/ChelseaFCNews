package com.br.natanfelipe.chelseafcnews.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.natanfelipe.chelseafcnews.databinding.ItemListBinding
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.model.ArticlesList

class HomeAdapter(private val articles: ArrayList<Articles>): RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemListBinding.inflate(inflater, parent, false)

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.view.article = articles[position]
    }

    fun updateList(list: ArticlesList) {
        articles.clear()
        articles.addAll(list.articles)
        notifyDataSetChanged()
    }

}