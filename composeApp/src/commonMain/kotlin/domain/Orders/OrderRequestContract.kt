package domain.Orders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetOrderRequest(@SerialName("user_id") val userId: Int)