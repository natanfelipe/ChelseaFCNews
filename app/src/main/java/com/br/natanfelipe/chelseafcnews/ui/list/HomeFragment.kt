package com.br.natanfelipe.chelseafcnews.ui.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.common.EspressoIdlingResources
import com.br.natanfelipe.chelseafcnews.common.Utils
import com.br.natanfelipe.chelseafcnews.databinding.FragmentHomeBindingImpl
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val utils: Utils by inject()
    private lateinit var adapter: HomeAdapter

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

        val context = this.getContext()

        adapter = HomeAdapter(homeViewModel.mutableProgressVisibility)
        newsList.adapter = adapter
        context?.let {
            loadNews(it)
        }
        refreshList.setOnRefreshListener {
            refresh()
        }
    }

    private fun loadNews(context: Context) {
        val isDeviceOnline = utils.isInternetAvailable(context)
        homeViewModel.apply {
            if (isDeviceOnline) {
                EspressoIdlingResources.increment()
                loadData().observe(viewLifecycleOwner, Observer { articles ->
                    adapter.submitList(articles)
                    animateRecyclerView()
                })
            } else {
                displayErrorMessage(isDeviceOnline)
            }
        }

    }

    private fun refresh() {
        homeViewModel.apply {
            articles.value?.let {
                it.dataSource.invalidate()
            }
            refresh()
            context?.let {
                loadNews(it)
            }
        }
        refreshList.isRefreshing = false
    }

    private fun animateRecyclerView() {
        val resId = R.anim.layout_animation_collapse
        val animation = AnimationUtils.loadLayoutAnimation(this.context, resId)
        newsList.setLayoutAnimation(animation)
    }
}
