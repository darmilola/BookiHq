package domain.Enums

enum class DeliveryMethodEnum {
    PICKUP,
    MOBILE;
    fun toPath() = when (this) {
        PICKUP -> "pickup"
        MOBILE -> "mobile_delivery"
    }
    fun toEventPropertyName() = when (this) {
        PICKUP -> "pickup"
        MOBILE -> "mobile_delivery"
    }
}