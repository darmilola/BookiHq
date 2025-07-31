package domain.Enums

enum class OrderStatusEnum {
    PROCESSING,
    CANCELLED,
    DELIVERED;
    fun toPath() = when (this) {
        PROCESSING -> "processing"
        DELIVERED -> "delivered"
        CANCELLED -> "cancel"
    }
    fun toEventPropertyName() = when (this) {
        PROCESSING -> "processing"
        DELIVERED -> "delivered"
        CANCELLED -> "cancel"
    }
}