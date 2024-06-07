package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CustomerOrder (
    @SerialName("id") val id: Int? = null,
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("vendor_id") val vendorId: Int? = null,
    @SerialName("orderStatus") val orderStatus: String? = "order_processing",
    @SerialName("paymentMethod") val paymentMethod: String = PaymentMethod.CARD_PAYMENT.toString(),
    @SerialName("vendor_recommendations") val recommendationRecommendations: ArrayList<VendorRecommendation>? = null,
    @SerialName("orderReference") val orderReference: Int? = null,
    @SerialName("order_items") var orderItems: List<OrderItem> = arrayListOf(),
    val isSelected: Boolean = false): Parcelable

data class CustomerItemUIModel(
    val selectedCustomerOrder: CustomerOrder?,
    val customerOrderList: List<CustomerOrder>
)
