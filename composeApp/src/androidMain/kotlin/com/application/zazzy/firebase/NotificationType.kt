package com.application.zazzy.firebase

enum class NotificationType {
    MEETING_REMINDER,
    MEETING_STARTED,
    MEETING_BOOKING,
    ORDER_BOOKING,
    ORDER_DELIVERY,
    ORDER_DELIVERED,
    APPOINTMENT_BOOKING,
    APPOINTMENT_POSTPONED,
    APPOINTMENT_REMINDER,
    CONNECT_BUSINESS,
    EXIT_BUSINESS,
    APPOINTMENT_DONE,
    APPOINTMENT_CANCELLED,
    MARKETING;

    fun toPath() = when (this) {
        MARKETING -> "marketing"
        MEETING_REMINDER -> "meeting_reminder"
        MEETING_STARTED -> "meeting_started"
        MEETING_BOOKING -> "meeting_booking"
        ORDER_BOOKING -> "order_booking"
        ORDER_DELIVERY -> "order_delivery"
        ORDER_DELIVERED -> "order_delivered"
        APPOINTMENT_BOOKING -> "appointment_booking"
        APPOINTMENT_POSTPONED -> "appointment_postponed"
        APPOINTMENT_REMINDER -> "appointment_reminder"
        CONNECT_BUSINESS -> "connect_business"
        EXIT_BUSINESS -> "exit_business"
        APPOINTMENT_DONE -> "appointment_done"
        APPOINTMENT_CANCELLED -> "appointment_cancelled"
    }

    fun toEventPropertyName() = when (this) {
        MEETING_REMINDER -> "meeting_reminder"
        MEETING_STARTED -> "meeting_started"
        MEETING_BOOKING -> "meeting_booking"
        ORDER_BOOKING -> "order_booking"
        ORDER_DELIVERY -> "order_delivery"
        ORDER_DELIVERED -> "order_delivered"
        APPOINTMENT_BOOKING -> "appointment_booking"
        APPOINTMENT_POSTPONED -> "appointment_postponed"
        APPOINTMENT_REMINDER -> "appointment_reminder"
        CONNECT_BUSINESS -> "connect_business"
        EXIT_BUSINESS -> "exit_business"
        MARKETING -> "marketing"
        APPOINTMENT_DONE -> "appointment_done"
        APPOINTMENT_CANCELLED -> "appointment_cancelled"
    }
}

enum class FirebaseTopic {
    CUSTOMER_TOPIC;

    fun toPath() = when (this) {
        CUSTOMER_TOPIC -> "customer"
    }
}

enum class Channel {
    NAME,
    DESCRIPTION,
    ADMIN_CHANNEL_ID;

    fun toPath() = when (this) {
        NAME -> "Parlors Notification"
        DESCRIPTION -> "For Notifying Customers"
        ADMIN_CHANNEL_ID -> "Admin Channel"
    }
}
