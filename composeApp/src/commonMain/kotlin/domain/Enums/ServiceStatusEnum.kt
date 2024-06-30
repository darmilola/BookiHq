package domain.Enums

enum class ServiceStatusEnum {
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


enum class MeetingStatus {
    Pending,
    Done;

    fun toPath() = when (this) {
        Pending -> "Pending"
        Done -> "done"
    }

    fun toEventPropertyName() = when (this) {
        Pending -> "Pending"
        Done -> "done"
    }
}
