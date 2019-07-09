package com.flyingyoo.flyinglist.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.adapter.RecyclerListItemAdapter
import com.flyingyoo.flyinglist.base.BaseActivity
import com.flyingyoo.flyinglist.base.BaseRecyclerViewAdapter
import com.flyingyoo.flyinglist.constant.Constants
import com.flyingyoo.flyinglist.data.database.ItemDB
import com.flyingyoo.flyinglist.databinding.ActivityMainBinding
import com.flyingyoo.flyinglist.data.dto.ListItem
import com.flyingyoo.flyinglist.util.CommonUtils
import com.flyingyoo.flyinglist.util.DLog
import com.google.gson.GsonBuilder

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val IDX_ALL = 0
        private const val IDX_ACTIVE = 1
        private const val IDX_COMPLETED = 2
    }

    private lateinit var adapter: RecyclerListItemAdapter
    private var db: ItemDB? = null
    private val items: MutableList<ListItem> = mutableListOf()


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ItemDB.getInstance(context)
        b.activity = this
        b.itemCount = 0

        initRecycler()
        initFilterSpinner()
    }

    private fun initRecycler() {
        adapter = RecyclerListItemAdapter(context, items)

        adapter.onItemClickListener = object : BaseRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(context, EditItemActivity::class.java)
                startActivityForResultWithAnimation(intent, Constants.REQ_EDIT_ITEM, R.anim.anim_rise_up, R.anim.anim_no_animation)
            }
        }

        b.rvItemList.layoutManager = LinearLayoutManager(context)
        b.rvItemList.adapter = adapter
        b.rvItemList.isNestedScrollingEnabled = false
        LinearSnapHelper().attachToRecyclerView(b.rvItemList)

        getItemDB().start()
    }

    private fun initFilterSpinner() {
        b.spnFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    IDX_ALL -> {
                        Toast.makeText(context, "IDX_ALL", Toast.LENGTH_SHORT).show()
                    }

                    IDX_ACTIVE -> {
                        Toast.makeText(context, "IDX_ACTIVE", Toast.LENGTH_SHORT).show()
                    }

                    IDX_COMPLETED -> {
                        Toast.makeText(context, "IDX_COMPLETED", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun getItemDB() : Thread {
        return Thread {
            items.addAll(db!!.itemDao().getAll())
            DLog.e("접속 성공!" + GsonBuilder().setPrettyPrinting().create().toJson(db!!.itemDao().getAll()))
        }
    }

    private fun insertItem(item: ListItem) : Thread {
        return Thread {
            db!!.itemDao().insert(item)
        }
    }

    fun addItem() {
        b.clAddItem.visibility = View.VISIBLE
        b.btnAddItem.visibility = View.GONE
        b.etAddItem.requestFocus()

        CommonUtils.SoftKeyBoardUtil.showKeyboard(context, b.etAddItem)
    }

    fun showClearDialog() {
        CommonUtils.DialogUtil.showDialog(context, R.string.msg_sure_to_clear, false,
            R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
                clearCompleted()
            },
            R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            })
    }

    private fun clearCompleted() {

    }

    fun confirmItem() {
        if (b.etAddItem.text.toString().trim() == "") {
            Toast.makeText(context, R.string.msg_please_fill_content, Toast.LENGTH_SHORT).show()
            return
        }

        val item = ListItem(null, false, b.etAddItem.text.toString().trim(), 0, System.currentTimeMillis(), 0L)
        items.add(item)
        DLog.e(GsonBuilder().setPrettyPrinting().create().toJson(item))
        adapter.notifyItemInserted(adapter.itemCount - 1)
        b.etAddItem.setText("")
        b.etAddItem.requestFocus()

        insertItem(item).start()
        //b.nsvItems.fullScroll(NestedScrollView.FOCUS_DOWN)
    }

    fun cancelItem() {
        b.clAddItem.visibility = View.GONE
        b.btnAddItem.visibility = View.VISIBLE
        b.etAddItem.setText("")

        CommonUtils.SoftKeyBoardUtil.hideKeyboard(context, b.etAddItem)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
