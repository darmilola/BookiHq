package com.application.bookihq.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.application.bookihq.MainActivity
import com.application.bookihq.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random


class AppFirebaseMessagingService : FirebaseMessagingService() {
    private var notificationManager: NotificationManager? = null
    override  fun onMessageReceived(message: RemoteMessage) {
        print("Message Received $message")
        val notificationId: Int = Random().nextInt(60000)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val notificationData = NotificationMessage().getNotificationText(message)
        val actionTitle = NotificationMessage().getActionTitle(message)
        val builder = createNotificationBuilder(notificationData,actionTitle)
        notificationManager!!.notify(notificationId, builder.build())
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun createNotificationBuilder(notificationDisplayData: NotificationMessage.NotificationDisplayData, actionTitle: String): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
        }
        val activityIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder =  NotificationCompat.Builder(this, Channel.ADMIN_CHANNEL_ID.toPath())
                .setSmallIcon(R.drawable.main_icon)
                .setContentTitle(notificationDisplayData.title)
                .setStyle(NotificationCompat.BigTextStyle().bigText(notificationDisplayData.body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.main_icon, actionTitle, pendingIntent)
                .setSound(defaultSoundUri)

        return builder
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannel: NotificationChannel
        val adminChannelName: CharSequence = Channel.NAME.toPath()
        val adminChannelDescription = Channel.DESCRIPTION.toPath()
        adminChannel = NotificationChannel(
            Channel.ADMIN_CHANNEL_ID.toPath(),
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