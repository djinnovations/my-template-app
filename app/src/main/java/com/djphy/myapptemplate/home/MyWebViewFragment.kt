package com.djphy.myapptemplate.home

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.djphy.myapptemplate.R
import com.djphy.myapptemplate.base.BaseFragment
import com.djphy.myapptemplate.databinding.FragmentWebviewBinding

class MyWebViewFragment : BaseFragment(){

    companion object {

        private const val KEY_URL = "web_link"
        private const val KEY_TITLE = "web_title"

        @JvmStatic
        fun newInstance(url: String?, title: String? = ""): MyWebViewFragment {
            val fragment = MyWebViewFragment()
            val bundle = Bundle()
            bundle.putString(KEY_URL, url)
            bundle.putString(KEY_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mViewBinding: FragmentWebviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mViewBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_webview, container, false)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url: String = arguments?.getString(KEY_URL)?: ""
        val title: String = arguments?.getString(KEY_TITLE)?: ""
        initToolBar(title)
        setWebView(url)
    }

    private fun initToolBar(title: String) {
        mViewBinding.includeToolbar.apply {
            tvTitle.text = title
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setWebView(url: String?){
        mViewBinding.apply {
            progressBar.visibility = View.VISIBLE
            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.visibility = View.GONE
                }

                @TargetApi(Build.VERSION_CODES.N)
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return super.shouldOverrideUrlLoading(view, url)
                }
            }

            webView.apply {
                clearCache(true)
                clearHistory()
                // Enable Javascript
                val webSettings: WebSettings = settings
                webSettings.javaScriptEnabled = true
                webSettings.javaScriptCanOpenWindowsAutomatically = true

                isVerticalScrollBarEnabled = true
                url?.also {
                    loadUrl(it)
                }
            }
        }
    }

}