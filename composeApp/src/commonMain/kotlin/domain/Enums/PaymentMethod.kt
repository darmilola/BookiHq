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

fun getPaymentMethodDisplayName(paymentMethod: String): String{
    when(paymentMethod){
        PaymentMethod.CARD_PAYMENT.toPath() -> return "Card Payment"
        PaymentMethod.PAYMENT_ON_DELIVERY.toPath() -> return "Pay On Delivery"
        else -> return "Pay On Delivery"
    }
}