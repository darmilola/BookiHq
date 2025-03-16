package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.OrderStatusEnum
import domain.Enums.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CustomerOrder (
    @SerialName("id") val id: Long? = null,
    @SerialName("user_id") val userId: Long? = null,
    @SerialName("vendor_id") val vendorId: Long? = null,
    @SerialName("orderStatus") val orderStatus: String? = OrderStatusEnum.PROCESSING.toPath(),
    @SerialName("paymentMethod") val paymentMethod: String = PaymentMethod.CARD_PAYMENT.toString(),
    @SerialName("order_items") var orderItems: OrderItem? = null,
    val isSelected: Boolean = false): Parcelable

data class CustomerItemUIModel(
    val selectedCustomerOrder: CustomerOrder?,
    val customerOrderList: List<CustomerOrder>
)
