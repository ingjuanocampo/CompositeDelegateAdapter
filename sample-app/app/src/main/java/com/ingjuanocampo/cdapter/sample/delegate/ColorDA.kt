package com.ingjuanocampo.cdapter.sample.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ingjuanocampo.cdapter.DelegateViewHolder
import com.ingjuanocampo.cdapter.RecyclerViewType
import com.ingjuanocampo.cdapter.sample.R

data class ColorItemViewHolder( val parent: ViewGroup):
    DelegateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.delegate_color, parent, false)) {

    private val tvDelegate = itemView.findViewById<TextView>(R.id.tvDelegate)
    private val lnDelegate = itemView.findViewById<LinearLayout>(R.id.lnDelegate)

    override fun onBindViewHolder(recyclerViewType: RecyclerViewType) {
        recyclerViewType as ColorRecyclerViewType
        tvDelegate.text = recyclerViewType.text
        tvDelegate.setTextColor(itemView.resources.getColor(recyclerViewType.textColor))
        lnDelegate.setBackgroundColor(itemView.resources.getColor(recyclerViewType.bkg))
    }

}

data class ColorRecyclerViewType(val text: String, val textColor: Int, val bkg: Int):
    RecyclerViewType {
    override fun getDelegateId(): Int {
        return text.hashCode()
    }

    override fun getViewType(): Int = 1
}