package domain.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompleteProfileRequest(
    @SerialName("firstname") val firstname: String,
    @SerialName("lastname") val lastname: String,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("authPhone") val userPhone: String,
    @SerialName("countryId") val countryId: Int,
    @SerialName("cityId") val cityId: Int,
    @SerialName("gender") val gender: String,
    @SerialName("profileImageUrl") val profileImageUrl: String)

@Serializable
data class ValidateProfileRequest(@SerialName("userEmail") val userEmail: String)

@Serializable
data class PhoneValidateProfileRequest(@SerialName("userPhone") val userPhone: String)