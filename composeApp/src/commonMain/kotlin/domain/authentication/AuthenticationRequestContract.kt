package domain.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompleteProfileRequest(
    @SerialName("firstname") val firstname: String,
    @SerialName("lastname") val lastname: String,
    @SerialName("email") val userEmail: String,
    @SerialName("authPhone") val authPhone: String,
    @SerialName("signupType") val signupType: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String,
    @SerialName("gender") val gender: String,
    @SerialName("imageUrl") val profileImageUrl: String)

@Serializable
data class ValidateProfileRequest(@SerialName("userEmail") val userEmail: String)

@Serializable
data class PhoneValidateProfileRequest(@SerialName("authPhone") val authPhone: String)