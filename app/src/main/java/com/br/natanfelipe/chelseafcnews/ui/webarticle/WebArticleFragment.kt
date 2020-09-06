package com.br.natanfelipe.chelseafcnews.ui.webarticle

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.databinding.DataBindingUtil
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.common.hasInternetConnection
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

    private var isError = false

    private fun isOnline() = requireContext().hasInternetConnection()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWebArticleBinding>(
            inflater,
            R.layout.fragment_web_article, container, false
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnectivity(savedInstanceState)
    }

    private fun checkConnectivity(savedInstanceState: Bundle?) {
        when (isOnline()) {
            true -> {
                prepareWebView(savedInstanceState)
            }
            else -> viewModel.isDeviceOnline(isOnline())
        }
    }

    private fun prepareWebView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null && viewModel.isUrlWorking()) {
            webview.apply {
                settings.apply {
                    loadsImagesAutomatically = true
                    javaScriptEnabled = true
                }
                loadWebView(this)
            }
        }
    }

    private fun loadWebView(webView: WebView) {
        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    isError = false
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (isError) {
                        if (isOnline()) {
                            viewModel.genericError()
                        } else {
                            viewModel.isDeviceOnline(isOnline())
                        }
                    } else {
                        viewModel.loadPage()
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    isError = true
                }
            }
            loadUrl(viewModel.linkUrl)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            webview.restoreState(it)
        }
    }

}
