package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserOrders (
    @SerialName("id") val id: Long? = null,
    @SerialName("user_id") val userId: Long? = null,
    @SerialName("order_id") val orderId: Long? = null,
    @SerialName("order") val customerOrder: CustomerOrder? = null,
    val isSelected: Boolean = false): Parcelable

data class UserOrderItemUIModel(
    val selectedUserOrders: UserOrders?,
    val userOrderList: List<UserOrders>
)
