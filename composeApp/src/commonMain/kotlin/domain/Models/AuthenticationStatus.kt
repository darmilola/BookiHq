package domain.Models

enum class AuthenticationStatus {
    SUCCESS,
    FAILURE;
    fun toPath() = when (this) {
        SUCCESS -> "success"
        FAILURE -> "failure"
    }

    fun toEventPropertyName() = when (this) {
        SUCCESS -> "success"
        FAILURE -> "failure"
    }
}