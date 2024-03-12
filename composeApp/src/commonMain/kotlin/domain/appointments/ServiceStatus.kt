package domain.appointments

enum class ServiceStatus {
    Pending,
    Ongoing,
    Done;

    fun toPath() = when (this) {
        Pending -> "pending"
        Ongoing -> "ongoing"
        Done -> "done"
    }

    fun toEventPropertyName() = when (this) {
        Pending -> "pending"
        Ongoing -> "ongoing"
        Done -> "done"
    }
}
