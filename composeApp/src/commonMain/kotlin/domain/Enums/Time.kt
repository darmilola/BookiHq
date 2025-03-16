package domain.Enums

enum class Time {
    MORNING,
    AFTERNOON,
    EVENING;
    fun toPath() = when (this) {
        MORNING -> "Morning"
        AFTERNOON -> "Afternoon"
        EVENING -> "Evening"
    }
}
