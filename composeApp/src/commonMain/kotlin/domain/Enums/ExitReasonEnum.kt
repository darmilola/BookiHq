package domain.Enums

enum class ExitReasonEnum {
    BAD_THERAPIST,
    TOO_EXPENSIVE,
    NEEDED_MORE_SERVICE,
    NEED_SOMETHING_NEW,
    PREFER_NOT_SAY;
    fun toPath() = when (this) {
        BAD_THERAPIST -> "Bad Therapist"
        TOO_EXPENSIVE -> "Too Expensive"
        NEEDED_MORE_SERVICE -> "Needed More Service"
        NEED_SOMETHING_NEW -> "Need Something New"
        PREFER_NOT_SAY -> "Prefer not to say"
    }
    fun toEventPropertyName() = when (this) {
        BAD_THERAPIST -> "Bad Therapist"
        TOO_EXPENSIVE -> "Too Expensive"
        NEEDED_MORE_SERVICE -> "Needed More Service"
        NEED_SOMETHING_NEW -> "Need Something New"
        PREFER_NOT_SAY -> "Prefer not to say"
    }
}