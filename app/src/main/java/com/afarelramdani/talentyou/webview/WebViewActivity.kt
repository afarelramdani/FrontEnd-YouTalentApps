package com.afarelramdani.talentyou.webview

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(), WebViewListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_web_view
        super.onCreate(savedInstanceState)

        var git = sharePref.getGithub()

        binding.webView.loadUrl("https://github.com/${git}")
        binding.webView.webChromeClient = GithubChromeClient(this)
        binding.webView.webViewClient = GithubViewClient(this)

    }
    class GithubChromeClient(private var listener: WebViewListener) : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            listener.onProgressChange(newProgress)
            super.onProgressChanged(view, newProgress)
        }
}
    class GithubViewClient(private var listener: WebViewListener) :WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            listener.onPageStarted()
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            listener.onPageFinish()
            super.onPageFinished(view, url)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            listener.onShouldOverrideUrl(request?.url?.toString().orEmpty())
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    override fun onPageStarted() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onPageFinish() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onShouldOverrideUrl(redirectUrl: String) {
        Toast.makeText(this, "redirect url = $redirectUrl", Toast.LENGTH_SHORT).show()
    }

    override fun onProgressChange(progress: Int) {
        binding.progressBar.progress = progress
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}