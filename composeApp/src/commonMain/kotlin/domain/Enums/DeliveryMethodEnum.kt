package domain.Enums

enum class DeliveryMethodEnum {
    PICKUP,
    HOME_DELIVERY;
    fun toPath() = when (this) {
        PICKUP -> "pickup"
        HOME_DELIVERY -> "home_delivery"
    }
    fun toEventPropertyName() = when (this) {
        PICKUP -> "pickup"
        HOME_DELIVERY -> "home_delivery"
    }
}

fun getDeliveryMethodDisplayName(deliveryMethod: String): String{
    when(deliveryMethod){
        DeliveryMethodEnum.HOME_DELIVERY.toPath() -> return "Home Delivery"
        DeliveryMethodEnum.PICKUP.toPath() -> return "Pickup"
        else -> return "Pickup"
    }
}