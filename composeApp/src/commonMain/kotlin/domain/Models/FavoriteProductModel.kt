package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteProductModel(@SerialName("id") val favoriteId: Long = -1, @SerialName("product_id") val productId: Long = -1,
                                @SerialName("vendor_id") val vendorId: Long = -1, @SerialName("user_id") val userId: Long = -1,
                                @SerialName("product") val product: Product = Product())