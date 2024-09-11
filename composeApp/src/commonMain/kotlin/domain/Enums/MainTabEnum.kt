package domain.Enums

enum class MainTabEnum {
    HOME,
    PRODUCTS,
    PACKAGES,
    APPOINTMENT,
    MORE;

    fun toPath() = when (this) {
        HOME -> "home"
        PRODUCTS -> "shop"
        PACKAGES -> "packages"
        APPOINTMENT -> "appointment"
        MORE -> "more"
    }

    fun toEventPropertyName() = when (this) {
        HOME -> "home"
        PRODUCTS -> "shop"
        PACKAGES -> "packages"
        APPOINTMENT -> "appointment"
        MORE -> "more"
    }

    fun toPageID() = when (this) {
        HOME -> 0
        PRODUCTS -> 1
        PACKAGES -> 2
        APPOINTMENT -> 3
        MORE -> 4
    }
}
