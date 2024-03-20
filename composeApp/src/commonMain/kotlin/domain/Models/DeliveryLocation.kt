package domain.Models

enum class DeliveryLocation {
    HOME_DELIVERY,
    PICKUP;
    fun toPath() = when (this) {
        HOME_DELIVERY -> "home_delivery"
        PICKUP-> "pickup"
    }
}