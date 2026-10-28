package com.example.basekotlin.dialog.delete

import android.content.Context
import android.view.ViewGroup
import com.example.basekotlin.base.BaseDialog
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.DialogDeleteBinding

class DeleteDialog(
    context: Context,
    private val title: String? = null,
    private val message: String? = null,
    private val onDelete: () -> Unit,
) : BaseDialog<DialogDeleteBinding>(context, true) {

    override fun setBinding(): DialogDeleteBinding {
        return DialogDeleteBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun bindView() {
        binding.tvDeleteTitle.text = title
        binding.tvDeleteMessage.text = message
        binding.btnCancel.tap { dismiss() }
        binding.btnDelete.tap {
            dismiss()
            onDelete()
        }
    }
}
