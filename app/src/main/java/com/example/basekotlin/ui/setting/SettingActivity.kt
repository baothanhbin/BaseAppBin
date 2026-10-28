package com.example.basekotlin.ui.setting

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivitySettingsBinding
import com.example.basekotlin.ui.about.AboutActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.SharedPreUtils
import com.example.basekotlin.util.SystemUtil
import com.example.basekotlin.util.rateApp
import com.example.basekotlin.util.shareApp

class SettingActivity : BaseActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate) {

    override fun getData() {
        val codeLang = SystemUtil.getPreLanguage(this)
        binding.tvLang.text = InsertListManager.getLanguageName(this@SettingActivity, codeLang)

        if (SharedPreUtils.getInstance().isRated(this)) {
            binding.btnRateUs.gone()
        }
    }

    override fun initView() {
        applySystemBarInsets()
    }

    override fun bindView() {
        binding.btnBack.tap { onBack() }

        binding.btnLanguage.tap { startNextActivity(SettingLanguageActivity::class.java, null) }

        binding.btnShare.tap { shareApp() }

        binding.btnRateUs.tap { rateApp(binding.btnRateUs) }

        binding.btnAbout.tap { startNextActivity(AboutActivity::class.java, null) }
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
