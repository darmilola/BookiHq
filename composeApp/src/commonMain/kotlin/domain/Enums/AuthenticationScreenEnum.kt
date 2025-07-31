package domain.Enums

enum class AuthenticationScreenEnum {

    WELCOME_SCREEN,
    OTP_SCREEN,
    MAIN_SCREEN,
    SIGN_UP_SCREEN,
    FORGOT_PASSWORD_SCREEN,
    RESET_PASSWORD_SCREEN;

    fun toPath() = when (this) {
        WELCOME_SCREEN -> 1
        OTP_SCREEN -> 2
        MAIN_SCREEN -> 3
        SIGN_UP_SCREEN -> 4
        FORGOT_PASSWORD_SCREEN -> 5
        RESET_PASSWORD_SCREEN -> 6
    }
}