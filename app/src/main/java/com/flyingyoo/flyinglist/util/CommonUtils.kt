package com.flyingyoo.flyinglist.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager


object CommonUtils {

    class DisplayUtil {
        companion object {
            private var statusBarHeight = 0
            private var deviDensity : Float = 0f
            private var deviceDensityDpi : Int = 0
            private var deviceWidthPixels : Int = 0
            private var deviceHeightPixels : Int = 0
            private var displayMetrics : DisplayMetrics = DisplayMetrics()

//            private var sScreenSize: Point? = null
//            private var sScreenUseableSize: Point? = null
//            private var sSoftKeyHeight = -1

            fun getStatusBarHeight(activity : Activity) :Int {
                if (statusBarHeight == 0) {
                    val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
                    if (resourceId > 0) statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
                }
                return statusBarHeight
            }

            fun dpToPx(dp : Int, context : Context): Int {
                val density = context.resources.displayMetrics.density
                return Math.round(dp.toFloat() * density)
            }

            fun setDisplayInfo(activity : Activity) {
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                deviDensity = displayMetrics.density
                deviceDensityDpi = displayMetrics.densityDpi
                deviceHeightPixels = displayMetrics.heightPixels
                deviceWidthPixels = displayMetrics.widthPixels
                DLog.i("deviceDensity = $deviDensity / deviceDensityDpi = $deviceDensityDpi / deviceWidthPixels = $deviceWidthPixels / deviceHeightPixels = $deviceWidthPixels")
            }

            fun getDeviceDensity(activity : Activity) : Float{
                if(deviDensity == 0f) setDisplayInfo(activity)
                return deviDensity
            }

            fun getDeviceDensityDpi(activity : Activity) : Int{
                if(deviceDensityDpi == 0) setDisplayInfo(activity)
                return deviceDensityDpi
            }

            fun getDeviceWidthPixel(activity : Activity) : Int{
                if(deviceWidthPixels == 0) setDisplayInfo(activity)
                return deviceWidthPixels
            }

            fun getDeviceHeightPixel(activity : Activity) : Int{
                if(deviceHeightPixels == 0) setDisplayInfo(activity)
                return deviceHeightPixels
            }
        }
    }

    /**
     * use to decorate substring on string...
     */
    class StringUtil {
        companion object {
            fun getDecoratedString(string: String, targetString: String, targetColor: Int, boldFont: Boolean, addUnderLine: Boolean): SpannableString {
                val spnString = SpannableString(string)
                return try {
                    if (string.contains(targetString)) {
                        val targetStartIndex = string.indexOf(targetString)
                        val targetEndIndex = targetStartIndex + targetString.length
                        spnString.setSpan(ForegroundColorSpan(targetColor), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        if (boldFont) spnString.setSpan(StyleSpan(Typeface.BOLD), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        if (addUnderLine) spnString.setSpan(UnderlineSpan(), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    spnString
                }
                catch (e : Exception) {
                    DLog.printStackTrace(e)
                    spnString
                }
            }

            fun getDecoratedStrings(string: String, targetString: Array<String>, targetColor: Int, boldFont: Boolean, addUnderLine: Boolean): SpannableString {
                val spnStrings = SpannableString(string)
                return try {
                    for (i in targetString.indices) {
                        if (string.contains(targetString[i])) {
                            val targetStartIndex = string.indexOf(targetString[i])
                            val targetEndIndex = targetStartIndex + targetString[i].length
                            spnStrings.setSpan(ForegroundColorSpan(targetColor), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            if(boldFont) spnStrings.setSpan(StyleSpan(Typeface.BOLD), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            if (addUnderLine) spnStrings.setSpan(UnderlineSpan(), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    }
                    spnStrings
                } catch (e: Exception) {
                    DLog.printStackTrace(e)
                    spnStrings
                }

            }
        }
    }

    /**
     * use to force soft keyboard visibility
     */
    class SoftKeyBoardUtil {
        companion object {
            fun hideKeyboard(context: Context, view: View) {
                try {
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                } catch (e: Exception) {
                    DLog.printStackTrace(e)
                }
            }

            fun showKeyboard(context: Context, view: View) {
                try {
                    view.requestFocus()
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(view, 0)
                } catch (e: Exception) {
                    DLog.printStackTrace(e)
                }
            }
        }
    }

    /**
     * use to show a dialog easily.
     */
    class DialogUtil {
        companion object {
            fun showDialog(
                context: Context,
                msg: String,
                cancelable: Boolean,
                okBtnTxt: String,
                okListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setMessage(msg)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }

            fun showDialog(
                context: Context,
                msg: Int,
                cancelable: Boolean,
                okBtnTxt: Int,
                okListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setMessage(msg)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }

            fun showDialog(
                context: Context,
                msg: String,
                cancelable: Boolean,
                okBtnTxt: String,
                okListener: DialogInterface.OnClickListener,
                cancelBtnTxt: String,
                cancelListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setMessage(msg)
                    .setNegativeButton(cancelBtnTxt, cancelListener)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }

            fun showDialog(
                context: Context,
                msg: Int,
                cancelable: Boolean,
                okBtnTxt: Int,
                okListener: DialogInterface.OnClickListener,
                cancelBtnTxt: Int,
                cancelListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setMessage(msg)
                    .setNegativeButton(cancelBtnTxt, cancelListener)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }

            fun showDialog(
                context: Context,
                title: String,
                msg: String,
                cancelable: Boolean,
                okBtnTxt: String,
                okListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }

            fun showDialog(
                context: Context,
                title: Int,
                msg: Int,
                cancelable: Boolean,
                okBtnTxt: Int,
                okListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }

            fun showDialog(
                context: Context,
                title: Int,
                msg: Int,
                cancelable: Boolean,
                okBtnTxt: Int,
                okListener: DialogInterface.OnClickListener,
                cancelBtnTxt: Int,
                cancelListener: DialogInterface.OnClickListener
            ): AlertDialog {
                return AlertDialog.Builder(context)
                    .setCancelable(cancelable)
                    .setTitle(title)
                    .setMessage(msg)
                    .setNegativeButton(cancelBtnTxt, cancelListener)
                    .setPositiveButton(okBtnTxt, okListener)
                    .show()
            }
        }
    }

}