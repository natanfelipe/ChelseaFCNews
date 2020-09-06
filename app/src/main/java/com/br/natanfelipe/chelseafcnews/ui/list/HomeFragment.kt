package com.br.natanfelipe.chelseafcnews.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.common.hasInternetConnection
import com.br.natanfelipe.chelseafcnews.databinding.FragmentHomeBindingImpl
import com.br.natanfelipe.chelseafcnews.model.NetworkState
import com.br.natanfelipe.chelseafcnews.util.EspressoIdlingResources
import com.br.natanfelipe.chelseafcnews.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val adapter = HomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBindingImpl>(
            inflater, R.layout.fragment_home, container, false
        )

        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNews()
        refreshNews()
    }

    private fun isDeviceOnline() = requireContext().hasInternetConnection()

    private fun loadNews() {
        newsList.adapter = adapter

        homeViewModel.apply {
            if (isDeviceOnline()) {
                EspressoIdlingResources.increment()

                articlesDataSource.loadState.observe(viewLifecycleOwner, Observer { state ->

                    when (state) {
                        NetworkState.LOADING -> {
                            mutableProgressVisibility.value = View.VISIBLE
                            mutableErrorMessageVisibility.value = View.GONE
                        }
                        NetworkState.LOADED -> {
                            EspressoIdlingResources.decrement()
                            mutableProgressVisibility.value = View.GONE
                        }
                        else -> displayError(isDeviceOnline())
                    }
                })


                loadData().observe(viewLifecycleOwner, Observer { articles ->
                    adapter.submitList(articles)
                    animateRecyclerView()
                })
            } else {
                displayError(!isDeviceOnline())
            }
        }
    }

    private fun refreshNews() {
        refreshList.setOnRefreshListener {
            homeViewModel.refresh()
            loadNews()
            refreshList.isRefreshing = false
        }
    }

    private fun animateRecyclerView() {
        val resId = R.anim.layout_animation_collapse
        val animation = AnimationUtils.loadLayoutAnimation(this.context, resId)
        newsList.layoutAnimation = animation
    }
}
