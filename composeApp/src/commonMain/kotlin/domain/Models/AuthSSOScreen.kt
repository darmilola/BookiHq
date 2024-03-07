package domain.Models

enum class AuthSSOScreen {
    AUTH_LOGIN,
    AUTH_SIGNUP,
    VERIFY_OTP,
    WELCOME_SCREEN,
    COMPLETE_PROFILE;
    fun toPath() = when (this) {
        AUTH_LOGIN -> 0
        AUTH_SIGNUP -> 1
        COMPLETE_PROFILE -> 2
        VERIFY_OTP -> 3
        WELCOME_SCREEN -> 4
    }

    fun toEventPropertyName() = when (this) {
        AUTH_LOGIN -> "login"
        AUTH_SIGNUP -> "signup"
        COMPLETE_PROFILE -> "complete_profile"
        VERIFY_OTP -> "verify_otp"
        WELCOME_SCREEN -> "welcome_screen"
    }
}