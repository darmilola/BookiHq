package domain.Enums

enum class CardType {
    VISA,
    MASTERCARD,
    AMEX,
    UNKNOWN;

    fun toPath() = when (this) {
        VISA -> "visa"
        MASTERCARD -> "mastercard"
        AMEX -> "amex"
        UNKNOWN -> "unknown"
    }
}
