package com.example.basekotlin.dialog.permission

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseDialog
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.DialogPermissionBinding
import com.example.basekotlin.permission.PermissionManager

class PermissionDialog(
    context: Context,
    private val onRequestPermission: () -> Unit,
    @Suppress("unused") private val onCloseWithPermission: () -> Unit,
) : BaseDialog<DialogPermissionBinding>(context, false) {

    override fun setBinding(): DialogPermissionBinding {
        return DialogPermissionBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun initView() {
        renderPermissionState()
    }

    override fun bindView() {
        binding.switchVideo.tap {
            if (!PermissionManager.hasVideoPermission(context)) onRequestPermission()
        }
        binding.btnCancel.tap {
            dismiss()
        }
    }

    fun renderPermissionState(
        videoEnabled: Boolean = PermissionManager.hasVideoPermission(context),
    ) {
        binding.switchVideo.setImageResource(if (videoEnabled) R.drawable.iv_sw_on else R.drawable.iv_sw_off)
        binding.btnCancel.setText(if (videoEnabled) R.string.ok else R.string.cancel)
        binding.btnCancel.setTextColor(Color.BLACK)
        binding.btnCancel.setBackgroundResource(
            if (videoEnabled) R.drawable.bg_permission_enabled
            else R.drawable.bg_permission_disabled,
        )
    }
}
