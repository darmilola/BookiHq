package domain.Models

enum class AuthenticationAction {
    LOGIN,
    SIGNUP,
    LOGOUT;
    fun toPath() = when (this) {
        LOGIN -> "login"
        SIGNUP -> "signup"
        LOGOUT -> "logout"
    }

    fun toEventPropertyName() = when (this) {
        LOGIN -> "login"
        SIGNUP -> "signup"
        LOGOUT -> "logout"
    }
}
