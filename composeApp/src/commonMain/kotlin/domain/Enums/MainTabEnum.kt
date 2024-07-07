package domain.Enums

enum class MainTabEnum {
    HOME,
    SHOP,
    APPOINTMENT,
    PROFILE;

    fun toPath() = when (this) {
        HOME -> "home"
        SHOP -> "shop"
        APPOINTMENT -> "appointment"
        PROFILE -> "profile"
    }

    fun toEventPropertyName() = when (this) {
        HOME -> "home"
        SHOP -> "shop"
        APPOINTMENT -> "appointment"
        PROFILE -> "profile"
    }
}
