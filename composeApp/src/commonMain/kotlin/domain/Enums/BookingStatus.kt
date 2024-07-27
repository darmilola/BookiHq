package domain.Enums

enum class BookingStatus  {
    PENDING,
    DONE;
    fun toPath() = when (this) {
        PENDING -> "pending"
        DONE -> "done"
    }

    fun toEventPropertyName() = when (this) {
        PENDING -> "pending"
        DONE -> "done"
    }
}
