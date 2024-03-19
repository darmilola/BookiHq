package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(@SerialName("id") val itemId: Int? = -1, @SerialName("order_id") val orderId: Int? = -1,
                     @SerialName("product_id") val productId: Int? = -1, @SerialName("itemCount") val itemCount: Int? = -1,
                     @SerialName("product") val itemProduct: Product? = null, val isSelected: Boolean = false)