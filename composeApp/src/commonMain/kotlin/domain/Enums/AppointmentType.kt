package domain.Enums

enum class AppointmentType {
    MEETING,
    SERVICE;

    fun toPath() = when (this) {
        MEETING -> "meeting"
        SERVICE -> "service"
    }

    fun toEventPropertyName() = when (this) {
        MEETING -> "meeting"
        SERVICE -> "service"
    }
}
