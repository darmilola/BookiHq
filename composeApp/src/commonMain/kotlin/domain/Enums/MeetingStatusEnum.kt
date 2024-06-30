package domain.Enums

enum class MeetingStatusEnum {
    PENDING,
    POSTPONED,
    DONE;

    fun toPath() = when (this) {
        PENDING -> "pending"
        POSTPONED -> "postponed"
        DONE -> "done"
    }

    fun toEventPropertyName() = when (this) {
        PENDING -> "pending"
        POSTPONED -> "postponed"
        DONE -> "done"
    }
}
