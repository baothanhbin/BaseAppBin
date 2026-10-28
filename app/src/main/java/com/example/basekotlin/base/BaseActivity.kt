package com.example.basekotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.viewbinding.ViewBinding
import com.example.basekotlin.R
import com.example.basekotlin.util.SystemUtil

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    protected val binding: VB by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        SystemUtil.setLocale(this)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        window.hideNavigation()

        getData()
        initView()
        bindView()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })
    }

    open fun getData() {

    }

    open fun initView() {

    }

    open fun bindView() {

    }

    open fun reloadAds() {

    }

    protected fun clearInputFocus(target: EditText? = currentFocus as? EditText) {
        val editText = target ?: return
        editText.clearFocus()
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        getSystemService<InputMethodManager>()
            ?.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun openPrimaryTab(activity: Class<*>) {
        if (javaClass == activity) return

        clearInputFocus()
        val intent = Intent(this, activity).apply {
            // Bring an existing primary screen to the foreground to preserve its UI state.
            addFlags(
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_NO_ANIMATION,
            )
        }
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun startNextActivity(activity: Class<*>?, bundle: Bundle?) {
        var bundle = bundle
        val intent = Intent(this, activity)
        if (bundle == null) {
            bundle = Bundle()
        }
        intent.putExtras(bundle)
        resultLauncher.launch(intent)
        overridePendingTransition(R.anim.in_right, R.anim.out_left)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                reloadAds()
            }
        }

    fun finishThisActivity() {
        finish()
        overridePendingTransition(R.anim.in_left, R.anim.out_right)
    }

    open fun onBack() {
        setResult(RESULT_OK)
        finishThisActivity()
    }
}