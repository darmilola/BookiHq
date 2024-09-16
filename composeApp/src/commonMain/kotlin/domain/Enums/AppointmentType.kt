package domain.Enums

enum class AppointmentType {
    SINGLE,
    PACKAGE;
    fun toPath() = when (this) {
        SINGLE -> "single"
        PACKAGE -> "package"
    }
}