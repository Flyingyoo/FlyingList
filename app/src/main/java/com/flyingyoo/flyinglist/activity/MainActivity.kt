package com.flyingyoo.flyinglist.activity

import android.os.Bundle
import com.flyingyoo.flyinglist.R
import com.flyingyoo.flyinglist.base.BaseActivity
import com.flyingyoo.flyinglist.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b.activity = this
    }
}
