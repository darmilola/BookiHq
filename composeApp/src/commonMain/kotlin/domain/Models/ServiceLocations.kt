package domain.Models

enum class ServiceLocation {
    Spa,
    Home;
    fun toPath() = when (this) {
        Spa -> "spa"
        Home -> "home"
    }

    fun toEventPropertyName() = when (this) {
        Spa -> "spa"
        Home -> "home"
    }
}
