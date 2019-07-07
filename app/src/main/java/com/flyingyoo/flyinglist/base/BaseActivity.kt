package com.flyingyoo.flyinglist.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 2019. 06. 18. flyingyoo
 * base of activities.
 */

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected val TAG = javaClass.simpleName
    protected val context = this
    protected lateinit var b: B

    protected abstract fun getLayoutId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, getLayoutId())
    }

    protected fun finishWithAnimation(enterAnim : Int, exitAnim : Int) {
        finish()
        overridePendingTransition(enterAnim, exitAnim)
    }

    protected fun startActivityWithAnimation(intent : Intent, enterAnim: Int, exitAnim: Int) {
        startActivity(intent)
        overridePendingTransition(enterAnim, exitAnim)
    }

    protected fun startActivityForResultWithAnimation(intent : Intent, requestCode : Int, enterAnim: Int, exitAnim: Int) {
        startActivityForResult(intent, requestCode)
        overridePendingTransition(enterAnim, exitAnim)
    }

    open fun onBtnClick(v : View?) { }
}