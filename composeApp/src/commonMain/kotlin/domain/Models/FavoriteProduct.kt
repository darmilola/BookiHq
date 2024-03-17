package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteProduct(@SerialName("id") val favoriteId: Int = -1, @SerialName("product_id") val productId: Int = -1,
                           @SerialName("vendor_id") val vendorId: Int = -1,@SerialName("user_id") val userId: Int = -1,
                           @SerialName("product") val product: Product = Product())