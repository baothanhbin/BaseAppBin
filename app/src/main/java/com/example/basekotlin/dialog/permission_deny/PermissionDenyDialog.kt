package com.example.basekotlin.dialog.permission_deny

import android.content.Context
import android.view.ViewGroup
import com.example.basekotlin.base.BaseDialog
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.DialogPermissionDenyBinding
import com.example.basekotlin.permission.openAppSettings

class PermissionDenyDialog(
    context: Context,
) : BaseDialog<DialogPermissionDenyBinding>(context, false) {

    override fun setBinding(): DialogPermissionDenyBinding {
        return DialogPermissionDenyBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun bindView() {
        binding.btnCancel.tap { dismiss() }
        binding.btnOpenSettings.tap {
            context.openAppSettings()
            dismiss()
        }
    }
}
