package domain.Enums

enum class ServiceLocationEnum {
    SPA,
    MOBILE;
    fun toPath() = when (this) {
        SPA -> "spa"
        MOBILE -> "mobile"
    }

    fun toEventPropertyName() = when (this) {
        SPA -> "spa"
        MOBILE -> "mobile"
    }
}
