package domain.Enums

enum class PaymentMethod {
    CARD_PAYMENT;
    fun toPath() = when (this) {
        CARD_PAYMENT -> "card_payment"
    }
    fun toEventPropertyName() = when (this) {
        CARD_PAYMENT -> "card_payment"
    }
}