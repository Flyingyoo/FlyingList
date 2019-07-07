package com.flyingyoo.flyinglist.base

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

import java.lang.Exception

/**
 * 2019. 06. 18. flyingyoo
 */

class BaseApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        DEBUG = isDebuggable(this)

    }

    companion object {
        /**
         * 2019. 06. 19. flyingyoo
         * logic for checking build mode
         */
        var DEBUG = false

        private fun isDebuggable(context : Context) : Boolean {
            var debuggable = false
            val pm = context.packageManager
            try {
                val appInfo = pm.getApplicationInfo(context.packageName, 0)
                debuggable = (0 != (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE))
            }
            catch (e : Exception) {

            }
            return debuggable
        }
    }
}