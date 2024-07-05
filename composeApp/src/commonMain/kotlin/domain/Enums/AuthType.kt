package domain.Enums

enum class AuthType {
    EMAIL,
    PHONE;

    fun toPath() = when (this) {
        EMAIL -> "email"
        PHONE -> "phone"
    }

    fun toEventPropertyName() = when (this) {
        EMAIL -> "email"
        PHONE -> "phone"
    }
}