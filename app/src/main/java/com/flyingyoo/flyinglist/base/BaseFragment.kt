package com.flyingyoo.flyinglist.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    protected val TAG = javaClass.simpleName
    protected lateinit var b: B
    protected var c: Context? = null

    protected abstract fun getLayoutId(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        c = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return b.root
    }
}