package com.application.zazzy

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random


class AppFirebaseMessagingService : FirebaseMessagingService() {
    private var notificationManager: NotificationManager? = null
    private val ADMIN_CHANNEL_ID = "My Notification Channel"
    private var preferences: SharedPreferences? = null
  override  fun onMessageReceived(message: RemoteMessage) {
      println("I received this $message")
      println(message.data.toString())
      println(message.notification!!.body)

      notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
      println("Manager55 $notificationManager")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
            println("Am Oreo")
        }
        val notificationId: Int = Random().nextInt(60000)

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.dark_header) //a resource for your custom small icon
                .setContentTitle(message.notification!!.title) //the "title" value you sent in your notification
                .setContentText(message.notification!!.body) //ditto
                .setAutoCancel(true) //dismisses the notification on click
                .setSound(defaultSoundUri)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        preferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        val myEdit: SharedPreferences.Editor = preferences!!.edit()
        myEdit.putString("fcmToken",token)
        myEdit.apply()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannel: NotificationChannel
        val adminChannelName: CharSequence = "App Channel Name"
        val adminChannelDescription = "App Channel Description"
        adminChannel = NotificationChannel(
            ADMIN_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_LOW
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(adminChannel)
        }
    }

}