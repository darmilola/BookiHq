package domain.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitCheckoutRequest(@SerialName("customerEmail") val customerEmail: String, @SerialName("amount") val amount: String)