package domain.Enums

enum class PaymentMethod {
    CARD_PAYMENT,
    PAYMENT_ON_DELIVERY;
    fun toPath() = when (this) {
        CARD_PAYMENT -> "card_payment"
        PAYMENT_ON_DELIVERY -> "payment_on_delivery"
    }
    fun toEventPropertyName() = when (this) {
        CARD_PAYMENT -> "card_payment"
        PAYMENT_ON_DELIVERY -> "payment_on_delivery"
    }
}