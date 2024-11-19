package domain.Enums

enum class ExitReasonEnum {
    BAD_THERAPIST,
    TOO_EXPENSIVE,
    NEEDED_MORE_SERVICE,
    PREFER_NOT_SAY;
    fun toPath() = when (this) {
        BAD_THERAPIST -> "Bad Therapist"
        TOO_EXPENSIVE -> "Too Expensive"
        NEEDED_MORE_SERVICE -> "Needed More Service"
        PREFER_NOT_SAY -> "Prefer not to say"
    }
    fun toEventPropertyName() = when (this) {
        BAD_THERAPIST -> "Bad Therapist"
        TOO_EXPENSIVE -> "Too Expensive"
        NEEDED_MORE_SERVICE -> "Needed More Service"
        PREFER_NOT_SAY -> "Prefer not to say"
    }
}