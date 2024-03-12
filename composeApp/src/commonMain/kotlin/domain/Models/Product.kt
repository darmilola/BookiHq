package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(@SerialName("id") val productId: Int = -1, @SerialName("vendor_id") val vendorId: Int = -1, @SerialName("categoryId") val categoryId: Int = -1,
                   @SerialName("productName") val productName: String = "", @SerialName("productDescription") val productDescription: String = "",
                   @SerialName("productPrice") val productPrice: Int = 0, @SerialName("isDiscounted") val isDiscounted: Boolean = false, @SerialName("discount") val discount: Int = 0,
                   @SerialName("favoriteCount") val favoriteCount: Int = 0, @SerialName("orders") val orders: Int = 0, @SerialName("isAvailable") val isAvailable: Boolean = false,
                   @SerialName("product_reviews") val productReviews: ArrayList<ProductReview>? = null, @SerialName("product_images") val productImages: ArrayList<ProductImages> = arrayListOf(), val isSelected: Boolean = false)
data class ProductItemUIModel(
    val selectedProduct: Product?,
    val productList: List<Product>
)