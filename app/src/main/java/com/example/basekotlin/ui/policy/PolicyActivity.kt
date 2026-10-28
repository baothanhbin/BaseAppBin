package com.example.basekotlin.ui.policy

import android.annotation.SuppressLint
import android.net.http.SslError
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.basekotlin.R
import com.example.basekotlin.ads.IsNetWork
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.inVisible
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.databinding.ActivityPolicyBinding
import com.example.basekotlin.util.SettingManager

class PolicyActivity : BaseActivity<ActivityPolicyBinding>(ActivityPolicyBinding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        applySystemBarInsets()/*        binding.viewTop.tvToolBar.text = getString(R.string.privacy_policy)
                binding.viewTop.btnBack.inVisible()*/
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(
                view: WebView, handler: SslErrorHandler, error: SslError
            ) {
                handler.proceed()
            }
        }
        if (SettingManager.linkPolicy != "" && IsNetWork.haveNetworkConnection(this)) {
            binding.webView.visible()
            binding.lnNoInternet.gone()

            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(SettingManager.linkPolicy)
        } else {
            binding.webView.gone()
            binding.lnNoInternet.visible()
        }
    }

    override fun bindView() {
        binding.viewTop.btnBack.tap { onBack() }
    }

    private fun applySystemBarInsets() {
        val contentTopPadding = binding.root.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = contentTopPadding + statusBarInsets.top)
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

}