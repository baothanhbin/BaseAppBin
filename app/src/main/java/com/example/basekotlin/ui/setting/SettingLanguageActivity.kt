package com.example.basekotlin.ui.setting

import android.content.Intent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivitySettingLanguageBinding
import com.example.basekotlin.ui.main.MainActivity
import com.example.basekotlin.ui.language.adapter.LanguageAdapter
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.SystemUtil

class SettingLanguageActivity :
    BaseActivity<ActivitySettingLanguageBinding>(ActivitySettingLanguageBinding::inflate) {

    private var codeLang: String? = null
    private var isSavingLanguage = false

    override fun initView() {
        applySystemBarInsets()
    }

    override fun getData() {
        codeLang = SystemUtil.getPreLanguage(this)

        binding.rcvLang.adapter = LanguageAdapter { selectedCode ->
            if (isSavingLanguage) return@LanguageAdapter
            codeLang = selectedCode
        }.apply {
            submitList(InsertListManager.getListLanguage(this@SettingLanguageActivity), codeLang)
        }
    }

    override fun bindView() {
        binding.btnBack.tap { onBack() }
        binding.ivCheck.tap { saveLanguageAndReturnHome() }
    }

    private fun saveLanguageAndReturnHome() {
        if (isSavingLanguage) return
        isSavingLanguage = true

        SystemUtil.saveLocale(this@SettingLanguageActivity, codeLang)
        startActivity(
            Intent(this@SettingLanguageActivity, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        overridePendingTransition(R.anim.in_left, R.anim.out_right)
        finish()
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
