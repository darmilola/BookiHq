package domain.Enums

enum class AuthSSOScreenNav {
    AUTH_LOGIN,
    AUTH_SIGNUP,
    VERIFY_OTP,
    WELCOME_SCREEN,
    PHONE_SCREEN,
    CONNECT_VENDOR,
    MAIN,
    COMPLETE_PROFILE;
    fun toPath() = when (this) {
        AUTH_LOGIN -> 0
        AUTH_SIGNUP -> 1
        COMPLETE_PROFILE -> 2
        VERIFY_OTP -> 3
        WELCOME_SCREEN -> 4
        CONNECT_VENDOR -> 5
        MAIN -> 6
        PHONE_SCREEN -> 7
    }

    fun toEventPropertyName() = when (this) {
        AUTH_LOGIN -> "login"
        AUTH_SIGNUP -> "signup"
        COMPLETE_PROFILE -> "complete_profile"
        VERIFY_OTP -> "verify_otp"
        WELCOME_SCREEN -> "welcome_screen"
        CONNECT_VENDOR -> "connect_vendor"
        MAIN -> "main"
        PHONE_SCREEN -> "phone_screen"
    }
}