package com.example.basekotlin.ui.language.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ItemLanguageBinding
import com.example.basekotlin.model.LanguageModel
class LanguageAdapter(
    var onClick: (String) -> Unit,
) :
    BaseAdapter<LanguageModel, ItemLanguageBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemLanguageBinding {
        return ItemLanguageBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<LanguageModel>) {
        submitList(newList, null)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: MutableList<LanguageModel>, selectedCode: String?) {
        listData.clear()
        listData.addAll(newList)
        applySelection(selectedCode)
        notifyDataSetChanged()
    }

    override fun setData(binding: ItemLanguageBinding, item: LanguageModel, layoutPosition: Int) {
        binding.tvLang.text = item.name
        binding.icLang.setImageResource(item.image)
        binding.variantsContainer.visibility = View.GONE
        binding.variantsContainer.removeAllViews()

        setMainItemState(binding, item.active)
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(binding: ItemLanguageBinding, item: LanguageModel, layoutPosition: Int) {
        super.onCLick(binding, item, layoutPosition)
        binding.mainLanguageLayout.tap {
            setCheck(item.code)
            onClick(item.code)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCheck(code: String?) {
        applySelection(code)
        notifyDataSetChanged()
    }

    private fun applySelection(code: String?) {
        listData.forEach { item ->
            item.active = item.code == code
        }
    }

    private fun setMainItemState(binding: ItemLanguageBinding, selected: Boolean) {
        binding.mainLanguageLayout.setBackgroundResource(
            if (selected) R.drawable.bg_language_card_selected else R.drawable.bg_language_card_normal
        )
        binding.tvLang.setTextColor(
            binding.root.context.getColor(
                if (selected) R.color.language_text_selected else R.color.white
            )
        )
    }

}
