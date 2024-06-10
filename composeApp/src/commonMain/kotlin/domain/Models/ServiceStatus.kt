package domain.Models

enum class ServiceStatus {
    Pending,
    POSTPONED,
    Done;

    fun toPath() = when (this) {
        Pending -> "pending"
        POSTPONED -> "postponed"
        Done -> "done"
    }

    fun toEventPropertyName() = when (this) {
        Pending -> "pending"
        POSTPONED -> "postponed"
        Done -> "done"
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
