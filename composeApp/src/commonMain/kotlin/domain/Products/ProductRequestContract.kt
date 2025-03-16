package domain.Products

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class GetAllProductsRequest(@SerialName("vendorId") val vendorId: Long)

@Serializable
data class SearchProductRequest(@SerialName("vendorId") val vendorId: Long,
                                        @SerialName("searchQuery") val searchQuery: String)

@Serializable
data class CreateOrderRequest(@SerialName("vendor_id") val vendorId: Long, @SerialName("user_id") val userId: Long,
                              @SerialName("deliveryMethod") val deliveryMethod: String, @SerialName("day") val day: Int,
                              @SerialName("month") val month: Int, @SerialName("year") val year: Int,
                              @SerialName("paymentAmount") val paymentAmount: Long, @SerialName("orderItemJson") val orderItemJson: String)

@Serializable
data class OrderItemRequest(@SerialName("product_id") val productId: Long,
                            @SerialName("product_name") val productName: String,
                            @SerialName("imageUrl") val imageUrl: String,
                            @SerialName("description") val productDescription: String,
                            @SerialName("price") val price: Long,
                            @SerialName("itemCount") val itemCount: Int)

@Serializable
data class GetProductTypeRequest(
    @SerialName("vendorId") val vendorId: Long,
    @SerialName("type") val productType: String
)

@Serializable
data class AddFavoriteProductRequest(
    @SerialName("user_id") val userId: Long,
    @SerialName("product_id") val productId: Long,
    @SerialName("vendor_id") val vendorId: Long
)

@Serializable
data class RemoveFavoriteProductRequest(
    @SerialName("user_id") val userId: Long,
    @SerialName("productId") val productId: Long
)

@Serializable
data class GetFavoriteProductRequest(
    @SerialName("user_id") val userId: Long
)

@Serializable
data class GetFavoriteProductIdsRequest(
    @SerialName("user_id") val userId: Long
)