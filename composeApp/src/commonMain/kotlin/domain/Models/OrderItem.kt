package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class OrderItem(@SerialName("order_id") var orderId: Int = -1, @SerialName("imageUrl") val imageUrl: String = "",
                     @SerialName("product_id") var productId: Int = -1, @SerialName("itemCount") var itemCount: Int = 1,
                     @SerialName("orderItemJson") var orderItemJson: String = "",
                     @SerialName("product") var itemProduct: Product? = null, val isSelected: Boolean = false, var itemKey: Int = -1): Parcelable

data class OrderItemUIModel(
    val selectedItem: OrderItem?,
    val itemList: ArrayList<OrderItem>
)
@Parcelize
@Serializable
data class ItemComponent(@SerialName("product_id") var productId: Int = -1, @SerialName("imageUrl") val imageUrl: String,
                     @SerialName("product_name") var productName: String = "", @SerialName("description") var productDescription: String = "",
                     @SerialName("price") var productPrice: Int = 0, @SerialName("itemCount") var itemCount: Int = 0): Parcelable