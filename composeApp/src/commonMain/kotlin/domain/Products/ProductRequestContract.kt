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
                              @SerialName("orderReference") val orderReference: Int, @SerialName("deliveryMethod") val deliveryMethod: String,
                              @SerialName("paymentMethod") val paymentMethod: String, @SerialName("orderItem_Array") val orderItems: ArrayList<OrderItemRequest>)

@Serializable
data class OrderItemRequest(@SerialName("orderReference") val orderReference: Int,
                            @SerialName("user_id") val userId: Long,
                            @SerialName("product_id") val productId: Int,
                            @SerialName("itemCount") val itemCount: Int)