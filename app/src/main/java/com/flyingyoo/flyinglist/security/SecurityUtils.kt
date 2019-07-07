package com.flyingyoo.flyinglist.security

import android.app.Activity
import android.app.AlertDialog
import android.os.Environment
import android.text.TextUtils
import android.util.Base64
import java.io.File
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object SecurityUtils {

    class RootingCheck {
        companion object {
            private const val MSG_ROOTING_CHECK_TRUE = "This device is turned out to be OS modulated. Quit the application."

            private val ROOT_PATH = Environment.getExternalStorageDirectory().toString() + ""
            private const val ROOTING_PATH_1 = "/su/bin/su"
            private const val ROOTING_PATH_2 = "/su/xbin/su"
            private const val ROOTING_PATH_3 = "/su/bin/.user/.su"
            private const val ROOTING_PATH_4 = "/system/xbin/su"
            private const val ROOTING_PATH_5 = "/system/bin/su"
            private const val ROOTING_PATH_6 = "/system/bin/.user/.su"
            private const val ROOTING_PATH_7 = "/dev/com.noshufou.android.su"
            private const val ROOTING_PATH_8 = "/data/data/com.tegrak.lagfix"
            private const val ROOTING_PATH_9 = "/data/data/eu.chainfire.supersu"
            private const val ROOTING_PATH_10 = "/data/data/com.noshufou.android.su"
            private const val ROOTING_PATH_11 = "/data/data/com.jrummy.root.browserfree"
            private const val ROOTING_PATH_12 = "/system/app/Superuser.apk/"
            private const val ROOTING_PATH_13 = "/data/app/com.tegrak.lagfix.apk"
            private const val ROOTING_PATH_14 = "/data/app/eu.chainfire.supersu.apk"
            private const val ROOTING_PATH_15 = "/data/app/com.noshufou.android.su.apk"
            private const val ROOTING_PATH_16 = "/data/app/com.jrummy.root.browserfree.apk"

            private const val ROOTING_APP_1 = "com.tegrak.lagfix"
            private const val ROOTING_APP_2 = "eu.chainfire.supersu"
            private const val ROOTING_APP_3 = "com.noshufou.android.su"
            private const val ROOTING_APP_4 = "com.jrummy.root.browserfree"
            private const val ROOTING_APP_5 = "com.jrummy.busybox.installer"
            private const val ROOTING_APP_6 = "me.blog.markan.UnRooting"
            private const val ROOTING_APP_7 = "com.formyhm.hideroot"

            private val ROOT_FILES_PATH = arrayOf(
                ROOT_PATH + ROOTING_PATH_1,
                ROOT_PATH + ROOTING_PATH_2,
                ROOT_PATH + ROOTING_PATH_3,
                ROOT_PATH + ROOTING_PATH_4,
                ROOT_PATH + ROOTING_PATH_5,
                ROOT_PATH + ROOTING_PATH_6,
                ROOT_PATH + ROOTING_PATH_7,
                ROOT_PATH + ROOTING_PATH_8,
                ROOT_PATH + ROOTING_PATH_9,
                ROOT_PATH + ROOTING_PATH_10,
                ROOT_PATH + ROOTING_PATH_11,
                ROOT_PATH + ROOTING_PATH_12,
                ROOT_PATH + ROOTING_PATH_13,
                ROOT_PATH + ROOTING_PATH_14,
                ROOT_PATH + ROOTING_PATH_15,
                ROOT_PATH + ROOTING_PATH_16
            )

            private val ROOTING_APPS = arrayOf(
                ROOTING_APP_1,
                ROOTING_APP_2,
                ROOTING_APP_3,
                ROOTING_APP_4,
                ROOTING_APP_5,
                ROOTING_APP_6,
                ROOTING_APP_7
            )

            fun checkDevice(activity: Activity): Boolean {
                var isRooted = false
                try {
                    Runtime.getRuntime().exec("su")
                    isRooted = true
                    AlertDialog.Builder(activity)
                        .setMessage(MSG_ROOTING_CHECK_TRUE)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok) { di, i -> activity.finishAffinity() }
                        .show()
                } catch (e: Exception) {
                    if (checkRootingFiles(createFiles(ROOT_FILES_PATH)) || checkRootingApp(ROOTING_APPS, activity)) {
                        isRooted = true
                        AlertDialog.Builder(activity)
                            .setMessage(MSG_ROOTING_CHECK_TRUE)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok) { di, i -> activity.finishAffinity() }
                            .show()
                    }
                }
                return isRooted
            }

            /**
             * 루팅파일 의심 Path를 가진 파일들을 생성 한다.
             */
            private fun createFiles(sfiles: Array<String>): Array<File?> {
                val rootingFiles = arrayOfNulls<File>(sfiles.size)
                for (i in sfiles.indices) {
                    rootingFiles[i] = File(sfiles[i])
                }
                return rootingFiles
            }

            /**
             * 루팅파일 여부를 확인 한다.
             */
            private fun checkRootingFiles(file: Array<File?>): Boolean {
                var result = false
                for (f in file) {
                    if (f != null && f.exists() && f.isFile) {
                        result = true
                        break
                    } else {
                        result = false
                    }
                }
                return result
            }

            // 루팅 앱 설치 확인
            private fun checkRootingApp(rootApps: Array<String>, activity: Activity): Boolean {
                var result = false
                for (rootApp in rootApps) {
                    val startLink = activity.packageManager.getLaunchIntentForPackage(rootApp)
                    if (null != startLink) {
                        result = true
                        break
                    } else {
                        result = false
                    }
                }
                return result
            }
        }
    }

    class AES256Cipher {
        companion object {
            private const val SECRET_KEY = "38173156422918137132636764468992"
            private val IV = SECRET_KEY.substring(0, 16)
            private const val ALGORITHM = "AES"
            private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

            @Throws(
                java.io.UnsupportedEncodingException::class,
                NoSuchAlgorithmException::class,
                NoSuchPaddingException::class,
                InvalidAlgorithmParameterException::class,
                InvalidKeyException::class,
                IllegalBlockSizeException::class,
                BadPaddingException::class
            )
            fun aesEncode(str: String): String {
                if (TextUtils.isEmpty(str)) return ""
                val keyData = SECRET_KEY.toByteArray()
                val secretKey = SecretKeySpec(keyData, ALGORITHM)
                val c = Cipher.getInstance(TRANSFORMATION)
                c.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(IV.toByteArray()))
                val encrypted = c.doFinal(str.toByteArray(StandardCharsets.UTF_8))
                return String(Base64.encode(encrypted, 0))
            }

            @Throws(
                java.io.UnsupportedEncodingException::class,
                NoSuchAlgorithmException::class,
                NoSuchPaddingException::class,
                InvalidAlgorithmParameterException::class,
                InvalidKeyException::class,
                IllegalBlockSizeException::class,
                BadPaddingException::class
            )
            fun aesDecode(str: String): String {
                if (TextUtils.isEmpty(str)) return ""
                val keyData = SECRET_KEY.toByteArray()
                val secretKey = SecretKeySpec(keyData, ALGORITHM)
                val c = Cipher.getInstance(TRANSFORMATION)
                c.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(IV.toByteArray(StandardCharsets.UTF_8)))
                val byteStr = Base64.decode(str.toByteArray(), 0)
                return String(c.doFinal(byteStr), StandardCharsets.UTF_8)
            }
        }
    }
}