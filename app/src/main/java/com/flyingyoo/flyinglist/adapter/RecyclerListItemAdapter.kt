package com.flyingyoo.flyinglist.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.base.BaseRecyclerViewAdapter
import com.flyingyoo.flyinglist.databinding.ViewListItemBinding
import com.flyingyoo.flyinglist.data.dto.ListItem
import com.flyingyoo.flyinglist.security.SecurityUtils
import com.flyingyoo.flyinglist.util.DLog
import java.lang.Exception

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

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ViewListItemBinding = DataBindingUtil.bind(itemView)!!
    }

    companion object {

        private const val COLOR_333333 = "#333333"
        private const val COLOR_999999 = "#999999"

        @JvmStatic
        @BindingAdapter("contents", "isCompleted")
        fun isCompleted(tv: TextView, contents: String, isCompleted: Boolean) {
            try {
                if (!isCompleted) {
                    tv.text = contents
                    tv.setTextColor(Color.parseColor(COLOR_333333))
                } else {
                    val spnString = SpannableString(contents)
                    tv.setTextColor(Color.parseColor(COLOR_999999))
                    spnString.setSpan(StrikethroughSpan(), 0, contents.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tv.text = spnString
                }
            } catch (e: Exception) {
                tv.text = contents
                DLog.printStackTrace(e)
            }
        }
    }
}
