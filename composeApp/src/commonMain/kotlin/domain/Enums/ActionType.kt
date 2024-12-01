package domain.Enums

enum class ActionType {
    DEFAULT,
    PAYMENT;
    fun toPath() = when (this) {
        DEFAULT -> "default"
        PAYMENT -> "payment"
    }
}