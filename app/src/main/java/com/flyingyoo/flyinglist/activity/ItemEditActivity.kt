package com.flyingyoo.flyinglist.activity

import android.os.Bundle
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.base.BaseActivity
import com.flyingyoo.flyinglist.databinding.ActivityItemEditBinding

class ItemEditActivity : BaseActivity<ActivityItemEditBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_item_edit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b.activity = this
    }

    fun showDeleteDialog() {

    }

    fun deleteItem() {

    }

    fun confirmItem() {

    }

    fun exit() {
        finish()
    }
}
