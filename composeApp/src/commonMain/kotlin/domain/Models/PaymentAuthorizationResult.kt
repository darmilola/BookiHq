package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PaymentAuthorizationResult(@SerialName("status") var status: Boolean = false, @SerialName("message") var message: String = "",
                                      @SerialName("data") var paymentAuthorizationData: PaymentAuthorizationData): Parcelable

@Parcelize
@Serializable
data class PaymentAuthorizationData(@SerialName("authorization_url") var authorizationUrl: String = "", @SerialName("access_code") var accessCode: String = "",
                                      @SerialName("reference") var reference: String = ""): Parcelable

//{"status":true,"message":"Authorization URL created","data":{"authorization_url":"https://checkout.paystack.com/83fjb24sed5ik9i","access_code":"83fjb24sed5ik9i","reference":"j00hgk8a86"}}