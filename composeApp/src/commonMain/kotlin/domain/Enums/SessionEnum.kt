package domain.Enums

enum class SessionEnum {
    MORNING,
    AFTERNOON,
    EVENING;

    fun toPath() = when (this) {
        MORNING -> "morning"
        AFTERNOON -> "afternoon"
        EVENING -> "evening"
    }

    fun toEventPropertyName() = when (this) {
        MORNING -> "morning"
        AFTERNOON -> "afternoon"
        EVENING -> "evening"
    }
}