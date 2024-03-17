package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VendorRecommendation(@SerialName("id") val recommendationId: Int = -1, @SerialName("vendor_id") val vendorId: Int = -1,
                                @SerialName("recommendationType") val recommendationType: String = RecommendationType.Services.toPath(),
                                @SerialName("description") val description: String = "", @SerialName("product_id") val productId: Int = -1,
                                @SerialName("service_type_id") val serviceTypeId: Int = -1, @SerialName("service_category") val serviceTypeItem: ServiceTypeItem? = null,
                                @SerialName("product") val product: Product? = null, val isSelected: Boolean = false)