package com.application.zazzy.firebase

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.application.zazzy.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random


class AppFirebaseMessagingService : FirebaseMessagingService() {
    private var notificationManager: NotificationManager? = null
    private val ADMIN_CHANNEL_ID = "My Notification Channel"
    private var preferences: SharedPreferences? = null
  override  fun onMessageReceived(message: RemoteMessage) {
      println(message.notification!!.body)
      notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
        }

      val intent = Intent(this, MainActivity::class.java).apply {
          flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
      val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
      val notificationId: Int = Random().nextInt(60000)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.dark_header)
                .setContentTitle(message.notification!!.title)
                .setContentText(message.notification!!.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
        notificationManager!!.notify(notificationId, notificationBuilder.build())
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