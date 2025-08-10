package com.application.bookihq.firebase

import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.Serializable

class NotificationMessage {
    fun getNotificationText(message: RemoteMessage): NotificationDisplayData {

        when (message.data["type"]) {
            NotificationType.MARKETING.toPath() -> return getMarketingMessage(message)
            NotificationType.APPOINTMENT_DONE.toPath() -> return getAppointmentDoneMessage(message)
            NotificationType.APPOINTMENT_CANCELLED.toPath() -> return getAppointmentCancelledMessage(message)
        }
        return NotificationDisplayData(logoUrl = "", title = "", body = "")
    }

    fun getActionTitle(message: RemoteMessage): String {

        when (message.data["type"]) {
            NotificationType.MARKETING.toPath() -> return "Book A Session"
            NotificationType.APPOINTMENT_DONE.toPath() -> return "Add Review"
            NotificationType.APPOINTMENT_CANCELLED.toPath() -> return "Book Another Session"
        }
        return ""
    }

    private fun getMarketingMessage(message: RemoteMessage): NotificationDisplayData {

        val body = message.data["body"]
        val title = message.data["title"]
        val logoUrl = message.data["logoUrl"]

        return NotificationDisplayData(
            logoUrl = logoUrl.toString(),
            title = title!!,
            body = body!!
        )

    }

    private fun getAppointmentDoneMessage(message: RemoteMessage): NotificationDisplayData {

        val body = message.data["body"]
        val title = message.data["title"]
        val logoUrl = message.data["logoUrl"]

        return NotificationDisplayData(
            logoUrl = logoUrl.toString(),
            title = title!!,
            body = body!!
        )

    }

    private fun getAppointmentCancelledMessage(message: RemoteMessage): NotificationDisplayData {

        val body = message.data["body"]
        val title = message.data["title"]
        val logoUrl = message.data["logoUrl"]

        return NotificationDisplayData(
            logoUrl = logoUrl.toString(),
            title = title!!,
            body = body!!
        )

    }





    data class NotificationDisplayData(
        val logoUrl: String = "",
        val title: String = "",
        val body: String = ""
    )


    @Serializable
    data class data(
        val type: String,
        val businessName: String = "",
        val customerName: String = "",
        val therapistName: String = "",
        val meetingTime: String = "",
        val meetingDay: String = "",
        val meetingMonth: String = "",
        val meetingYear: String = "",
        val appointmentDay: String = "",
        val appointmentMonth: String = "",
        val appointmentYear: String = "",
        val appointmentTime: String = "",
        val serviceType: String = "",
        val isToday: String = "",
        val exitReason: String = "",
        val vendorLogoUrl: String = "")

    @Serializable
    data class message(val token: String, val data: data)

    @Serializable
    data class rootMessage(val message: message)
}

