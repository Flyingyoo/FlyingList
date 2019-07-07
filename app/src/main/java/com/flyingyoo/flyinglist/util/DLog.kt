package com.flyingyoo.flyinglist.util


import android.util.Log
import com.flyingyoo.flyinglist.base.BaseApplication


/**
 * 2019. 06. 19. flyingyoo
 * Logs will be printed only release build mode.
 */

class DLog {
    companion object {
        const val TAG = "FLYING_LIST"
        private val DEBUG = BaseApplication.DEBUG

        @JvmStatic
        fun d(msg: String) { if (DEBUG) Log.d(TAG, msg) }

        @JvmStatic
        fun e(msg: String) { if (DEBUG) Log.e(TAG, msg) }

        @JvmStatic
        fun i(msg: String) { if (DEBUG) Log.i(TAG, msg) }

        @JvmStatic
        fun w(msg: String) { if (DEBUG) Log.w(TAG, msg) }

        @JvmStatic
        fun wtf(msg: String) { if (DEBUG) Log.wtf(TAG, msg) }

        @JvmStatic
        fun printStackTrace(e : Exception) { if (DEBUG) e.printStackTrace() }
    }
}
