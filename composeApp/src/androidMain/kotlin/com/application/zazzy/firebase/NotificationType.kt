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
    EXIT_BUSINESS;

    fun toPath() = when (this) {
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
    }
}
