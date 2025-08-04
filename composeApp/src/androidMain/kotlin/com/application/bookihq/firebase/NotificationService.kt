package com.application.bookihq.firebase

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

    fun sendAppNotification(fcmToken: String, accessToken: String, data: NotificationMessage.data) {
        val message = NotificationMessage.message(token = fcmToken, data = data)
        val rootMessage = NotificationMessage.rootMessage(message = message)
        val jsonBody = Json.encodeToString(rootMessage)
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody: RequestBody = jsonBody.toRequestBody(mediaType)

        val thread = Thread(Runnable {
            try {
                val url = URL("https://fcm.googleapis.com/v1/projects/booki-1eacf/messages:send")
                val client = OkHttpClient()
                val request = Request.Builder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url(url)
                    .post(body = requestBody)
                    .build()
                val response: Response = client.newCall(request).execute()
                println("Response S $response")
                response.close()
            } catch (e: Exception) {
                println("Response F ${e.message}")
                e.printStackTrace()
            }
        })
        thread.start()
    }
}


