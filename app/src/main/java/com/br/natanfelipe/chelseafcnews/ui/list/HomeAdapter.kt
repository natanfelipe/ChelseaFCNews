package com.br.natanfelipe.chelseafcnews.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.br.natanfelipe.chelseafcnews.databinding.ItemListBinding
import com.br.natanfelipe.chelseafcnews.interfaces.OnClickItemList
import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.util.EspressoIdlingResources

class HomeAdapter() : PagedListAdapter<Articles, HomeViewHolder>(articlesDiffCallback), OnClickItemList {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemListBinding.inflate(inflater, parent, false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let { article ->
            holder.bind(article)
            holder.view.click = this
        }
    }

    companion object {
        private val articlesDiffCallback = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles) =
                oldItem == newItem
        }
    }

    override fun onClick(view: View) {
        currentList?.let { articles ->
            for (article in articles) {
                if (view.tag == article.url) {
                    val action = HomeFragmentDirections.navigateToNewsDetail(article)
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }
}