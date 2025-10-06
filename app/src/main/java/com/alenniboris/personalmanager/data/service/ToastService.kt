package com.alenniboris.personalmanager.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.alenniboris.personalmanager.R

class ToastService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val messageResId = it.getIntExtra(EXTRA_MESSAGE_ID, 0)
            if (messageResId != 0) showCustomToast(messageResId)
        }
        return START_NOT_STICKY
    }

    private fun showCustomToast(messageResId: Int) {
        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.app_toast, null)

        val textView: TextView = layout.findViewById(R.id.toast_message)

        textView.text = getString(messageResId)

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    companion object {
        const val EXTRA_MESSAGE_ID = "extra_message_id"

        fun show(context: android.content.Context, messageResId: Int) {
            val intent = Intent(context, ToastService::class.java)
            intent.putExtra(EXTRA_MESSAGE_ID, messageResId)
            context.startService(intent)
        }
    }
}