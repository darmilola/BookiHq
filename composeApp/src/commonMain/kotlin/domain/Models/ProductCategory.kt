package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductCategory(@SerialName("id") val categoryId: Int? = null, @SerialName("vendor_id") val vendorId: Int? = null, @SerialName("categoryTitle") val categoryTitle: String? = null)