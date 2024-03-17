package domain.Products

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetProductCategoryRequest(@SerialName("vendor_id") val vendorId: Int,
                                     @SerialName("user_id") val userId: Int)

@Serializable
data class GetProductsInCategoryRequest(@SerialName("vendorId") val vendorId: Int,
                                        @SerialName("categoryId") val categoryId: Int)

@Serializable
data class SearchProductRequest(@SerialName("vendorId") val vendorId: Int,
                                        @SerialName("searchQuery") val searchQuery: String)