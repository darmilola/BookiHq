package domain.Enums

enum class BookingStatus {
    ALL,
    BOOKING,
    PENDING,
    POSTPONED,
    DONE,
    CANCELLED;

    fun toPath() = when (this) {
        BOOKING -> "booking"
        PENDING -> "pending"
        POSTPONED -> "postponed"
        DONE -> "done"
        ALL -> "all"
        CANCELLED -> "cancel"
    }

    fun toName() = when (this) {
        BOOKING -> "Booking"
        PENDING -> "Pending"
        POSTPONED -> "Postponed"
        DONE -> "Done"
        ALL -> "All"
        CANCELLED -> "Cancelled"
    }

    fun toEventPropertyName() = when (this) {
        BOOKING -> "booking"
        PENDING -> "pending"
        POSTPONED -> "postponed"
        DONE -> "done"
        ALL -> "all"
        CANCELLED -> "cancel"
    }
}

