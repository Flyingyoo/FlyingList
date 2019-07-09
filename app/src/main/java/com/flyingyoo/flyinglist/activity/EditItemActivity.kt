package com.flyingyoo.flyinglist.activity

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.text.SimpleDateFormat

class EditItemActivity : BaseActivity<ActivityEditItemBinding>() {

    private var db: ItemDB? = null

    private var itemId: Int = 0

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
        deleteItemFromDB(b.item!!)
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
            b.item = db!!.itemDao().getItem(itemId)
            DLog.e(GsonBuilder().setPrettyPrinting().create().toJson(b.item))
            runOnUiThread { setMetaData(b.item!!) }
        }.start()
    }

    private fun setMetaData(item: ListItem) {

        b.etContent.setText(b.item!!.contents)

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createString = getString(R.string.edit_created_time) + " " + format.format(item.createTime)
        val editString = getString(R.string.edit_edited_time) + " " + format.format(item.editedTime)
        b.tvCreateDate.text = createString
        b.tvEditDate.text = editString

        val lengthTxt = "(${item.contents!!.length}/300)"
        b.tvLength.text = lengthTxt
        b.etContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val lengthTxt = "(${b.etContent.text.length}/300)"
                b.tvLength.text = lengthTxt
            }
        })

        b.etContent.setSelection(b.etContent.length())
    }

    private fun deleteItemFromDB(item: ListItem) = Thread { db!!.itemDao().delete(item) }.start()

    private fun updateFromDB(item: ListItem) {

        Thread { db!!.itemDao().update(item) }.start()
    }

}
