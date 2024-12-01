package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserOrders (
    @SerialName("id") val id: Int? = null,
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("order_id") val orderId: Int? = null,
    @SerialName("order") val customerOrder: CustomerOrder? = null,
    val isSelected: Boolean = false): Parcelable

data class UserOrderItemUIModel(
    val selectedUserOrders: UserOrders?,
    val userOrderList: List<UserOrders>
)
