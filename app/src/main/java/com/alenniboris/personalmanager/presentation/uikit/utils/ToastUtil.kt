package com.alenniboris.personalmanager.presentation.uikit.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.alenniboris.personalmanager.R

object ToastUtil {
    fun show(context: Context, @StringRes resId: Int) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.app_toast, null)
        layout.findViewById<TextView>(R.id.toast_message).text = context.getString(resId)

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}