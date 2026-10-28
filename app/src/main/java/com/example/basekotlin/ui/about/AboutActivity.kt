package com.example.basekotlin.ui.about

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.basekotlin.BuildConfig
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.inVisible
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityAboutBinding
import com.example.basekotlin.ui.policy.PolicyActivity

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun getData() {
        binding.tvVersion.text = getString(R.string.version) + " ${BuildConfig.VERSION_NAME}"
        binding.tvPolicy.paintFlags = binding.tvPolicy.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    override fun bindView() {
        binding.btnBack.tap { onBack() }
        binding.tvPolicy.tap { startNextActivity(PolicyActivity::class.java, null) }
    }
    override fun initView() {
        applySystemBarInsets()
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