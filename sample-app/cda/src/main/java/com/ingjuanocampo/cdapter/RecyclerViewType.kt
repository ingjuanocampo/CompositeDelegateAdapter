package com.ingjuanocampo.cdapter

interface RecyclerViewType {
    fun getDelegateId(): Int = this.hashCode()
    fun getViewType(): Int
}