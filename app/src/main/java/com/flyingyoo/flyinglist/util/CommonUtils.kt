package com.flyingyoo.flyinglist.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager


object CommonUtils {
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