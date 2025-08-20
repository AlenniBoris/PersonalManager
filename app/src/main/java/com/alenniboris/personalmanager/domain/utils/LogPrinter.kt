package com.alenniboris.personalmanager.domain.utils

import android.util.Log
import com.alenniboris.personalmanager.BuildConfig

object LogPrinter {
    fun printLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            runCatching {
                Log.e(tag, message)
            }.getOrElse {
                println(
                    """
                        $tag
                        --------------
                        $message
                    """.trimIndent()
                )
            }
        }
    }
}