package domain.Enums

enum class ServerResponse {
    SUCCESS,
    FAILURE,
    EMPTY;
    fun toPath() = when (this) {
        SUCCESS -> "success"
        FAILURE -> "failure"
        EMPTY -> "empty"
    }

    fun toEventPropertyName() = when (this) {
        SUCCESS -> "success"
        FAILURE -> "failure"
        EMPTY -> "empty"
    }
}
