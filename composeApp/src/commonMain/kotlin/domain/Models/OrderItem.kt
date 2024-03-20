package domain.Models

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(@SerialName("orderReference") var orderReference: Int = -1,
                     @SerialName("product_id") var productId: Int = -1, @SerialName("itemCount") var itemCount: Int = 1,
                     @SerialName("product") var itemProduct: Product? = null, val isSelected: Boolean = false, var itemReference: Int = -1)

data class OrderItemUIModel(
    val selectedItem: OrderItem?,
    val itemList: SnapshotStateList<OrderItem>
)