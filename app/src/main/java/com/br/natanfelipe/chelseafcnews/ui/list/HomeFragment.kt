package com.br.natanfelipe.chelseafcnews.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.databinding.FragmentHomeBindingImpl
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var adapter = HomeAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBindingImpl>(
            inflater, R.layout.fragment_home, container, false
        )

        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsList.adapter = adapter
        homeViewModel.refresh()
        loadNews()
        refreshList.setOnRefreshListener {
            refresh()
        }
    }

    private fun loadNews() {
        homeViewModel.articlesList.observe(viewLifecycleOwner, Observer { articles ->
            adapter.updateList(articles)
        })
    }

    private fun refresh() {
        homeViewModel.refresh()
        refreshList.isRefreshing = false
    }
}
