package domain.Enums

enum class BookingStatus {
    BOOKING,
    PENDING,
    POSTPONED,
    DONE;

    fun toPath() = when (this) {
        BOOKING -> "booking"
        PENDING -> "pending"
        POSTPONED -> "postponed"
        DONE -> "done"
    }

    fun toEventPropertyName() = when (this) {
        BOOKING -> "booking"
        PENDING -> "pending"
        POSTPONED -> "postponed"
        DONE -> "done"
    }
}
