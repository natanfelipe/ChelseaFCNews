package com.br.natanfelipe.chelseafcnews.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.natanfelipe.chelseafcnews.databinding.ItemListBinding
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.model.ArticlesList

class HomeAdapter(private val articles: ArrayList<Articles>): PagedListAdapter<Articles, HomeViewHolder>(articlesDiffCallback){//RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemListBinding.inflate(inflater, parent, false)

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.view.article = articles[position]
    }

    companion object {
        private val articlesDiffCallback = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles) =
                oldItem == newItem
        }
    }

}