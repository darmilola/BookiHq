package domain.Products

import domain.Models.OrderItem
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
                              @SerialName("paymentAmount") val paymentAmount: Double,
                              @SerialName("paymentMethod") val paymentMethod: String, @SerialName("orderItemJson") val orderItemJson: String)

@Serializable
data class OrderItemRequest(@SerialName("product_id") val productId: Int,
                            @SerialName("product_name") val productName: String,
                            @SerialName("imageUrl") val imageUrl: String,
                            @SerialName("description") val productDescription: String,
                            @SerialName("price") val price: Int,
                            @SerialName("itemCount") val itemCount: Int)

@Serializable
data class GetProductTypeRequest(
    @SerialName("vendorId") val vendorId: Long,
    @SerialName("type") val productType: String
)