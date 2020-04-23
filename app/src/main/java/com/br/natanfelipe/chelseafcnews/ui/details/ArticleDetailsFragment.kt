package com.br.natanfelipe.chelseafcnews.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.databinding.FragmentArticleDetailsBindingImpl
import com.br.natanfelipe.chelseafcnews.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_article_details.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ArticleDetailsFragment : Fragment() {

    private lateinit var appCompatActivity: AppCompatActivity

    private val viewModel: DetailsViewModel by inject {
        parametersOf(arguments?.let {
            ArticleDetailsFragmentArgs.fromBundle(it).article
        })
    }
    private lateinit var binding: FragmentArticleDetailsBindingImpl
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appCompatActivity = activity as AppCompatActivity

        binding = DataBindingUtil.inflate<FragmentArticleDetailsBindingImpl>(
            inflater, R.layout.fragment_article_details, container, false
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        appCompatActivity.supportActionBar?.let {
            it.hide()
        }
        toolbar.setNavigationOnClickListener { appCompatActivity.onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        appCompatActivity.supportActionBar?.let {
            it.show()
        }
    }

}
