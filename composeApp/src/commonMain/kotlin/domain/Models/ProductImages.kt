package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductImages(@SerialName("id") val id: Int?, @SerialName("product_id") val serviceId: Int?, @SerialName("imageUrl") val imageUrl: String = "")