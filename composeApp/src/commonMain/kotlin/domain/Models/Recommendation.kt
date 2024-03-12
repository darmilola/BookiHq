package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recommendation(@SerialName("id") val recommendationId: Int = -1, @SerialName("vendor_id") val vendorId: Int = -1,
                          @SerialName("recommendationType") val recommendationType: String? = null,
                          @SerialName("description") val description: String = "", @SerialName("product_id") val productId: Int = -1,
                          @SerialName("service_type_id") val serviceTypeId: Int = -1, @SerialName("service_category") val serviceCategoryItem: ServiceCategoryItem? = null,
                          @SerialName("product") val product: Product? = null, val isSelected: Boolean = false)

data class RecommendationItemUIModel(
    val selectedRecommendation: Recommendation?,
    val recommendationList: List<Recommendation>
)