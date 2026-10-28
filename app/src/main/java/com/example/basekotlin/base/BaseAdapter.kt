package com.example.basekotlin.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseAdapter<T, VB : ViewBinding> :
    RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {

    private var binding: VB? = null
    private val adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var submitJob: Job? = null
    private var submitGeneration = 0

    abstract fun setBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VB

    abstract fun addListData(newList: MutableList<T>)

    abstract fun setData(binding: VB, item: T, layoutPosition: Int)

    val listData: MutableList<T> = mutableListOf()
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = setBinding(LayoutInflater.from(context), parent, viewType)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    protected fun submitData(newList: List<T>) {
        val generation = ++submitGeneration
        val oldList = listData.toList()
        val nextList = newList.toList()

        submitJob?.cancel()
        submitJob = adapterScope.launch {
            val diffResult = withContext(Dispatchers.Default) {
                DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun getOldListSize(): Int = oldList.size

                    override fun getNewListSize(): Int = nextList.size

                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return areItemsTheSame(oldList[oldItemPosition], nextList[newItemPosition])
                    }

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return areContentsTheSame(oldList[oldItemPosition], nextList[newItemPosition])
                    }
                }, true)
            }

            if (generation != submitGeneration) return@launch

            listData.clear()
            listData.addAll(nextList)
            diffResult.dispatchUpdatesTo(this@BaseAdapter)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    protected fun submitDataImmediately(newList: List<T>) {
        submitJob?.cancel()
        submitGeneration++
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        submitJob?.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    protected open fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    inner class ViewHolder(val binding: VB) : BaseViewHolder<T>(binding) {

        override fun bindData(obj: T) {
            bindView(obj)
            setData(binding, obj, layoutPosition)
        }

        override fun bindView(obj: T) {
            onCLick(binding, obj, layoutPosition)
        }

    }

    open fun onCLick(binding: VB, item: T, layoutPosition: Int) {

    }
}
