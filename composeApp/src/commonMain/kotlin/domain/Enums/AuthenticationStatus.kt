package domain.Enums

enum class AuthenticationStatus {
    AUTHENTICATION_SUCCESS,
    AUTHENTICATION_FAILURE;
    fun toPath() = when (this) {
        AUTHENTICATION_SUCCESS -> "success"
        AUTHENTICATION_FAILURE -> "failure"
    }

    fun toEventPropertyName() = when (this) {
        AUTHENTICATION_SUCCESS -> "success"
        AUTHENTICATION_FAILURE -> "failure"
    }
}