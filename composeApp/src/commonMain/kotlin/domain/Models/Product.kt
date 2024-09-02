package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Product(@SerialName("id") val productId: Long = -1L, @SerialName("vendor_id") val vendorId: Long = -1L, @SerialName("categoryId") val categoryId: Long = -1L,
                   @SerialName("productName") val productName: String = "", @SerialName("productDescription") val productDescription: String = "",
                   @SerialName("productPrice") val productPrice: Long = 0L, @SerialName("favoriteCount") val favoriteCount: Int = 0, @SerialName("orders") val orders: Int = 0, @SerialName("isAvailable") val isAvailable: Boolean = false,
                   @SerialName("product_reviews") val productReviews: ArrayList<ProductReview>? = null, @SerialName("product_images") val productImages: ArrayList<ProductImages> = arrayListOf(), val isSelected: Boolean = false): Parcelable
data class ProductItemUIModel(
    val selectedProduct: Product?,
    val productList: List<Product>
)