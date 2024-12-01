package domain.Enums

enum class VendorEnum {
    DEFAULT_VENDOR_ID;

    fun toPath() = when (this) {
        DEFAULT_VENDOR_ID -> -1L
    }
}


enum class CustomerPaymentEnum {
    PAYMENT_EMAIL;

    fun toPath() = when (this) {
        PAYMENT_EMAIL -> "damilolaakinterinwa@gmail.com"
    }
}