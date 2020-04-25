package com.br.natanfelipe.chelseafcnews.ui.webarticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.databinding.DataBindingUtil

import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.databinding.FragmentWebArticleBinding
import com.br.natanfelipe.chelseafcnews.viewmodel.WebArticleViewModel
import kotlinx.android.synthetic.main.fragment_web_article.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class WebArticleFragment : Fragment() {

    private val viewModel: WebArticleViewModel by inject {
        parametersOf(arguments?.let {
            WebArticleFragmentArgs.fromBundle(
                it
            ).linkUrl
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWebArticleBinding>(inflater,
            R.layout.fragment_web_article, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null && viewModel.isUrlWorking()) {
            webview.settings.apply {
                loadsImagesAutomatically = true
                javaScriptEnabled = true
            }

            webview.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    viewModel.handleLoading(newProgress)
                }
            }

            webview.loadUrl(viewModel.linkUrl)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        webview.restoreState(savedInstanceState)
    }

}
