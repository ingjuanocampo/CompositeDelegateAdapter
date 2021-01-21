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

    fun addNewItems(itemsToInsert: List<RecyclerViewType>) {
        val originalItems = items.toMutableList()
        if (itemsToInsert.isNullOrEmpty().not()) {
            items.addAll(itemsToInsert)
        }
        dispatchUpdates(originalItems, items)
    }

    private fun dispatchUpdates(originalItems: MutableList<RecyclerViewType>, items: java.util.ArrayList<RecyclerViewType>) {
        val diffResult = DiffUtil.calculateDiff(DelegateDiffCallback(originalItems, items))
        diffResult.dispatchUpdatesTo(this)
    }

    fun addNewItem(itemToAdd: RecyclerViewType) = addNewItems(arrayListOf(itemToAdd))

    fun deleteItems(itemsToRemove: List<RecyclerViewType>) {
        val originalItems = items.toMutableList()
        if (itemsToRemove.isNullOrEmpty().not()) {
            items.removeAll(itemsToRemove)
        }
        dispatchUpdates(originalItems, items)
    }

    fun deleteItem(itemToRemove: RecyclerViewType) = deleteItems(arrayListOf(itemToRemove))

    fun updateItem(item: RecyclerViewType) = updateItems(arrayListOf(item))

    fun updateItems(itemsToUpdate: List<RecyclerViewType>) {
        val originalItems = items.toMutableList()
        if (itemsToUpdate.isNullOrEmpty().not()) {
            items.clear()
            items.addAll(itemsToUpdate)
        } else items.clear()
        dispatchUpdates(originalItems, items)
    }

    fun clearAll() = updateItems(emptyList())

    fun appendDelegate(viewType: Int, delegate: (ViewGroup) -> DelegateViewHolder) {
        delegateAdapters.put(viewType, delegate)
    }
}