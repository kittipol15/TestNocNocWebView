package com.example.testwebview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.*

class SmartWebViewClient(
    val activity: Activity,
    val extraRedirect: ((String, String?) -> Unit)? = null
) : WebViewClient() {
    val REDIRECT = "event"
    var fragment: androidx.fragment.app.Fragment? = null


    constructor(
        fragment: androidx.fragment.app.Fragment,
        extraRedirect: ((String, String?) -> Unit)? = null
    ) : this(fragment.requireActivity(), extraRedirect) {
        this.fragment = fragment
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request)
    }

    /*override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (url.startsWith("https://line.ee") || url.startsWith("https://line.me"))
            activity.finish()
        return handleUrl(view, url)
    }*/

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }


    private fun handleUrl(view: WebView, url: String): Boolean {
        val uri = Uri.parse(url)
        val redirectParam = try {
            uri.getQueryParameter(REDIRECT)
        } catch (e: Exception) {
            null
        }
        if (redirectParam != null) {
            extraRedirect?.invoke(redirectParam, url)
            return true
        } else {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                activity.startActivity(intent)

                if (fragment == null) {
                    activity.finish()
                }
            } catch (e: Exception) {
            }
            return true
        }
    }
}


