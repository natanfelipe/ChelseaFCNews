package com.br.natanfelipe.chelseafcnews.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var adapter = HomeAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsList.adapter = adapter

        homeViewModel.getNews()

        refreshList.setOnRefreshListener {
            refresh()
        }

        loadNews()
    }

    private fun loadNews() {
        homeViewModel.articlesList.observe(viewLifecycleOwner, Observer { articles ->
            val loading = homeViewModel.isLoading
            if(loading.value == false) {
                progress.visibility = View.GONE
            }
            newsList.visibility = View.VISIBLE
            adapter.updateList(articles)
        })
    }

    private fun refresh() {
        newsList.visibility = View.GONE
        progress.visibility = View.VISIBLE
        homeViewModel.refresh()
        refreshList.isRefreshing = false
    }

}
