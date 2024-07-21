package com.application.zazzy.firebase

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.net.URL


class NotificationService {

    fun sendAppNotification(fcmToken: String, accessToken: String, data: NotificationMessage.SendNotificationData) {
        val message = NotificationMessage.message(token = fcmToken, SendNotificationData = data)
        val rootMessage = NotificationMessage.rootMessage(message = message)
        val jsonBody = Json.encodeToString(rootMessage)

        var mediaType = "application/json".toMediaTypeOrNull()
        var requestBody: RequestBody = jsonBody.toRequestBody(mediaType)

        val thread = Thread(Runnable {
            try {
                val url = URL("https://fcm.googleapis.com/v1/projects/carediva-8e117/messages:send")
                val client = OkHttpClient()
                val request = Request.Builder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url(url)
                    .post(body = requestBody)
                    .build()
                var response: Response = client.newCall(request).execute()
            } catch (e: Exception) {
                println(e.message)
                e.printStackTrace()
            }
        })
        thread.start()
    }
}


