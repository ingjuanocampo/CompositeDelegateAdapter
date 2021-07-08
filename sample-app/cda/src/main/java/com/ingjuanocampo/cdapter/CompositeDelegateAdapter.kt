package com.ingjuanocampo.cdapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

internal typealias Delegate = (ViewGroup) -> DelegateViewHolder

open class CompositeDelegateAdapter(delegateCapacity: Int) :
    RecyclerView.Adapter<DelegateViewHolder>() {

    private var delegateAdapters: SparseArrayCompat<Delegate> =
        SparseArrayCompat(delegateCapacity)
    private val items: MutableList<RecyclerViewType> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateViewHolder {
        return delegateAdapters[viewType]?.invoke(parent)!!
    }

    override fun onBindViewHolder(holder: DelegateViewHolder, position: Int) {
        val viewType = items[position]
        holder.onBindViewHolder(viewType)
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    override fun getItemCount() = items.size

    fun addNewItems(itemsToInsert: List<RecyclerViewType>) = with(items) {
        val originalItems = toMutableList()
        if (itemsToInsert.isNullOrEmpty().not()) {
            addAll(itemsToInsert)
        }
        dispatchUpdates(originalItems, this)
    }

    private fun dispatchUpdates(
        originalItems: MutableList<RecyclerViewType>,
        items: MutableList<RecyclerViewType>
    ) {
        DiffUtil.calculateDiff(DelegateDiffCallback(originalItems, items)).apply {
            dispatchUpdatesTo(this@CompositeDelegateAdapter)
        }
    }

    fun addNewItem(itemToAdd: RecyclerViewType) = addNewItems(arrayListOf(itemToAdd))

    fun deleteItems(itemsToRemove: List<RecyclerViewType>) = with(items) {
        val originalItems = toMutableList()
        if (itemsToRemove.isNullOrEmpty().not()) {
            removeAll(itemsToRemove)
        }
        dispatchUpdates(originalItems, this)
    }

    fun deleteItem(itemToRemove: RecyclerViewType) = deleteItems(arrayListOf(itemToRemove))

    fun updateItem(item: RecyclerViewType) = updateItems(arrayListOf(item))

    fun updateItems(itemsToUpdate: List<RecyclerViewType>) = with(items) {
        val originalItems = toMutableList()
        clear()
        if (itemsToUpdate.isNullOrEmpty().not()) {
            addAll(itemsToUpdate)
        }
        dispatchUpdates(originalItems, this)
    }

    fun clearAll() = updateItems(emptyList())

    fun appendDelegate(viewType: Int, delegate: Delegate) {
        delegateAdapters.put(viewType, delegate)
    }
}
