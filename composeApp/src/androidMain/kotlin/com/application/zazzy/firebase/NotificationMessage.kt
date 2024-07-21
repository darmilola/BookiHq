package com.application.zazzy.firebase

import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.Serializable

class NotificationMessage {
    fun getNotificationText(message: RemoteMessage): NotificationDisplayData {
        val notificationType = message.data["type"]

        when (notificationType) {
            NotificationType.MEETING_REMINDER.toPath() -> return getMeetingReminder(message)
            NotificationType.APPOINTMENT_REMINDER.toPath() -> return getAppointmentReminder(message)
            NotificationType.MEETING_STARTED.toPath() -> return getMeetingStarted(message)
            NotificationType.ORDER_DELIVERY.toPath() -> return getOrderDelivery(message)
            NotificationType.ORDER_DELIVERED.toPath() -> return getOrderDelivered(message)

            NotificationType.ORDER_BOOKING.toPath() -> return getOrderBooking(message)
            NotificationType.MEETING_BOOKING.toPath() -> return getMeetingBooking(message)
            NotificationType.APPOINTMENT_BOOKING.toPath() -> return getAppointmentBooking(message)
            NotificationType.APPOINTMENT_POSTPONED.toPath() -> return getAppointmentPostponed(message)
            NotificationType.CONNECT_BUSINESS.toPath() -> return getConnectBusiness(message)
            NotificationType.EXIT_BUSINESS.toPath() -> return getExitBusiness(message)
        }

        return NotificationDisplayData(vendorLogoUrl = "", title = "", body = "")
    }

    fun getAppointmentReminder(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val serviceType = message.data["serviceType"]
        val businessName = message.data["businessName"]
        val appointmentDay = message.data["appointmentDay"]
        val appointmentMonth = message.data["appointmentMonth"]
        val appointmentYear = message.data["appointmentYear"]
        val appointmentTime = message.data["appointmentTime"]
        val isToday = message.data["isToday"] as Boolean


        val formattedCustomerName = "<b>$customerName</b>"
        val formattedServiceType = "<b>$serviceType</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val formattedDate = "<b>$appointmentMonth, $appointmentDay</b>"
        val formattedAppointmentTime = "<b>\uD83D\uDD5E$appointmentTime</b>"
        val title = "Reminder"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        var body = ""

        if (isToday) {
            body =
                "Hi $formattedCustomerName your $formattedServiceType appointment at $formattedBusinessName is today at $formattedAppointmentTime d'ont forget to bring your cat\uD83D\uDE39\uD83D\uDE39"
        } else {
            body =
                "Hi $formattedCustomerName your $formattedServiceType appointment at $formattedBusinessName is on $formattedDate at $formattedAppointmentTime d'ont forget to bring your cat\uD83D\uDE39\uD83D\uDE39"
        }

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    fun getMeetingReminder(message: RemoteMessage): NotificationDisplayData {

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
        val isToday = message.data["isToday"] as Boolean
        val title = "Reminder"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        var body = ""


        if (isToday) {
            body =
                "Hi $formattedCustomerName your Consultation with $formattedBusinessName is today at $formattedAppointmentTime"
        } else {
            body =
                "Hi $formattedCustomerName your Consultation with $formattedBusinessName is on $formattedDate at $formattedAppointmentTime"
        }

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )

    }


    fun getMeetingStarted(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedBusinessName = "<b>$businessName\uD83D\uDC86</b>"
        val title = "Consultation"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        val body =  "Hi $formattedCustomerName your Consultation with $formattedBusinessName has been started"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )

    }

    fun getOrderDelivery(message: RemoteMessage): NotificationDisplayData {

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

    fun getOrderDelivered(message: RemoteMessage): NotificationDisplayData {
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


    // vendor notification

    fun getOrderBooking(message: RemoteMessage): NotificationDisplayData {
        val customerName = message.data["customerName"]
        val title = "Order Update"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        val body =  "$customerName has just placed an Order"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    fun getMeetingBooking(message: RemoteMessage): NotificationDisplayData {
        val customerName = message.data["customerName"]
        val meetingDay = message.data["meetingDay"]
        val meetingMonth = message.data["meetingMonth"]
        val meetingYear = message.data["meetingYear"]
        val meetingTime = message.data["meetingTime"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedDate = "<b>$meetingMonth, $meetingDay</b>"
        val formattedAppointmentTime = "<b>\uD83D\uDD5E$meetingTime</b>"
        val title = "Booking Update"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        var body = ""

        body = "$formattedCustomerName has scheduled a consultation meeting on $formattedDate by $formattedAppointmentTime"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    fun getAppointmentBooking(message: RemoteMessage): NotificationDisplayData {

        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]
        val appointmentDay = message.data["appointmentDay"]
        val appointmentMonth = message.data["appointmentMonth"]
        val appointmentYear = message.data["appointmentYear"]
        val appointmentTime = message.data["appointmentTime"]
        val serviceType = message.data["serviceType"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedDate = "<b>$appointmentMonth, $appointmentDay</b>"
        val formattedAppointmentTime = "<b>\uD83D\uDD5E$appointmentTime</b>"
        val formattedServiceType = "<b>$serviceType</b>"
        val title = "New Appointment"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        var body = ""

        body =
            "$formattedCustomerName has just booked a $formattedServiceType Appointment on $formattedDate by $formattedAppointmentTime"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    fun getAppointmentPostponed(message: RemoteMessage): NotificationDisplayData {
        val customerName = message.data["customerName"]
        val businessName = message.data["businessName"]
        val appointmentDay = message.data["appointmentDay"]
        val appointmentMonth = message.data["appointmentMonth"]
        val appointmentYear = message.data["appointmentYear"]
        val appointmentTime = message.data["appointmentTime"]
        val serviceType = message.data["serviceType"]

        val formattedCustomerName = "<b>$customerName</b>"
        val formattedDate = "<b>$appointmentMonth, $appointmentDay</b>"
        val formattedAppointmentTime = "<b>\uD83D\uDD5E$appointmentTime</b>"
        val formattedServiceType = "<b>$serviceType</b>"
        val title = "Booking Update"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        var body = "$formattedCustomerName has just postponed $formattedServiceType Appointment to $formattedDate by $formattedAppointmentTime"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    fun getConnectBusiness(message: RemoteMessage): NotificationDisplayData {
        val customerName = message.data["customerName"]
        val title = "New Customer"
        val vendorLogoUrl = message.data["vendorLogoUrl"]

        val body =  "$customerName has just Joined your Shop"

        return NotificationDisplayData(
            vendorLogoUrl = vendorLogoUrl.toString(),
            title = title,
            body = body
        )
    }

    fun getExitBusiness(message: RemoteMessage): NotificationDisplayData {
        val title = "Customer Exit"
        val vendorLogoUrl = message.data["vendorLogoUrl"]
        val exitReason = message.data["exitReason"]

        val formattedReson = "<b>$exitReason</b>"

        val body = "A customer has just left reason: $formattedReson"

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
    data class SendNotificationData(
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
        val isToday: Boolean = false,
        val exitReason: String = "",
        val vendorLogoUrl: String = "")

    @Serializable
    data class message(val token: String, val SendNotificationData: SendNotificationData)

    @Serializable
    data class rootMessage(val message: message)
}

