package com.example.basekotlin.ui.permission

import androidx.activity.result.contract.ActivityResultContracts
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.tap
import com.example.basekotlin.data.repository.PermissionPromptRepository
import com.example.basekotlin.databinding.ActivityPermissionBinding
import com.example.basekotlin.permission.PermissionManager
import com.example.basekotlin.permission.openAppSettings
import com.example.basekotlin.ui.main.MainActivity
import com.example.basekotlin.R

class PermissionActivity :
    BaseActivity<ActivityPermissionBinding>(ActivityPermissionBinding::inflate) {

    private val permissionPromptRepository by lazy {
        PermissionPromptRepository(this)
    }
    private var isOpeningHome = false

    private val videoPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                updatePermissionState(hasVideoPermission = true)
                permissionPromptRepository.resetVideoPermissionDenials()
                return@registerForActivityResult
            }

            updatePermissionState(hasVideoPermission = false)
            handlePermissionDenied(permissionPromptRepository.markVideoPermissionDenied())
        }

    override fun onResume() {
        super.onResume()
        updatePermissionState()
    }

    override fun bindView() {
        binding.videoToggle.tap { requestOrOpenVideoSettings() }
        binding.tvContinue.tap { openHome() }
        updatePermissionState()
    }

    private fun requestOrOpenVideoSettings() {
        if (PermissionManager.hasVideoPermission(this)) {
            updatePermissionState()
            return
        }

        if (permissionPromptRepository.videoPermissionDenyCount() >=
            PERMISSION_DENY_DIALOG_THRESHOLD
        ) {
            openAppSettings()
            return
        }

        videoPermissionLauncher.launch(PermissionManager.videoPermission())
    }

    private fun handlePermissionDenied(denyCount: Int) {
        if (denyCount >= PERMISSION_OPEN_SETTINGS_THRESHOLD) {
            openAppSettings()
        }
    }

    private fun updatePermissionState(
        hasVideoPermission: Boolean = PermissionManager.hasVideoPermission(this),
    ) {
        binding.videoToggle.setImageResource(
            if (hasVideoPermission) R.drawable.iv_sw_on else R.drawable.iv_sw_off
        )
        binding.tvContinue.setText(
            if (hasVideoPermission) {
                R.string.agree_and_continue
            } else {
                R.string.skip_and_continue
            }
        )
        binding.tvContinue.alpha = if (hasVideoPermission) 1f else 0.92f
    }

    private fun openHome() {
        if (isOpeningHome) return
        isOpeningHome = true
        binding.tvContinue.isEnabled = false
        startNextActivity(MainActivity::class.java, null)
        finishAffinity()
    }

    override fun onBack() {
        finishAffinity()
    }

    private companion object {
        const val PERMISSION_DENY_DIALOG_THRESHOLD = 2
        const val PERMISSION_OPEN_SETTINGS_THRESHOLD = 3
    }
}
