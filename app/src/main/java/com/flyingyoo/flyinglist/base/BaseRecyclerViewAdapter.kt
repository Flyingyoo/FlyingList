package com.flyingyoo.flyinglist.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var arrayList: MutableList<T>? = null
    internal var onItemClickListener: OnItemClickListener? = null
    internal var onItemLongClickListener: OnItemLongClickListener? = null

    var context: Context? = null
        private set

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, arrayList: MutableList<T>) {
        this.context = context
        this.arrayList = arrayList
    }

    fun getItem(position: Int): T? = if (arrayList == null) null else arrayList!![position]

    fun updateItems(items: List<T>) {
        if (this.arrayList == null) {
            arrayList = mutableListOf()
        }
        this.arrayList!!.clear()
        this.arrayList!!.addAll(items)

        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<T>) {
        if (this.arrayList == null) {
            this.arrayList = items
        } else {
            this.arrayList!!.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun clearItems() {
        if (arrayList != null) {
            arrayList!!.clear()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return if (arrayList == null) 0 else arrayList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(holder.itemView, position)
            }
        }
        holder.itemView.setOnLongClickListener {
            if (onItemLongClickListener != null) {
                onItemLongClickListener!!.onItemLongClick(holder.itemView, position)
            }
            false
        }
        onBindView(holder as H, position)

    }

    abstract fun onBindView(holder: H, position: Int)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }
}
