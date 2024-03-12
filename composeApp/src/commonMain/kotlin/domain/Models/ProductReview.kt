package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductReview(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("vendor_id")
    val vendorId: Int? = null,
    @SerialName("product_id")
    val productId: Int? = null,
    @SerialName("reviewer")
    val productReviewer: User? = null,
    @SerialName("reviewText")
    val reviewText: String? = null,
    @SerialName("created_at")
    val reviewDate: String? = null)
