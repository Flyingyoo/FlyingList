package com.flyingyoo.flyinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.base.BaseRecyclerViewAdapter
import com.flyingyoo.flyinglist.databinding.ViewListItemBinding
import com.flyingyoo.flyinglist.dto.ListItem

class RecyclerListItemAdapter : BaseRecyclerViewAdapter<ListItem, RecyclerListItemAdapter.ItemViewHolder> {

    constructor(context: Context) : super(context)

    constructor(context: Context, arrayList: MutableList<ListItem>) : super(context, arrayList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindView(holder: ItemViewHolder, position: Int) {
        holder.binding.item = getItem(position)
    }

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding : ViewListItemBinding = DataBindingUtil.bind(itemView)!!
    }
}
