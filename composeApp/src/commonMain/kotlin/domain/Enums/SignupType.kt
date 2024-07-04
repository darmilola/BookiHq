package domain.Enums

enum class SignupType {
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