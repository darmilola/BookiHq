package domain.Enums

enum class MainTabEnum {
    HOME,
    SHOP,
    SKIN_ANALYSIS,
    APPOINTMENT,
    PROFILE;

    fun toPath() = when (this) {
        HOME -> "home"
        SHOP -> "shop"
        SKIN_ANALYSIS -> "skinAnalysis"
        APPOINTMENT -> "appointment"
        PROFILE -> "profile"
    }

    fun toEventPropertyName() = when (this) {
        HOME -> "home"
        SHOP -> "shop"
        SKIN_ANALYSIS -> "skinAnalysis"
        APPOINTMENT -> "appointment"
        PROFILE -> "profile"
    }
}
