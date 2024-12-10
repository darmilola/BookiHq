package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductCategory(@SerialName("id") val categoryId: Long? = null, @SerialName("vendor_id") val vendorId: Long? = null, @SerialName("categoryTitle") val categoryTitle: String? = null)