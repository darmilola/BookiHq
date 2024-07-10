package domain.Enums

enum class ServiceLocationEnum {
    SPA,
    MOBILE;
    fun toPath() = when (this) {
        SPA -> "spa"
        MOBILE -> "home"
    }

    fun toEventPropertyName() = when (this) {
        SPA -> "spa"
        MOBILE -> "home"
    }
}
