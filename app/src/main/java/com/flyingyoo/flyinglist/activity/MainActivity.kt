package com.flyingyoo.flyinglist.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.flyingyoo.flyinglist.security.SecurityUtils
import com.flyingyoo.flyinglist.util.*
import com.google.gson.GsonBuilder


class MainActivity : BaseActivity<ActivityMainBinding>(), SwipeCallback.OnItemSwipeListener {

    companion object {
        private const val IDX_ALL = 0
        private const val IDX_ACTIVE = 1
        private const val IDX_COMPLETED = 2
    }

    private lateinit var adapter: RecyclerListItemAdapter
    private var db: ItemDB? = null
    private val items: MutableList<ListItem> = mutableListOf()
    private var completedIndex = IDX_ALL

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SecurityUtils.RootingCheck.checkDevice(this)) {
            db = ItemDB.getInstance(context)
            b.activity = this
            b.itemCount = 0

            initInputAddItem()
            initRecycler()
            initFilterSpinner()
        }
    }

    private fun initInputAddItem() {
        b.etAddItem.setOnEditorActionListener { _, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    addItem()
                }
            }
            false
        }
    }


    private fun initRecycler() {
        adapter = RecyclerListItemAdapter(context, items)

        adapter.onItemClickListener = object : BaseRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val item = items[position]
                val intent = Intent(context, EditItemActivity::class.java)
                intent.putExtra(Constants.ID, item.id)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                increaseViewCount(item.id!!)
                startActivityForResultWithAnimation(
                    intent,
                    Constants.REQ_EDIT_ITEM,
                    R.anim.anim_rise_up,
                    R.anim.anim_no_animation
                )
            }
        }

        b.rvItemList.layoutManager = LinearLayoutManager(context)
        b.rvItemList.adapter = adapter
        b.rvItemList.isNestedScrollingEnabled = false
        LinearSnapHelper().attachToRecyclerView(b.rvItemList)

        val swipeCallback = SwipeCallback(this)
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(b.rvItemList)
    }

    private fun initFilterSpinner() {
        b.spnFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                completedIndex = i
                refreshListItems(completedIndex)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun refreshListItems(idx: Int) {
        when (idx) {
            IDX_ALL -> {
                getAll()
            }
            IDX_ACTIVE, IDX_COMPLETED -> {
                getByCompleted(idx)
            }
        }
    }

    private fun getAll() {
        Thread {
            val items = db!!.itemDao().getAll()
            b.itemCount = db!!.itemDao().getActiveCount()
            DLog.e("getAll\n" + GsonBuilder().setPrettyPrinting().create().toJson(items))
            runOnUiThread {
                adapter.updateItems(items)
            }
        }.start()
    }

    private fun getByCompleted(idx: Int) {
        val completed = idx == IDX_COMPLETED
        Thread {
            val items = db!!.itemDao().getItemByCompleted(completed)
            b.itemCount = db!!.itemDao().getActiveCount()
            DLog.e("getByCompleted\n" + GsonBuilder().setPrettyPrinting().create().toJson(items))
            runOnUiThread { adapter.updateItems(items) }
        }.start()
    }

    private fun insertToDB(item: ListItem) {
        Thread {
            db!!.itemDao().insert(item)
            refreshListItems(completedIndex)
        }.start()
    }

    private fun deleteFromDB(item: ListItem) {
        Thread {
            db!!.itemDao().delete(item)
            refreshListItems(completedIndex)
        }.start()
    }

    private fun increaseViewCount(id: Int) {
        Thread {
            db!!.itemDao().increaseViewCount(id)
        }.start()
    }

    private fun clearCompleted() {
        Thread {
            db!!.itemDao().deleteCompleted()
            refreshListItems(completedIndex)
        }.start()
    }

    fun visibleAddViews() {
        b.clAddItem.visibility = View.VISIBLE
        b.btnVisibleAdd.visibility = View.GONE
        b.etAddItem.requestFocus()

        CommonUtils.SoftKeyBoardUtil.showKeyboard(context, b.etAddItem)
    }

    fun addItem() {
        if (b.etAddItem.text.toString().trim() == "") {
            Toast.makeText(context, R.string.msg_please_fill_content, Toast.LENGTH_SHORT).show()
            return
        }

        val item = ListItem(null, false, b.etAddItem.text.toString().trim(), 0, System.currentTimeMillis(), 0L)
        insertToDB(item)
        b.etAddItem.setText("")
        CommonUtils.SoftKeyBoardUtil.hideKeyboard(context, b.etAddItem)
    }

    fun cancelItem() {
        b.clAddItem.visibility = View.GONE
        b.btnVisibleAdd.visibility = View.VISIBLE
        b.etAddItem.setText("")
        CommonUtils.SoftKeyBoardUtil.hideKeyboard(context, b.etAddItem)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constants.REQ_EDIT_ITEM -> {
                    refreshListItems(completedIndex)
                }
            }
        }
    }

    override fun onItemSwipe(position: Int) {
        CommonUtils.DialogUtil.showDialog(context,
            R.string.msg_sure_to_delete,
            false,
            R.string.delete,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
                deleteFromDB(items[position])
            },
            R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
                adapter.notifyItemChanged(position)
            })

    }
}
