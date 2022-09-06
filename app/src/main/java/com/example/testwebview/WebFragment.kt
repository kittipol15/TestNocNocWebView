package com.example.testwebview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import im.delight.android.webview.AdvancedWebView
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : Fragment(), AdvancedWebView.Listener {
    companion object {
        const val URL = "URL"
        private const val IS_RECEIVED_CHROME_CLIENT = "IS_RECEIVED_CHROME_CLIENT"

        fun newInstance(url: String, isReceivedChromeClient: Boolean): WebFragment {
            val fragment = WebFragment()
            val bundle = Bundle()
            bundle.putString(URL, url)
            bundle.putBoolean(IS_RECEIVED_CHROME_CLIENT, isReceivedChromeClient)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mUrl: String? = null
    private var isReceivedChromeClient: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = arguments?.getString(URL)
        isReceivedChromeClient = arguments?.getBoolean(IS_RECEIVED_CHROME_CLIENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            // Restore the previous URL and history stack
            webview.restoreState(savedInstanceState)
        }

        initWebView(mUrl.toDefaultValue())
        if (isReceivedChromeClient == true) webview.webChromeClient = mSWWebChromeClient
    }

    private fun initWebView(url: String) {
        if (webview != null) {
            webview.setListener(activity, this)
            webview.makeItSmart(this, progressBar, { query, url ->

            })
            val headerMap = HashMap<String, String>()
            //headerMap["Authorization"] =  "Bearer ${getAccessToken()}"
            //headerMap["Set-Cookie"] = "${getCookies()}"
            webview.loadUrl(url, headerMap)
        }
    }

    private val mSWWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
        }
    }

    override fun onResume() {
        super.onResume()
        webview?.onResume()
    }

    override fun onPause() {
        webview?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        webview?.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        webview.onActivityResult(requestCode, resultCode, data)
    }


    override fun onPageFinished(url: String?) {
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
//        Log.d("WebActivity", description)
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
//        Log.d("WebActivity", url)
//        url?.let { activity?.launchExternalBrowser(url) }
    }

    override fun onExternalPageRequest(url: String?) {
//        Log.d("WebActivity", url)
//        url?.let { activity?.launchExternalBrowser(url) }
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
    }
}
