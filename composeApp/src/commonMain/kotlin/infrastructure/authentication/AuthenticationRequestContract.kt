package infrastructure.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompleteProfileRequest(
    @SerialName("firstname") val firstname: String,
    @SerialName("lastname") val lastname: String,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("address") val address: String,
    @SerialName("contactPhone") val contactPhone: String,
    @SerialName("country") val country: String,
    @SerialName("gender") val gender: String,
    @SerialName("profileImageUrl") val profileImageUrl: String)

@Serializable
data class UpdateProfileRequest(@SerialName("firstname") val firstname: String,
                                  @SerialName("lastname") val lastname: String,
                                  @SerialName("userEmail") val userEmail: String,
                                  @SerialName("address") val address: String,
                                  @SerialName("contactPhone") val contactPhone: String,
                                  @SerialName("country") val country: String,
                                  @SerialName("gender") val gender: String,
                                  @SerialName("profileImageUrl") val profileImageUrl: String)

@Serializable
data class DeleteProfileRequest(@SerialName("userEmail") val userEmail: String)

@Serializable
data class GetProfileRequest(@SerialName("userEmail") val userEmail: String)
@Serializable
data class ConnectVendorRequest(@SerialName("userEmail") val userEmail: String,
                                @SerialName("vendorId") val vendorId: Int)