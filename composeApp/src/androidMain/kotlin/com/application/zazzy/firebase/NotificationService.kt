package com.application.zazzy.firebase

import com.google.firebase.messaging.RemoteMessage
import io.dyte.media.utils.sdp.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.net.URL


class NotificationService {

    fun sendNotification(fcmToken: String, accessToken: String) {

        val notification = notification(title = "This is my Title", body = "This is the body of my notification, thank you.")
        //  val android = android(notification)
        // val ios = ios(notification)
        val message = message(token = fcmToken, notification = notification)
        val rootMessage = rootMessage(message = message)
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

    @Serializable
    data class notification(val title: String, val body: String)

    @Serializable
    data class android(val notification: notification)

    @Serializable
    data class ios(val notification: notification)

    @Serializable
    data class message(val token: String, val notification: notification)

    @Serializable
    data class rootMessage(val message: message)
}


