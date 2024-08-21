package domain.Enums

enum class OrderStatusEnum {
    PROCESSING,
    DELIVERED;
    fun toPath() = when (this) {
        PROCESSING -> "processing"
        DELIVERED -> "delivered"
    }
    fun toEventPropertyName() = when (this) {
        PROCESSING -> "processing"
        DELIVERED -> "delivered"
    }
}