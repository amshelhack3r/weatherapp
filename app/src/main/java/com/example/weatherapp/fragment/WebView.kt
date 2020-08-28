package com.example.weatherapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.weatherapp.R
import com.example.weatherapp.util.webview_url
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebView : Fragment() {
    private lateinit var webView:WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: android.webkit.WebView?,
                url: String?
            ): Boolean {
                view?.loadUrl(webview_url)
                return true
            }
        }

        webview.loadUrl(webview_url)

    }

}