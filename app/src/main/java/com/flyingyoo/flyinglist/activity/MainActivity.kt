package com.flyingyoo.flyinglist.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.adapter.RecyclerListItemAdapter
import com.flyingyoo.flyinglist.base.BaseActivity
import com.flyingyoo.flyinglist.databinding.ActivityMainBinding
import com.flyingyoo.flyinglist.dto.ListItem
import com.flyingyoo.flyinglist.util.CommonUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val IDX_ALL = 0
        private const val IDX_ACTIVE = 1
        private const val IDX_COMPLETED = 2
    }

    private lateinit var adapter: RecyclerListItemAdapter
    private val items: MutableList<ListItem> = arrayListOf()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b.activity = this
        b.itemCount = 0

        initRecycler()
        initFilterSpinner()

        //for test
        b.btnTest.setOnClickListener {
            startActivity(Intent(context, ItemEditActivity::class.java))
        }
    }

    private fun initRecycler() {
        items.add(ListItem("1", false, "크하하핳1", 0, 0L, 0L))
        items.add(ListItem("2", false, "크하하핳1", 0, 0L, 0L))
        items.add(ListItem("3", true, "크하하핳1", 0, 0L, 0L))
        items.add(ListItem("4", true, "크하하핳1", 0, 0L, 0L))
        items.add(ListItem("5", true, "크하하핳1", 0, 0L, 0L))

        adapter = RecyclerListItemAdapter(context, items)

        b.rvItemList.layoutManager = LinearLayoutManager(context)
        b.rvItemList.adapter = adapter
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

    fun addItem() {
        b.clAddItem.visibility = View.VISIBLE
        b.btnAddItem.visibility = View.GONE
        b.etAddItem.requestFocus()

        CommonUtils.SoftKeyBoardUtil.showKeyboard(context, b.etAddItem)
    }

    fun showClearDialog() {
        CommonUtils.DialogUtil.showDialog(context, R.string.msg_sure_to_clear, false, R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
                clearCompleted()
            }
            , R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            })
    }

    fun clearCompleted() {

    }

    fun confirmItem() {
        if (b.etAddItem.text.toString().trim() == "") {
            Toast.makeText(context, R.string.msg_please_fill_content, Toast.LENGTH_SHORT).show()
            return
        }

        items.add(ListItem("123", false, b.etAddItem.text.toString(), 0, 0, 0))
        adapter.notifyItemInserted(adapter.itemCount - 1)
        b.etAddItem.setText("")
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
