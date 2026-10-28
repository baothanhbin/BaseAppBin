package com.example.basekotlin.ui.intro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.databinding.ItemIntroBinding
import com.example.basekotlin.model.IntroModel

class IntroAdapter : BaseAdapter<IntroModel, ItemIntroBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemIntroBinding {
        return ItemIntroBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<IntroModel>) {
        submitData(newList)
    }

    override fun setData(binding: ItemIntroBinding, item: IntroModel, layoutPosition: Int) {
        binding.tvTitle.text = context!!.getString(item.title)
        binding.tvContent.text = context!!.getString(item.content)
        context?.let { Glide.with(it).load("file:///android_asset/${item.image}").into(binding.ivIntro) }
    }

    override fun getItemCount(): Int = listData.size

}
