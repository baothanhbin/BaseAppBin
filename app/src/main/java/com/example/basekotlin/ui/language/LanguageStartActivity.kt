package com.example.basekotlin.ui.language

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.applyRecyclerTapBehavior
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityLanguageStartBinding
import com.example.basekotlin.ui.intro.IntroActivity
import com.example.basekotlin.ui.language.adapter.LanguageAdapter
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.SystemUtil

class LanguageStartActivity :
    BaseActivity<ActivityLanguageStartBinding>(ActivityLanguageStartBinding::inflate) {

    private var codeLang: String? = null
    private var toast: Toast? = null
    private val languageAdapter by lazy {
        LanguageAdapter { selectedCode ->
            codeLang = selectedCode
        }
    }

    override fun getData() {
        super.getData()
        codeLang = SystemUtil.getPreLanguage(this)
        submitLanguages()
    }

    override fun initView() {
        applySystemBarInsets()
        window.statusBarColor = ContextCompat.getColor(this, R.color.language_start_bg)
        binding.rcvLangStart.applyRecyclerTapBehavior()
        binding.rcvLangStart.adapter = languageAdapter
    }

    private fun applySystemBarInsets() {
        val contentTopPadding = binding.contentContainer.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.contentContainer) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = contentTopPadding + statusBarInsets.top)
            insets
        }
        ViewCompat.requestApplyInsets(binding.contentContainer)
    }

    override fun bindView() {
        binding.ivCheck.tap {
            if (!codeLang.isNullOrEmpty()) {
                SystemUtil.saveLocale(this, codeLang)
                startNextActivity()
            } else {
                toast?.cancel()
                toast = Toast.makeText(
                    this,
                    getString(R.string.please_choose_a_language),
                    Toast.LENGTH_SHORT
                )
                toast?.show()
            }
        }
    }

    private fun startNextActivity() {
        startNextActivity(IntroActivity::class.java, null)
        finish()
    }

    override fun onBack() {
        finishAffinity()
    }

    private fun submitLanguages() {
        languageAdapter.submitList(
            InsertListManager.getListLanguage(this@LanguageStartActivity),
            codeLang
        )
    }
}
