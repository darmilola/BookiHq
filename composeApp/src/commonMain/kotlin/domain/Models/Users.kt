package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int? = null, @SerialName("userEmail") val userEmail: String? = null, @SerialName("firstname") val firstname: String? = null,
    @SerialName("lastname") val lastname: String? = null, @SerialName("address") val address: String? = null, @SerialName("contactPhone") val contactPhone: String? = null,
    @SerialName("country") val country: String? = null, @SerialName("gender") val gender: String? = null, @SerialName("profileImageUrl")
    val profileImageUrl: String? = null, @SerialName("countryCode") val countryCode: String? = null, @SerialName("auth_token") val authToken: String? = null,
    @SerialName("connectedVendor") val connectedVendor: Int? = null, @SerialName("isTherapist") val isTherapist: Boolean? = false, @SerialName("isProfileCompleted") val isProfileCompleted: Boolean? = false)

