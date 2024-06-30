package domain.Enums

enum class ServiceLocationEnum {
    SPA,
    HOME,
    WORK;
    fun toPath() = when (this) {
        SPA -> "spa"
        HOME -> "home"
        WORK -> "work"
    }

    fun toEventPropertyName() = when (this) {
        SPA -> "spa"
        HOME -> "home"
        WORK -> "work"
    }
}
