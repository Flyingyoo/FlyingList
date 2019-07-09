package com.flyingyoo.flyinglist.activity

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.base.BaseActivity
import com.flyingyoo.flyinglist.constant.Constants
import com.flyingyoo.flyinglist.data.database.ItemDB
import com.flyingyoo.flyinglist.data.dto.ListItem
import com.flyingyoo.flyinglist.databinding.ActivityEditItemBinding
import com.flyingyoo.flyinglist.util.CommonUtils
import com.flyingyoo.flyinglist.util.DLog
import com.google.gson.GsonBuilder

class EditItemActivity : BaseActivity<ActivityEditItemBinding>() {

    private var db: ItemDB? = null

    private var itemId: Int = 0
    private var item: ListItem? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_edit_item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b.activity = this

        db = ItemDB.getInstance(context)
        itemId = intent.getIntExtra(Constants.ID, 0)

        getItemFromDB(itemId)
    }

    fun showDeleteDialog() {
        CommonUtils.DialogUtil.showDialog(context,
            R.string.msg_sure_to_delete,
            false,
            R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
                deleteItem()
            },
            R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        )
    }

    private fun deleteItem() {
        setResult(Activity.RESULT_OK)
        finishWithAnimation(R.anim.anim_no_animation, R.anim.anim_drop_down)
    }

    fun confirmItem() {
        if (b.etContent.text.toString().trim() == "") {
            Toast.makeText(context, R.string.msg_please_fill_content, Toast.LENGTH_SHORT).show()
            return
        }

        setResult(Activity.RESULT_OK)
        finishWithAnimation(R.anim.anim_no_animation, R.anim.anim_drop_down)
    }

    fun exit() {
        finishWithAnimation(R.anim.anim_no_animation, R.anim.anim_drop_down)
    }

    private fun getItemFromDB(itemId: Int) {
        Thread {
            item = db!!.itemDao().getItem(itemId)
            DLog.e(GsonBuilder().setPrettyPrinting().create().toJson(item))
        }.start()
    }

    private fun deleteItemFromDB(item: ListItem) = Thread { db!!.itemDao().delete(item) }.start()
    private fun updateFromDB(item: ListItem) = Thread { db!!.itemDao().update(item) }.start()

}
