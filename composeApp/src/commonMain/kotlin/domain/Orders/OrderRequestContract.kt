package domain.Orders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetOrderRequest(@SerialName("user_id") val userId: Long)
@Serializable
data class AddProductReviewRequest(@SerialName("user_id") val userId: Long, @SerialName("product_id") val productId: Long,
                                   @SerialName("reviewText") val reviewText: String)