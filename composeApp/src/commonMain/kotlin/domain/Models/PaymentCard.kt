package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PaymentCard(
    val firstname: String = "",
    val lastname: String = "",
    val cardNumber: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = "",
    val cvv: String = "",
    val isSelected: Boolean = false): Parcelable


data class PaymentCardUIModel(
    val selectedCard: PaymentCard?,
    val visibleCards: List<PaymentCard>)