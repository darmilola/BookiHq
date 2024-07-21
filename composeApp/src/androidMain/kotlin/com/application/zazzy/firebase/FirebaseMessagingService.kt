package com.application.zazzy.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.application.zazzy.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.seiko.imageloader.Bitmap
import java.util.Random


class AppFirebaseMessagingService : FirebaseMessagingService() {
    private var notificationManager: NotificationManager? = null
    private val ADMIN_CHANNEL_ID = "App Notification Channel"
    private var preferences: SharedPreferences? = null
  override  fun onMessageReceived(message: RemoteMessage) {
        val notificationId: Int = Random().nextInt(60000)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val notificationData = NotificationMessage().getNotificationText(message)
        Glide.with(applicationContext)
          .asBitmap()
          .load(notificationData.vendorLogoUrl)
          .into(object : CustomTarget<Bitmap>() {
              override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                  val builder = createNotificationBuilder(notificationData)
                  builder.setLargeIcon(getCircleBitmap(resource))
                  notificationManager!!.notify(notificationId, builder.build())
              }
              override fun onLoadCleared(placeholder: Drawable?) {}
              override fun onLoadFailed(errorDrawable: Drawable?) {
                  super.onLoadFailed(errorDrawable)
              }
          })
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        preferences = getSharedPreferences("TokenSharedPref", MODE_PRIVATE);
        val myEdit: SharedPreferences.Editor = preferences!!.edit()
        myEdit.putString("fcmToken",token)
        myEdit.apply()
    }

    private fun createNotificationBuilder(notificationDisplayData: NotificationMessage.NotificationDisplayData): NotificationCompat.Builder {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
        }
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder =  NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(com.application.zazzy.R.drawable.sample_logo)
                .setContentTitle(notificationDisplayData.title)
                .setStyle(NotificationCompat.BigTextStyle().bigText(notificationDisplayData.body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)

        return builder
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannel: NotificationChannel
        val adminChannelName: CharSequence = "App Channel Name"
        val adminChannelDescription = "App Channel Description"
        adminChannel = NotificationChannel(
            ADMIN_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(adminChannel)
        }
    }

    fun getCircleBitmap(bitmap: android.graphics.Bitmap): android.graphics.Bitmap? {
        val output: android.graphics.Bitmap
        val srcRect: Rect
        val dstRect: Rect
        val r: Float
        val width = bitmap.width
        val height = bitmap.height
        if (width > height) {
            output = android.graphics.Bitmap.createBitmap(
                height,
                height,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val left = (width - height) / 2
            val right = left + height
            srcRect = Rect(left, 0, right, height)
            dstRect = Rect(0, 0, height, height)
            r = (height / 2).toFloat()
        } else {
            output = android.graphics.Bitmap.createBitmap(
                width,
                width,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val top = (height - width) / 2
            val bottom = top + width
            srcRect = Rect(0, top, width, bottom)
            dstRect = Rect(0, 0, width, width)
            r = (width / 2).toFloat()
        }
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(r, r, r, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
        bitmap.recycle()
        return output
    }

}