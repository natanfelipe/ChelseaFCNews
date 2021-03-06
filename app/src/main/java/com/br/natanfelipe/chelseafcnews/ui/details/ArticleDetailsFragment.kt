package com.br.natanfelipe.chelseafcnews.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.common.TEXT_TYPE
import com.br.natanfelipe.chelseafcnews.databinding.FragmentArticleDetailsBindingImpl
import com.br.natanfelipe.chelseafcnews.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_article_details.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ArticleDetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by inject {
        parametersOf(arguments?.let {
            ArticleDetailsFragmentArgs.fromBundle(it).article
        })
    }

    private lateinit var appCompatActivity: AppCompatActivity
    private lateinit var binding: FragmentArticleDetailsBindingImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appCompatActivity = activity as AppCompatActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article_details, container, false
        )

        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        toolbar.setNavigationOnClickListener { appCompatActivity.onBackPressed() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            createShareIntent()
        }
    }

    private fun createShareIntent() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, viewModel.link)
            type = TEXT_TYPE
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
}
