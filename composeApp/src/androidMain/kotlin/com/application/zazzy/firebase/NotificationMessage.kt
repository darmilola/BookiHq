package com.application.zazzy.firebase

import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.Serializable

class NotificationMessage {
    fun getNotificationText(message: RemoteMessage): NotificationDisplayData {

        when (message.data["type"]) {
            NotificationType.MEETING_REMINDER.toPath() -> return getMeetingReminder(message)
            NotificationType.APPOINTMENT_REMINDER.toPath() -> return getAppointmentReminder(message)
            NotificationType.MEETING_STARTED.toPath() -> return getMeetingStarted(message)
            NotificationType.ORDER_DELIVERY.toPath() -> return getOrderDelivery(message)
            NotificationType.ORDER_DELIVERED.toPath() -> return getOrderDelivered(message)

        }

        return NotificationDisplayData(vendorLogoUrl = "", title = "", body = "")
    }

    private fun getAppointmentReminder(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val serviceType = message.data["serviceType"]
        val businessName = message.data["businessName"]
        val appointmentDay = message.data["appointmentDay"]
        val appointmentMonth = message.data["appointmentMonth"]
        val appointmentYear = message.data["appointmentYear"]
        val appointmentTime = message.data["appointmentTime"]
        val isToday = message.data["isToday"]


        val formattedCustomerName = "<b>$customerName</b>"
        val formattedServiceType = "<b>$serviceType</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val formattedDate = "<b>$appointmentMonth, $appointmentDay</b>"
        val formattedAppointmentTime = "<b>\uD83D\uDD5E$appointmentTime</b>"
        val title = "Reminder"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        var body = ""

        body = if (isToday.toString() == "true") {
            "Hi $formattedCustomerName your $formattedServiceType appointment at $formattedBusinessName is today at $formattedAppointmentTime d'ont forget to bring your cat\uD83D\uDE39\uD83D\uDE39"
        } else {
            "Hi $formattedCustomerName your $formattedServiceType appointment at $formattedBusinessName is on $formattedDate at $formattedAppointmentTime d'ont forget to bring your cat\uD83D\uDE39\uD83D\uDE39"
        }

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    private fun getMeetingReminder(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]
        val meetingDay = message.data["meetingDay"]
        val meetingMonth = message.data["meetingMonth"]
        val meetingYear = message.data["meetingYear"]
        val meetingTime = message.data["meetingTime"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val formattedDate = "<b>$meetingMonth, $meetingDay</b>"
        val formattedAppointmentTime = "<b>\uD83D\uDD5E$meetingTime</b>"
        val isToday = message.data["isToday"]
        val title = "Reminder"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        var body = ""


        body = if (isToday.toString() == "true") {
            "Hi $formattedCustomerName your Consultation with $formattedBusinessName is today at $formattedAppointmentTime"
        } else {
            "Hi $formattedCustomerName your Consultation with $formattedBusinessName is on $formattedDate at $formattedAppointmentTime"
        }

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )

    }


    private fun getMeetingStarted(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val title = "Consultation"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        val body =  "Hi $formattedCustomerName your Consultation with $formattedBusinessName has started"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )

    }

    private fun getOrderDelivery(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val title = "Order Update"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        val body =  "Hi $formattedCustomerName your Order with $formattedBusinessName delivery is on the way"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )

    }

    private fun getOrderDelivered(message: RemoteMessage): NotificationDisplayData {
        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val title = "Order Update"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        val body =  "Hi $formattedCustomerName your Order with $formattedBusinessName has been delivered"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }




    data class NotificationDisplayData(
        val vendorLogoUrl: String,
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

