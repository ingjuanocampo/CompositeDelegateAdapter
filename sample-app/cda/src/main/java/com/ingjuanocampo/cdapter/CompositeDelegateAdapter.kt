package com.ingjuanocampo.cdapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class CompositeDelegateAdapter(private val delegateCapacity: Int) : RecyclerView.Adapter<DelegateViewHolder>() {

    private var delegateAdapters: SparseArrayCompat<(ViewGroup) -> DelegateViewHolder> = SparseArrayCompat(delegateCapacity)
    var items: ArrayList<RecyclerViewType> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateViewHolder {
        return delegateAdapters[viewType]?.invoke(parent)!!
    }

    override fun onBindViewHolder(holder: DelegateViewHolder, position: Int) {
        val viewType = items[position]
        holder.onBindViewHolder(viewType)
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    override fun getItemCount() = items.size

    open fun addItems(itemsToInsert: List<RecyclerViewType>) {
        val originalItems = items.toMutableList()
        if (itemsToInsert.isNullOrEmpty().not()) {
            items.addAll(itemsToInsert)
        }
        val diffResult = DiffUtil.calculateDiff(DelegateDiffCallback(originalItems, items))
        diffResult.dispatchUpdatesTo(this)
    }


    open fun addItem(itemToAdd: RecyclerViewType) {
        val list = ArrayList<RecyclerViewType>()
        list.add(itemToAdd)
        addItems(list)
    }

    fun addDelegate(id: Int, delegate: (ViewGroup) -> DelegateViewHolder) {
        delegateAdapters.put(id, delegate)
    }
}