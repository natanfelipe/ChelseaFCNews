package com.br.natanfelipe.chelseafcnews.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
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
        loadNews()
        showProgressBar(homeViewModel.isLoading)
        showErrorMessage(homeViewModel.isError)

        refreshList.setOnRefreshListener {
            refresh()
        }
    }

    private fun loadNews() {
        homeViewModel.articlesList.observe(viewLifecycleOwner, Observer { articles ->
            newsList.visibility = View.VISIBLE
            adapter.updateList(articles)
        })
    }

    private fun refresh() {
        newsList.visibility = View.GONE
        errorMessage.visibility = View.GONE
        progress.visibility = View.VISIBLE
        homeViewModel.refresh()
        refreshList.isRefreshing = false
    }

    private fun showErrorMessage(errorMessage: MutableLiveData<Boolean>) {
        errorMessage.observe(viewLifecycleOwner, Observer { isError ->
            if(isError) {
                this.errorMessage.visibility = View.VISIBLE
            }
        })
    }

    private fun showProgressBar(loading: MutableLiveData<Boolean>) {
        loading.observe(viewLifecycleOwner, Observer {isLoading ->
            progress.visibility = if(isLoading) View.VISIBLE else View.GONE
            if(isLoading){
                newsList.visibility = View.GONE
                errorMessage.visibility = View.GONE
            }

        })
    }

}
